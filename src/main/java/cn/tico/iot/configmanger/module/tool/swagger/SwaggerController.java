package cn.tico.iot.configmanger.module.tool.swagger;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import javax.servlet.http.HttpServletRequest;

/**
 * swagger 接口
 * @author Haiming
 */
@IocBean
@At("/tool/swagger")
public class SwaggerController {

    @At("")
    @Ok("forward:/swagger")
    public void index(HttpServletRequest req) {

    }
}
