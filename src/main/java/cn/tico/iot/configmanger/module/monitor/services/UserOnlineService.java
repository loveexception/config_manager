package cn.tico.iot.configmanger.module.monitor.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.monitor.models.UserOnline;

/**
 * 在线用户记录 服务层实现
 * 
 * @author haiming
 * @date 2019-04-18
 */
@IocBean(args = {"refer:dao"})
public class UserOnlineService extends Service<UserOnline> {
	public UserOnlineService(Dao dao) {
		super(dao);
	}
}
