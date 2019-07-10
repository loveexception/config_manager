package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * 业务标签 服务层实现
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean(args = {"refer:dao"})
public class DeviceService extends Service<Device> {
    public DeviceService(Dao dao) {
        super(dao);
    }

    public Object insertUpdate(Device device) {
        return this.dao().insertOrUpdate(device);
    }

    public Object insertAll(List<Driver> asList) {

        return this.dao().insert(asList);
    }

    public Object updateAll(List<Driver> asList) {
        return this.dao().update(asList);
    }
}
