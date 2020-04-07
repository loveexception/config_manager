package cn.tico.iot.configmanger.module.mao.common;

import cn.tico.iot.configmanger.module.sys.models.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.nutz.boot.starter.WebEventListenerFace;
import org.nutz.boot.starter.shiro.ShiroEnvStarter;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionInfo;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Processor;
import org.nutz.mvc.impl.processor.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Set;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;

@IocBean
public class MyCorssProcessor extends AbstractProcessor {
    private static final Log log = Logs.get();
    public static final String KEYS="access_token,code,dept_id,location,location_id,login_name,Referer,token,user_id";

    public MyCorssProcessor() {
    }

    @Override
    public void process(ActionContext ac) throws Throwable {
        Stopwatch sw = Stopwatch.begin();
        try {
            ac.getResponse().setHeader("Access-Control-Allow-Origin","*");
            ac.getResponse().setHeader("Access-Control-Allow-Credentials","true");

            if ("OPTIONS".equals(ac.getRequest().getMethod())) {
                ac.getResponse().setHeader("Access-Control-Allow-Origin","*");
                ac.getResponse().setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, "+KEYS);
                ac.getResponse().setHeader("Access-Control-Allow-Methods","GET, POST, PUT,DELETE,OPTIONS");
                ac.getRequest().setAttribute("","");
            }
            doNext(ac);

        } finally {
            sw.stop();
            if (log.isDebugEnabled()) {
                HttpServletRequest req = ac.getRequest();
                log.debugf("[%-4s]URI=%s %sms", req.getMethod(), req.getRequestURI(), sw.getDuration());
            }
        }
    }



}
