package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.TIotOwner;

/**
 * 资产管理 服务层实现
 * 
 * @author maodajun
 * @date 2019-11-06
 */
@IocBean(args = {"refer:dao"})
public class TIotOwnerService extends Service<TIotOwner> {
	public TIotOwnerService(Dao dao) {
		super(dao);
	}
}
