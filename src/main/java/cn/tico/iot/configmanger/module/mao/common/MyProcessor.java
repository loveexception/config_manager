package cn.tico.iot.configmanger.module.mao.common;

import org.nutz.lang.Stopwatch;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionInfo;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Processor;
import org.nutz.mvc.impl.processor.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;

public class MyProcessor extends AbstractProcessor {
    private static final Log log = Logs.get();

    public MyProcessor() {
    }

    @Override
    public void process(ActionContext ac) throws Throwable {
        Stopwatch sw = Stopwatch.begin();
        try {
            ac.getResponse().setHeader("Access-Control-Allow-Origin","*");
            ac.getResponse().setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
            ac.getResponse().setHeader("Access-Control-Allow-Methods","GET, POST, PUT,DELETE,OPTIONS");
            if ("OPTIONS".equals(ac.getRequest().getMethod())) {
                    log.debugf("DO CROS OPTIONs -- [%s] [%s] [%s] [%s]",ac.getPath(),ac.getResponse().getHeaderNames(),ac.getRequest().getQueryString(),ac.getRequest().getMethod());

                return ;
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
