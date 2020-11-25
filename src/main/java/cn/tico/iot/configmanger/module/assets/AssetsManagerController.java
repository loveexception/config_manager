package cn.tico.iot.configmanger.module.assets;

import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.common.utils.TreeUtils;
import cn.tico.iot.configmanger.module.assets.model.CustomerToken;
import cn.tico.iot.configmanger.module.sys.models.Menu;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.MenuService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.nutz.integration.jedis.RedisService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 资产管理模块单独使用
 * Package Name: cn.tico.iot.configmanger.module.assets
 * Date: 2020/11/25
 * Author: jt
 */
@IocBean
@At("/assets")
public class AssetsManagerController {
    @Inject
    private RedisService redisService;
    @Inject
    private MenuService menuService;
    @Inject
    private UserService userService;

    /**
     * @description: 访问资产管理前的免密登录
     * @params: request
     * @return: response
     * @author: jt
     */
    @At({ "/toAssetsManager"})
    @Ok("re")
    public String tokenLogin(HttpServletRequest request, HttpServletResponse response, String token) throws IOException {
        String accessToken = request.getHeader("access_token");
        if (StringUtils.isEmpty(accessToken)){
            accessToken = token;
        }

        String userInfo = redisService.get("login:pro:"+accessToken);
        if (StringUtils.isEmpty(userInfo)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            Map<String, Object> map = new HashMap<>();
            map.put("success",false);
            map.put("code","666");
            map.put("msg","您还未登录哦！请登录账户。");
            pw.write(JSON.toJSONString(map));
            pw.flush();
            pw.close();
            return null;
        }

        //判断是否登录
        User user = ShiroUtils.getSysUser();
        if (Lang.isEmpty(user)) {
            Subject subject = SecurityUtils.getSubject();
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            String loginName = jsonObject.getString("loginName");
            subject.login(new CustomerToken(loginName));
            ThreadContext.bind(subject);
            user = ShiroUtils.getSysUser();
            System.out.println("token值:"+userInfo);
        }

        user = userService.fetchLinks(user, "dept|image");
        request.setAttribute("user", user);
        if (Lang.isNotEmpty(user.getImage())) {
            request.setAttribute("image", user.getImage().getBase64());
        }
        List<Menu> menuList = menuService.getMenuList(user.getId());
        request.setAttribute("menus", TreeUtils.getChildPerms(menuList, "0"));
        return "th:/newIndex.html";
    }

    /**
     * 系统介绍
     *
     * @return
     */
    @At({ "/main" })
    @Ok("th:/newMain.html")
    public NutMap main() {
        return NutMap.NEW().setv("version", "1.0");
    }

    @At({ "/loginOut"})
    @Ok("json")
    public Map loginOut() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success",true);
        map.put("code",0);
        map.put("msg","退出登陆成功");
        return map;
    }
}
