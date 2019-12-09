package cn.tico.iot.configmanger.module.iot.graphql;

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
import org.nutz.boot.starter.caffeine.Cache;
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
public class ApiService {
    @Inject
    private DeviceService device;

    @Inject
    private DriverService driver;

    @Inject
    private Dao dao;

    @GraphQLQuery(name = "device")
    public Device getDeviceBySno(@GraphQLArgument(name = "sno") String sno) {
        System.out.println("maodajun ---> sno:" + sno);
        if (Strings.isBlank(sno)) {
            return null;
        }
        Cnd cnd = Cnd.NEW();
        cnd.and("t_iot_devices.sno", "=", sno);
        cnd.and("t_iot_devices.delflag","=","false");
        cnd.and("t_iot_devices.asset_status", "=", "2");
        List<Device> devices = dao.queryByJoin(Device.class, "^dept|kind|location|driver|gateway|tags$", cnd);
        Iterator<Device> it = devices.iterator();
        if (it.hasNext()) {
            return devices.get(0);
        }
        return null;
    }


    @GraphQLQuery(name = "normals")
    public List<Normal> getNormals(@GraphQLContext Driver driver) {
        Cnd cnd = Cnd.NEW();
        cnd.and("driver_id", "=", driver.getId());
        cnd.and("delflag","=","false");

        cnd.orderBy("order_num", "asc");

        List<Normal> result = dao.query(Normal.class, cnd);
        return result;
    }



    @GraphQLQuery(name = "kinds")
    public List<Kind> getKinds(@GraphQLContext Device device) {
        Kind kind = dao.fetch(Kind.class, device.getKindid());
        String ids[] = kind.getAncestors().split(",");
        Cnd cnd = Cnd.NEW();
        cnd.and("id", "in", ids);
        cnd.and("level", ">", "0");
        cnd.and("delflag","=","false");

        cnd.orderBy("level", "asc");

        List<Kind> result = dao.query(Kind.class, cnd);
        result.add(kind);
        return result;
    }

    @GraphQLQuery(name = "locations")
    public List<Location> getlocations(@GraphQLContext Device device) {
        Location location = dao.fetch(Location.class, device.getLocationid());
        String ids[] = location.getAncestors().split(",");
        Cnd cnd = Cnd.NEW();
        cnd.and("id", "in", ids);
        cnd.and("level", ">", "0");
        cnd.and("delflag","=","false");

        cnd.orderBy("level", "asc");
        List<Location> result = dao.query(Location.class, cnd);
        result.add(location);
        return result;
    }

    @GraphQLQuery(name = "persons")
    public List<Person> getPersons(@GraphQLContext Device device) {
        Cnd cnd = Cnd.NEW();
        cnd.and("t_iot_persons.device_id", "=", device.getId());
        cnd.and("t_iot_persons.delflag","=","false");

        List<Person> result = dao.queryByJoin(Person.class, "^person|grades|device|normal$", cnd);
        return result;
    }

    @GraphQLQuery(name = "person")
    public Person onePerson(@GraphQLContext Normal normal, @GraphQLArgument(name = "sno") String sno) {
        List<Device> devices = dao.query(Device.class
                , Cnd.NEW()
                        .and("sno", "=", sno)
                        .and("delflag","=","false"));
        if (Lang.isEmpty(devices)) {
            return null;
        }
        Device device = devices.get(0);

        Cnd cnd = Cnd.NEW();
        cnd.and("normal_id", "=", normal.getId());
        cnd.and("device_id", "=", device.getId());
        cnd.and("delflag","=","false");

        List<Person> persons = dao.query(Person.class, cnd);
        if (Lang.isEmpty(persons)) {
            return null;
        }
        return persons.get(0);
    }

    @GraphQLQuery(name = "grades")
    public List<PersonGrade> gradesByPerson(@GraphQLContext Person person) {
        Cnd cnd = Cnd.NEW();
        cnd.and("person_id", "=", person.getId());
        cnd.and("delflag","=","false");

        List<PersonGrade> result = dao.query(PersonGrade.class, cnd);
        return result;

    }

    @GraphQLQuery(name = "grades")
    public List<Grade> getGrades(@GraphQLContext Normal normal) {
        Cnd cnd = Cnd.NEW();
        cnd.and("normal_id", "=", normal.getId());
        cnd.and("delflag","=","false");

        List<Grade> result = dao.query(Grade.class, cnd);
        return result;
    }

    @GraphQLQuery(name = "rulers")
    public List<Ruler> getDriver(@GraphQLContext Grade grade) {
        Cnd cnd = Cnd.NEW();
        cnd.and("t_iot_rulers.grade_id", "=", grade.getId());
        cnd.and("t_iot_rulers.delflag","=","false");

        List<Ruler> result = dao.queryByJoin(Ruler.class, "^normal$", cnd);
        return result;
    }


    @GraphQLQuery(name = "rulers")
    public List<PersonRuler> getPersonRulers(@GraphQLContext PersonGrade grade) {
        Cnd cnd = Cnd.NEW();
        cnd.and("t_iot_person_rulers.grade_id", "=", grade.getId());
        cnd.and("t_iot_person_rulers.delflag","=","false");

        List<PersonRuler> result = dao.queryByJoin(PersonRuler.class,"^normal$", cnd);
        return result;
    }

    /**
     * 这里是EXT SNO 入口
     * @param extsno
     * @return
     */
    @GraphQLQuery(name = "subgateway")
    public SubGateway subGateway(@GraphQLArgument(name = "extsno") String extsno) {
        Logs.get().infof("subgateway start by extsno:%s",extsno);
        if (Strings.isBlank(extsno)) {
            return null;
        }
        Cnd cnd = Cnd.NEW();
        cnd.and("ext_sno", "=", extsno);
        List<SubGateway> subGateways = dao.queryByJoin(SubGateway.class, "^gateway$", cnd);
        for(SubGateway sub : subGateways){
            return sub;
        }
        return null;
    }



    /**
     * 从GATE WAY 到 SUBGATEWAY
     * @param gateway
     * @return
     */
    @GraphQLQuery(name = "subgateway")
    public SubGateway getSubGateWay(@GraphQLContext Gateway gateway) {
        if(Lang.isEmpty(gateway)){
            return null;
        }
        if(Strings.isEmpty(gateway.getSubid())){
            return null;
        }

        SubGateway subGateway = dao.fetch(SubGateway.class,gateway.getSubid());
        if(Lang.isEmpty(subGateway)){
            return null;
        }


        return subGateway;
    }
    @GraphQLQuery(name = "devices")
    public List<Device>  gateWayDevices(@GraphQLContext Gateway gateway) {
        if(Lang.isEmpty(gateway)){
            return null;
        }
        if(Strings.isEmpty(gateway.getId())){
            return null;
        }
        Cnd cnd = Cnd.NEW();
        cnd .and("gateway_id","=",gateway.getId());

        List<Device> devices = dao.query(Device.class,cnd);


        return devices;
    }
}