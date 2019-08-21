package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.TUiMetrics;

/**
 * 一键寻检数据 服务层实现
 * 
 * @author maodajun
 * @date 2019-08-21
 */
@IocBean(args = {"refer:dao"})
public class TUiMetricsService extends Service<TUiMetrics> {
	public TUiMetricsService(Dao dao) {
		super(dao);
	}
}
