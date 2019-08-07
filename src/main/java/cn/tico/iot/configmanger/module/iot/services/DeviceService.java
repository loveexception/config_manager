package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import java.util.Date;
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

        if(isOld(device)){
            Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));

            device =  extAttr(device);
            forup.update(device);
            return  device;

        }
        device = creatAttr(device);
        return this.dao().insert(device);




    }

    private Device creatAttr(Device device) {
        device.setCreateBy(ShiroUtils.getSysUserId());
        device.setCreateTime(new Date());
        device.setAssetStatus("0");

        return device;
    }

    /**
     * 老项目数据清洗
     * @param device
     * @return
     */
    private Device extAttr(Device device) {
        device.setCreateBy(null);
        device.setCreateTime(null);
        device.setUpdateBy(ShiroUtils.getSysUserId());
        device.setUpdateTime(new Date());
        return device;
    }

    private boolean isOld(Device device) {
        return Strings.isNotBlank(device.getId());
    }

    public Object insertAll(List<Driver> drivers) {

        return this.dao().insert(drivers);
    }

    public Object updateAll(List<Driver> drivers) {

        return this.dao().update(drivers);
    }
}