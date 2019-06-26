package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.Location;
import cn.tico.iot.configmanger.module.iot.models.Gateway;
import cn.tico.iot.configmanger.module.sys.models.User;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Date;

/**
 * 业务标签
 *
 * @author maodajun
 * @date 2019-06-26
 */
@IocBean(args = {"refer:dao"})
public class GatewayService extends Service<Gateway> {
	public GatewayService(Dao dao) {
		super(dao);
	}

	public Gateway insertGateway(Gateway gateway) {
		User user =  ShiroUtils.getSysUser();
		gateway.setDeptid(user.getDeptId());

		gateway.setCreateBy(ShiroUtils.getSysUserId());
		gateway.setCreateTime(new Date());
		return this.dao().insert(gateway);
	}

	public int updateGateway(Gateway gateway) {
		gateway.setDeptid(null);
		gateway.setUpdateBy(ShiroUtils.getSysUserId());
		gateway.setUpdateTime(new Date());
		Dao forup = Daos.ext(this.dao(), FieldFilter.create(Location.class, true));
		return forup.update(gateway);


	}

}
