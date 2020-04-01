package cn.tico.iot.configmanger.module.mao.common;


import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.filter.CrossOriginFilter;

@Filters({@By(type=CrossOriginFilter.class)})
public class BaseController {


}

