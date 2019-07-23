package cn.tico.iot.configmanger.iot.graphql;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.base.Location;
import cn.tico.iot.configmanger.iot.models.base.Tag;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
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
        List<Device> devices = dao.queryByJoin(Device.class,"^dept|kind|location|driver|gateway|tags$",cnd);
        Iterator<Device> it = devices.iterator();
        if(it.hasNext()){
            Device device =  it.next();
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
        List<Ruler> result =dao.queryByJoin(Ruler.class,"^grade|normal$",cnd);
        return result;
    }


    @GraphQLQuery(name="persons")
    public List<Person> getPersons(@GraphQLContext Device device) {
        Cnd cnd = Cnd.NEW();
        cnd.and("deviceid","=",device.getId());
        List<Person> result =dao.queryByJoin(Person.class,"^personGrades|device|normal$",cnd);
        return result;
    }
    @GraphQLQuery(name="personRulers")
    public List<PersonRuler> getPersonRulers(@GraphQLContext PersonGrade grade) {
        Cnd cnd = Cnd.NEW();
        cnd.and("gradeid","=",grade.getId());
        List<PersonRuler> result =dao.query(PersonRuler.class,cnd);
        return result;
    }

    @GraphQLQuery(name="kinds")
    public List<Kind> getKinds(@GraphQLContext Device device) {
        Kind kind = dao.fetch(Kind.class,device.getKindid());
        String ids[] = kind.getAncestors().split(",");
        Cnd cnd = Cnd.NEW();
        cnd.and("id","in",ids);
        cnd.and("level",">","0");
        cnd.orderBy("level","asc");

        List<Kind> result =dao.query(Kind.class,cnd);
        result.add(kind);
        return result;
    }
    @GraphQLQuery(name="locations")
    public List<Location> getlocations(@GraphQLContext Device device) {
        Location location = dao.fetch(Location.class,device.getLocationid());
        String ids[] = location.getAncestors().split(",");
        Cnd cnd = Cnd.NEW();
        cnd.and("id","in",ids);
        cnd.and("level",">","0");

        cnd.orderBy("level","asc");
        List<Location> result =dao.query(Location.class,cnd);
        result.add(location);
        return result;
    }

}
