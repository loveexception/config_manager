package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.OtherWorkflow;

/**
 * 运维 服务层实现
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@IocBean(args = {"refer:dao"})
public class OtherWorkflowService extends Service<OtherWorkflow> {
	public OtherWorkflowService(Dao dao) {
		super(dao);
	}
}
