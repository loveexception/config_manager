package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.Topo.Base;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * 拓扑图存储 服务层实现
 * 
 * @author maodajun
 * @date 2019-12-05
 */
@IocBean(args = {"refer:dao"})
public class TTopoBasesService extends Service<Base> {
	public TTopoBasesService(Dao dao) {
		super(dao);
	}
}
