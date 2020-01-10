package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

//import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.TIotDevices;

/**
 * 设备资本 服务层实现
 * 
 * @author maodajun
 * @date 2019-11-07
 */
@IocBean(args = {"refer:dao"})
public class TIotDevicesService extends Service<Device> {
	public TIotDevicesService(Dao dao) {
		super(dao);
	}
}
