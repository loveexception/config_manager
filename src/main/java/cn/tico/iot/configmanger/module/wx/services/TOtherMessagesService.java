package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.TOtherMessages;

/**
 * kafka的推送 服务层实现
 * 
 * @author maodajun
 * @date 2019-11-21
 */
@IocBean(args = {"refer:dao"})
public class TOtherMessagesService extends Service<TOtherMessages> {
	public TOtherMessagesService(Dao dao) {
		super(dao);
	}
}
