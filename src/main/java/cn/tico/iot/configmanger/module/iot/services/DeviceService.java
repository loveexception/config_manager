package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.controller.ApiController;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.mao.controller.MyGraphQLController;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Inject
    MyGraphQLController graphql;

    @Inject
    TagService tagService;

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

    public void kafka(List<Device> devices) {
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < devices.size(); i++) {
                    String sno =devices.get(i).getSno();
                    api.device(sno,null);
                    graphql.killCache(sno);
                    graphql.device(sno);
                }
                for (Device device:devices) {
                    kafkaBlock.produce(KafkaBlock.TOPIC, KafkaBlock.KEY_SNO,device.getSno());

                    try {
                        Thread.sleep(2000l);
                    } catch (InterruptedException e) {

                    }

                }
            }
        }.start();



    }
    public void kafka(Tag tag) {
        tag = tagService.fetchLinks(tag,"devices");
        final List<Device> devices = tag.devices;
        kafka(devices);
    }
    public void kafka(Driver driver){
        Device device = new Device();
        device .setDriverid(driver.getId());
        Cnd cnd = findByDevice(device);
        List<Device> devices =query(cnd);
        kafka(devices);
    }
//    public void kafka(Gateway gateway){
//        Device device =new Device();
//        device.setGatewayid(gateway.getId());
//        Cnd cnd = findByDevice(device);
//        List<Device> devices = query(cnd);
//        kafka(devices);
//    }

    public void kafka(Gateway from,Gateway to){

        Device device =new Device();
        device.setGatewayid(from.getId());
        Cnd cnd = findByDevice(device);
        List<Device> devices = query(cnd);

        device =new Device();
        device.setGatewayid(to.getId());
        cnd = findByDevice(device);


        devices .addAll( query(cnd) );

        kafka(devices);
    }
    public void kafka(Dept dept){
        Device device = new Device();
        device.setDeptid(dept.getId());
        Cnd cnd = findByDevice(device);
        List<Device> devices = query(cnd);
        kafka(devices);

    }
    public void kafka(Kind kind){
        Device device = new Device();
        device.setKindid(kind.getId());
        Cnd cnd = findByDevice(device);
        List<Device> devices = query(cnd);
        kafka(devices);

    }
    public void kafka(Location location){
        Device device = new Device();
        device.setLocationid(location.getId());
        Cnd cnd = findByDevice(device);
        List<Device> devices = query(cnd);
        kafka(devices);

    }

    public   Cnd findByDevice(Device device) {
        if(Lang.isEmpty(device)){
            return Cnd.NEW();
        }
        Cnd cnd = Cnd.NEW();
        cnd.and("status","=","true");
        cnd.and("delflag","=","false");

        if(Strings.isNotBlank(device.getDriverid())){
            cnd.and("driver_id","=",device.getDriverid());
        }
        if(Strings.isNotBlank(device.getGatewayid())){
            cnd.and("gateway_id","=",device.getGatewayid());
        }
        if(Strings.isNotBlank(device.getDeptid())){
            cnd.and("dept_id","=",device.getDeptid());
        }
        if(Strings.isNotBlank(device.getLocationid())){
            cnd.and("location_id","=",device.getLocationid());
        }
        if(Strings.isNotBlank(device.getKindid())){
            cnd.and("kind_id","=",device.getKindid());
        }
        if(Lang.isNotEmpty(device.getTags())){
            List<Device> all = Lists.newArrayList();
            List<String> ids = device.getTags()
                    .stream()
                    .reduce(all,(acc,item)->{
                        item = dao().fetchLinks(item,"devices");
                        acc.addAll(item.getDevices());
                        return acc;
                    },(acc,item)->null)
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            cnd.and("id","in",ids);
        }

        return cnd;

    }
}
