package cn.tico.iot.configmanger.module.mao.common;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

@IocBean(singleton = false)
    public class MyCrossFliter extends AbstractProcessor {
        @Override
        public void process(ActionContext actionContext) throws Throwable {
            actionContext.getResponse().setHeader("Access-Control-Allow-Origin","*");
            actionContext.getResponse().setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
            actionContext.getResponse().setHeader("Access-Control-Allow-Methods","GET, POST, PUT,DELETE,OPTIONS");

            doNext(actionContext);
        }
    }
