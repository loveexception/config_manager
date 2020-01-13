package cn.tico.iot.configmanger.module.mao.services;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.mao.models.Upgrades;

/**
 * 告警升级 服务层实现
 * 
 * @author maodajun
 * @date 2020-01-03
 */
@IocBean(args = {"refer:dao"})
public class UpgradesService extends Service<Upgrades> {
	public UpgradesService(Dao dao) {
		super(dao);
	}
}
