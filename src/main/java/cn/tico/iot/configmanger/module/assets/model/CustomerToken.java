package cn.tico.iot.configmanger.module.assets.model;

import cn.tico.iot.configmanger.module.assets.enums.LoginType;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Description: todo
 * Package Name: cn.tico.iot.configmanger.module.assets.model
 * Date: 2020/11/24
 * Author: jt
 */
public class CustomerToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -2564928913725078138L;

    private LoginType type;


    public CustomerToken() {
        super();
    }


    public CustomerToken(String username, String password, LoginType type, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.type = type;
    }

    public CustomerToken(String username, String password,LoginType type, boolean rememberMe) {
        super(username, password, rememberMe,null);
        this.type = type;
    }

    /**
     * 免密登录
     */
    public CustomerToken(String username) {
        super(username, "", false, null);
        this.type = LoginType.NOPASSWD;
    }

    /**
     * 账号密码登录
     */
    public CustomerToken(String username, String password) {
        super(username, password, false, null);
        this.type = LoginType.PASSWORD;
    }

    public LoginType getType() {
        return type;
    }


    public void setType(LoginType type) {
        this.type = type;
    }

}
