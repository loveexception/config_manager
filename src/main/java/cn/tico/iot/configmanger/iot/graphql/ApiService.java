package cn.tico.iot.configmanger.iot.graphql;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.iot.services.DeviceService;
import cn.tico.iot.configmanger.iot.services.DriverService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@IocBean
public class ApiService {
    @Inject
    private DeviceService device;

    @Inject
    private DriverService driver;

    @Inject
    private Dao dao;

    @GraphQLQuery(name = "device")
    public  Device getDeviceBySno(@GraphQLArgument(name = "sno") String sno){
        System.out.println("maodajun ---> sno:"+sno);
        if(Strings.isBlank(sno)){
            return null;
        }
        Cnd cnd = Cnd.NEW();
        cnd.and("t_iot_devices.sno","=",sno);
        List<Device> devices = dao.queryByJoin(Device.class,"^dept|kind|location|driver|gateway$",cnd);
        Iterator<Device> it = devices.iterator();
        if(it.hasNext()){
            Device device =  it.next();
            System.out.println(Json.toJson(device));
            return devices.get(0);
        }
        return null;
    }
    @GraphQLQuery(name="normals")
    public List<Normal> getDriver(@GraphQLContext Driver driver) {
        Cnd cnd = Cnd.NEW();
        cnd.and("driverid","=",driver.getId());
        List<Normal> result =dao.queryByJoin(Normal.class,"^grades|driver$",cnd);
        return result;
    }

    @GraphQLQuery(name="rules")
    public List<Ruler> getDriver(@GraphQLContext Grade grade) {
        Cnd cnd = Cnd.NEW();
        cnd.and("gradeid","=",grade.getId());
        List<Ruler> result =dao.queryByJoin(Ruler.class,"^grade$",cnd);
        return result;
    }

}
