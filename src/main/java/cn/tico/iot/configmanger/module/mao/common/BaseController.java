package cn.tico.iot.configmanger.module.mao.common;


import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.filter.CrossOriginFilter;

@Filters({@By(type=CrossOriginFilter.class, args={"*"
        , "GET, POST, PUT, DELETE, OPTIONS, PATCH"
        , "Origin, Content-Type, Accept, X-Requested-With"
        + "adcode,dept_id,location,location_id,login_name,Referer,token,user_id"
        , "true"})})
public class BaseController {


}

