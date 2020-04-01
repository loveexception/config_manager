package cn.tico.iot.configmanger.module.mao.common;

import cn.tico.iot.configmanger.common.annotation.AccessToken;
import cn.tico.iot.configmanger.common.manager.AsyncManager;
import cn.tico.iot.configmanger.common.manager.factory.AsyncFactory;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.monitor.services.LogininforService;
import cn.tico.iot.configmanger.module.monitor.services.OperLogService;
import cn.tico.iot.configmanger.module.monitor.services.UserOnlineService;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.nutz.integration.jedis.JedisAgent;
import org.nutz.integration.jedis.RedisService;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;

@IocBean
public class MyTokenProcessor extends AbstractProcessor {
    private static final Log log = Logs.get();
    public static final String ACCESS_TOKEN = "access_token";

    private String LOGIN_NAME  = "login_name";


    @Inject
    private UserService userService;
    @Inject
    private AsyncFactory asyncFactory;

    @Inject
    private LocationService locationService;

    @Inject
    RedisService agent;


    public MyTokenProcessor() {

    }

    @Override
    public void process(ActionContext ac) throws Throwable {
        String token ="";


        String username = headName(ac, LOGIN_NAME);

        if(Strings.isNotBlank(username)){
            token = headName(ac, ACCESS_TOKEN);
            token = redisFind(ac , token);
            if(token==null){
                ac.getResponse().setCharacterEncoding("utf-8");
                ac.getResponse().setContentType("application/json; charset=utf-8");
                PrintWriter writer = ac.getResponse().getWriter();
                Map<String, Object> map = new HashMap<>();
                map.put("code",401);
                map.put("msg", "success");
                writer.write(Json.toJson(map));


                return;

            }

        }else{
            token = "";
        }
        Subject subject = null;
        if(Strings.isNotBlank(token)){

            subject = SecurityUtils.getSubject();
            ThreadContext.bind(subject);
            if(subject.isAuthenticated()){
                Object obj = subject.getPrincipal();
                String temp = Json.toJson(obj);
                obj = Json.fromJson(temp);
                String id = Mapl.cell(obj,"id").toString();

                if(Strings.equals(id,token)){

                }else{
                    subjectLogin(ac, username, subject);
                }

            }else {
                subjectLogin(ac, username, subject);
            }


        }

        doNext(ac);


        if(Strings.isNotBlank(token)&&Lang.isNotEmpty(subject)){
            //subject.logout();

        }


    }

    public void subjectLogin(ActionContext ac, String username, Subject subject) {
       // subject.login(new UsernamePasswordToken(username, "123456", false));
        subject.login(new SimpleShiroToken(username));
        User user = (User) subject.getPrincipal();

        if (Lang.isEmpty(asyncFactory)) {
            asyncFactory = ac.getIoc().get(AsyncFactory.class);

        }
        AsyncManager.me().execute(asyncFactory.recordLogininfor(user.getLoginName(), true, "登录成功"));
        if (Lang.isEmpty(userService)) {
            userService = ac.getIoc().get(UserService.class);

        }

        userService.recordLoginInfo(user);
    }

    public  String redisFind(ActionContext ac, String token) {
        if(Lang.isEmpty(agent)){
            agent  = ac.getIoc().get(RedisService.class);

        }


       boolean istoken =  agent.exists("login:pro:"+token);

        if(istoken){
            token = agent.get("login:pro:"+token);
            Object obj = Json.fromJson(token);

            token = Mapl.cell(obj,"userId").toString();
            return token;
        }
        return null;


    }


    public String headName(ActionContext actionContext,String headName) {
        String token =actionContext.getRequest().getHeader(headName);
        if(Strings.isBlank(token)){
            return null;
        }
        return token;
    }


}
