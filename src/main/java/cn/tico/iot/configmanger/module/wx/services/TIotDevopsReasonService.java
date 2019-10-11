package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.TIotDevopsReason;

/**
 * 故障原因 服务层实现
 * 
 * @author maodajun
 * @date 2019-10-11
 */
@IocBean(args = {"refer:dao"})
public class TIotDevopsReasonService extends Service<TIotDevopsReason> {
	public TIotDevopsReasonService(Dao dao) {
		super(dao);
	}
}
