package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.WxUser;

/**
 * 微信用户 服务层实现
 * 
 * @author haiming
 * @date 2019-05-13
 */
@IocBean(args = {"refer:dao"})
public class WxUserService extends Service<WxUser> {
	public WxUserService(Dao dao) {
		super(dao);
	}
}
