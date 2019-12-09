package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.controller.ApiController;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
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
    @Inject
    KafkaBlock kafkaBlock;

    @Inject
    ApiController api;

    public DeviceService(Dao dao) {
        super(dao);
    }

    public Object insertUpdate(Device device) {

        if(isOld(device)){
           // Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));

            device =  extAttr(device);

            Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));

            forup.update(device);
            return  device;

        }
        device = creatAttr(device);
        return this.dao().insert(device);




    }

    public Device creatAttr(Device device) {
        device.setCreateBy(ShiroUtils.getSysUserId());
        device.setCreateTime(new Date());
        if(Strings.isBlank(device.getAssetStatus())){
            device.setAssetStatus("0");
        }

        return device;
    }

    /**
     * 老项目数据清洗
     * @param device
     * @return
     */
    public Device extAttr(Device device) {
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

    public void kafka(List<Device> result) {
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < result.size(); i++) {
                    String sno =result.get(i).getSno();
                    api.LRU.remove(sno);
                    //api.device(sno,null);
                }

                for (Device device:result) {
                    kafkaBlock.produce(KafkaBlock.TOPIC, KafkaBlock.KEY_SNO,device.getSno());

                }
            }
        }.start();



    }
}
