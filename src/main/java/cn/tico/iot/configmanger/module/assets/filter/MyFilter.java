package cn.tico.iot.configmanger.module.assets.filter;

import cn.tico.iot.configmanger.module.assets.model.CustomerToken;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.nutz.integration.jedis.RedisService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: todo
 * Package Name: cn.tico.iot.configmanger.module.assets.filter
 * Date: 2020/11/24
 * Author: jt
 */
//@IocBean
public class MyFilter implements ActionFilter {
    @Inject
    private RedisService redisService = Mvcs.ctx().getDefaultIoc().get(RedisService.class);

    @Override
    public View match(ActionContext actionContext) {
        HttpServletRequest request = actionContext.getRequest();

        String token = request.getHeader("token");

        String userInfo = redisService.get("login:pro:"+token);
        if (StringUtils.isEmpty(userInfo)){
            return null;
        }
        Subject subject = SecurityUtils.getSubject();
        //判断是否登录
        if (subject.isAuthenticated()){
            return null;
        }

        JSONObject jsonObject = JSONObject.parseObject(userInfo);
        String loginName = jsonObject.getString("loginName");
        subject.login(new CustomerToken(loginName));
        ThreadContext.bind(subject);
        System.out.println("token值:"+userInfo);
        return null;
    }
}
