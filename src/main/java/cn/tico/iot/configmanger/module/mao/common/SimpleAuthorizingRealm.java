package cn.tico.iot.configmanger.module.mao.common;

import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.integration.shiro.AbstractSimpleAuthorizingRealm;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import java.util.Set;

@IocBean(name="shiroRealm2", fields="dao")
public class SimpleAuthorizingRealm extends AbstractSimpleAuthorizingRealm {
	@Inject
	private UserService userService = Mvcs.ctx()
			.getDefaultIoc()
			.get(UserService.class);
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		// null usernames are invalid
//		if (principals == null) {
//			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
//		}
//		long userId = ((Number) principals.getPrimaryPrincipal()).longValue();
//		User user = dao().fetch(User.class, userId);
//		if (user == null)
//			return null;
//		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
//		auth.addRole(user.getLoginName());
//		auth.addStringPermission("user:list");
		if(true){
			Logs.get().debug("start");
		}
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
		SimpleShiroToken upToken = (SimpleShiroToken) token;

		User user = dao().fetch(User.class, (String)upToken.getPrincipal());
		if (user == null)
			return null;
		return new SimpleAccount(user, user.getPassword(), getName());
	}

	public SimpleAuthorizingRealm() {
		this(null, null);
	}

	public SimpleAuthorizingRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
		super(cacheManager, matcher);
		setAuthenticationTokenClass(SimpleShiroToken.class);
	}

	public SimpleAuthorizingRealm(CacheManager cacheManager) {
		this(cacheManager, null);
	}

	public SimpleAuthorizingRealm(CredentialsMatcher matcher) {
		this(null, matcher);
	}
	
}