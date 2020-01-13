package cn.tico.iot.configmanger.module.mao.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.wx.models.OtherParts;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.mao.models.Pars;

/**
 * 备品备件 服务层实现
 * 
 * @author maodajun
 * @date 2019-12-14
 */
@IocBean(args = { "refer:dao" })
public class ParsService extends Service<OtherParts> {
	public ParsService(Dao dao) {
		super(dao);
	}

}
