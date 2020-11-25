package cn.tico.iot.configmanger.module.assets.shiro;

import cn.tico.iot.configmanger.module.assets.enums.LoginType;
import cn.tico.iot.configmanger.module.assets.model.CustomerToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * Description: 重写HashedCredentialsMatcher，不需要密码登录
 * Package Name: cn.tico.iot.configmanger.module.assets.shiro
 * Date: 2020/11/24
 * Author: jt
 */
@IocBean
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        CustomerToken tk = (CustomerToken) authcToken;
        if(tk.getType().equals(LoginType.NOPASSWD)){
            return true;
        }
        return super.doCredentialsMatch(authcToken, info);
    }

}
