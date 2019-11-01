package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.page.TableDataInfo;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.entity.MappingField;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.Date;
import java.util.List;

/**
 * 业务标签
 *
 * @author maodajun
 * @date 2019-06-26
 */
@IocBean(args = {"refer:dao"})
public class SubGatewayService extends Service<SubGateway> {

	public SubGatewayService(Dao dao) {
		super(dao);
	}



	public SubGateway insertEntity(SubGateway subgateway) {


		subgateway.setCreateBy("0");
		subgateway.setCreateTime(new Date());
		return this.dao().insert(subgateway);
	}

	public int updateEntity(SubGateway subGateway) {
		subGateway.setUpdateBy("0");
		subGateway.setUpdateTime(new Date());
		Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));
		return forup.update(subGateway);
	}


	public void insertUpdateEntity(SubGateway subGateway) {
		if(Lang.isEmpty(subGateway)){
			return;
		}
		if(Strings.isEmpty(subGateway.getId())){
			insertEntity(subGateway);
		}else {
			updateEntity(subGateway);
		}
	}

    public SubGateway findByGateWayId(String gatewayid) {
		if(Strings.isBlank(gatewayid)){
			return null;
		}
		Cnd cnd = Cnd.NEW()
				.and("status","=","true")
				//.and("delflage","=","false")
				.and("gw_id","=",gatewayid);

		List<SubGateway> subGateways = query(cnd);
		if(Lang.isEmpty(subGateways)){
			return null;
		}
		return subGateways.get(0);

    }
}
