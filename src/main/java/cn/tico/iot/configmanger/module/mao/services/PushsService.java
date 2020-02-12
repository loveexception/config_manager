package cn.tico.iot.configmanger.module.mao.services;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.mao.models.Pushs;

/**
 * 推送方式 服务层实现
 * 
 * @author maodajun
 * @date 2020-01-10
 */
@IocBean(args = {"refer:dao"})
public class PushsService extends Service<Pushs> {
	public PushsService(Dao dao) {
		super(dao);
	}
	public Object insertAll(List<Pushs> pushs) {

        return this.dao().insert(pushs);
    }

}
