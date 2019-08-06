package cn.tico.iot.configmanger.module.iot.graphql;

import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Person;
import cn.tico.iot.configmanger.module.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.module.iot.models.device.PersonRuler;
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
            // Device device =  it.next();
            return devices.get(0);
        }
        return null;
    }


    @GraphQLQuery(name="normals")
    public List<Normal> getDriver(@GraphQLContext Driver driver) {
        Cnd cnd = Cnd.NEW();
        cnd.and("driverid","=",driver.getId());
        cnd.orderBy("order_num","asc");
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
    @GraphQLQuery(name="person")
    public Person onePerson(@GraphQLContext Normal normal,@GraphQLArgument(name = "sno") String sno) {
        List<Device> devices =dao .query(Device.class,Cnd.NEW().and("sno","=",sno));
        if(Lang.isEmpty(devices)){
            return null;
        }
        Device device = devices.get(0);

        Cnd cnd = Cnd.NEW();
        cnd.and("normalid","=",normal.getId());
        cnd.and("deviceid","=",device.getId());
        List<Person> persons = dao.query(Person.class,cnd);
        if(Lang.isEmpty(persons)){
            return null;
        }
        return persons.get(0);
    }
    @GraphQLQuery(name="grades")
    public List<PersonGrade> gradesByPerson(@GraphQLContext Person person){
        Cnd cnd = Cnd.NEW();
        cnd.and("person_id","=",person.getId());
        return dao.queryByJoin(PersonGrade.class,"^rulers$",cnd);
    }
}
