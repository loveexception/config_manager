package cn.tico.iot.configmanger.realui.bean;

import cn.tico.iot.configmanger.module.sys.models.Role;
import cn.tico.iot.configmanger.module.sys.models.User;


import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

import java.util.Set;


public class MyRealm extends AuthorizingRealm {

    // 取一次即可
    @Inject
    private UserService userService = Mvcs.ctx()
            .getDefaultIoc()
            .get(UserService.class);

    public MyRealm() {
        this(null, null);
    }

    public MyRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
        hashedCredentialsMatcher.setHashIterations(1024);
        // 这一行决定hex还是base64
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
        // 设置token类型是关键!!!
        setCredentialsMatcher(hashedCredentialsMatcher);
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    public MyRealm(CacheManager cacheManager) {
        this(cacheManager, null);
    }

    public MyRealm(CredentialsMatcher matcher) {
        this(null, matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        User user = (User) principals.getPrimaryPrincipal();
        if (user == null) {
            return null;
        }
        if (userService == null){
            userService = Mvcs.ctx().getDefaultIoc().get(UserService.class); // 取一次即可

        }
        Set<String> roles =userService.getRoleCodeList(user);
        Set<String> menus = userService.getPermsByUserId(user.getId());

        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        auth.setRoles(roles);
        auth.setStringPermissions(menus);
        return auth;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        if (Lang.isEmpty(upToken) || Strings.isEmpty(upToken.getUsername())) {
            throw Lang.makeThrow(AuthenticationException.class, "Account name is empty");
        }
        if (userService == null){
            userService = Mvcs.ctx()
                    .getDefaultIoc()
                    .get(UserService.class);

        }

        User user = userService.fetch(Cnd.where("login_name","=",upToken.getUsername()));
        if (Lang.isEmpty(user)) {
            throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", upToken.getUsername());
        }
        if (user.isStatus()) {
            throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", upToken.getUsername());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
                user.getPassword().toCharArray(), ByteSource.Util.bytes(user.getSalt()), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
//        info.
        return info;
    }


}