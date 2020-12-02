package cn.tico.iot.configmanger.module.assets.enums;

public enum LoginType {
    PASSWORD("password"), // 密码登录
    NOPASSWD("nopassword"); // 免密登录

    private String code;// 状态值

    LoginType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}