package cn.tico.iot.configmanger.module.mao.common;

import cn.tico.iot.configmanger.common.annotation.AccessToken;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.nutz.integration.jedis.JedisAgent;
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

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;

@IocBean
public class MyTokenProcessor extends AbstractProcessor {
    private static final Log log = Logs.get();
    public static final String ACCESS_TOKEN = "access_token";

    private String LOGIN_NAME  = "login_name";
    @Inject
    JedisAgent agent;


    public MyTokenProcessor() {

    }

    @Override
    public void process(ActionContext ac) throws Throwable {
        String token ="";


        String username = headName(ac, LOGIN_NAME);

        if(Strings.isNotBlank(username)){
            token = headName(ac, ACCESS_TOKEN);
            token = redisFind(ac , token);

        }else{
            token = "";
        }
        if(Strings.isNotBlank(token)){
            Subject subject = ShiroUtils.getSubject();
            subject.login(new SimpleShiroToken(token));

            boolean islogin = subject.isAuthenticated();
            Logs.get().debug(islogin);
            Logs.get().debug(subject.getPrincipal());

        }
        doNext(ac);

    }

    public  String redisFind(ActionContext ac, String token) {
        if(Lang.isEmpty(agent)){
            agent  = ac.getIoc().get(JedisAgent.class);

        }


       boolean istoken =  agent.jedis().exists("login:pro:"+token);

        if(istoken){
            token = agent.jedis().get("login:pro:"+token);
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
