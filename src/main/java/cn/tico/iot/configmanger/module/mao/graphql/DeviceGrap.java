package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.device.*;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.DriverService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Logs;

import java.util.Iterator;
import java.util.List;


@IocBean
public class DeviceGrap {
    @Inject
    private DeviceService device;

    @Inject
    private DriverService driver;



    @Inject
    private Dao dao;

    @GraphQLQuery
    public Device deviceBySno(@GraphQLArgument(name = "sno") String sno) {
        Logs.get().infof("start ---> sno:%s" + sno);
        if (Strings.isBlank(sno)) {
            return null;
        }
        Cnd cnd = Cnd.NEW();
        cnd.and("t_iot_devices.sno", "=", sno);
        cnd.and("t_iot_devices.delflag","=","false");
        cnd.and("t_iot_devices.asset_status", "=", "2");
        List<Device> devices = dao.queryByJoin(Device.class, "^tags$", cnd);
        Iterator<Device> it = devices.iterator();
        if (it.hasNext()) {
            return devices.get(0);
        }
        return null;
    }
    @GraphQLQuery
    public Device device(@GraphQLArgument(name = "id") String id) {
        Logs.get().infof("start ---> id:%s" + id);

        return dao.fetch(Device.class,id);
    }
    @GraphQLQuery
    public List<Device>  findDeviceByGateWay(@GraphQLContext Gateway gateway) {

        Logs.get().infof("start ---> gate way sno :%s" , gateway.getSno() );


        if(Lang.isEmpty(gateway)){
            return null;
        }

        if(Strings.isEmpty(gateway.getId())){
            return null;
        }
        if(Lang.isEmpty(gateway.getSubGateway())){
            return null;
        }

        Logs.get().infof("start --->sub gate way  extsno:%s" , gateway.getSubGateway().getExtSno() );


        Cnd cnd = Cnd.NEW();
        cnd .and("gateway_id","=",gateway.getId());

        List<Device> devices = dao.query(Device.class,cnd);


        return devices;
    }
}