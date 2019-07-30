package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.page.TableDataInfo;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.device.Gateway;
import cn.tico.iot.configmanger.iot.models.device.SubGateway;
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
public class GatewayService extends Service<Gateway> {

	public GatewayService(Dao dao) {
		super(dao);
	}



	public Gateway insertEntity(Gateway gateway) {


		gateway.setCreateBy(ShiroUtils.getSysUserId());
		gateway.setCreateTime(new Date());
		return this.dao().insert(gateway);
	}

	public int updateEntity(Gateway gateway) {
		gateway.setUpdateBy(ShiroUtils.getSysUserId());
		gateway.setUpdateTime(new Date());
		Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));
		return forup.update(gateway);
	}

	public Object selectSub(int pageNumber, int pageSize,Cnd cnd,String orderByColumn,String isAsc,String linkname) {

		Pager pager = this.dao().createPager(pageNumber, pageSize);
		if (Strings.isNotBlank(orderByColumn) && Strings.isNotBlank(isAsc)) {
			MappingField field =dao().getEntity(SubGateway.class).getField(orderByColumn);
			if(Lang.isNotEmpty(field)){
				cnd.orderBy(field.getColumnName(),isAsc);
			}
		}
		List<SubGateway> list = this.dao().query(SubGateway.class, cnd, pager);
		if (!Strings.isBlank(linkname)) {
			this.dao().fetchLinks(list, linkname);
		}
		return new TableDataInfo(list, this.dao().count(SubGateway.class,cnd));
	}
}
