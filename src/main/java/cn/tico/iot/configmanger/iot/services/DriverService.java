package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务标签 服务层实现
 * 
 * @author haiming
 * @date 2019-04-16
 */
@IocBean(args = {"refer:dao"})
public class DriverService extends Service<Driver> {
	public DriverService(Dao dao) {
		super(dao);
	}

	public Driver insertEntity(Driver driver) {


		driver.setCreateBy(ShiroUtils.getSysUserId());
		driver.setCreateTime(new Date());
		return this.dao().insert(driver);
	}

	public int updateEntity(Driver driver) {
		driver.setUpdateBy(ShiroUtils.getSysUserId());
		driver.setUpdateTime(new Date());
		Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));
		return forup.update(driver);
	}

	public List<Driver> insertEntitys(List<Driver> drivers) {
		List<Driver> list = new ArrayList<Driver>();
		for (Driver driver:drivers) {
			Driver temp =  insertEntity(driver);
			list.add(temp);
		}
		return list;
	}

	public List<Driver> updateEntitys(List<Driver> drivers) {

		for (Driver driver:drivers){

			updateEntity(driver);
		}
		return drivers;
	}

	public Object insertSubs(Driver driver) {
		Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));

		return forup.insertOrUpdate(driver);
	}
}
