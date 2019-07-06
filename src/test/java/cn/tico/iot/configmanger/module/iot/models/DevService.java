package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.device.Gateway;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import io.leangen.graphql.annotations.*;

import java.util.*;

public class DevService {

    @GraphQLQuery(name = "dev")
    public Device getDev(@GraphQLArgument(name = "sno") String sno) {
        System.out.println("maodajun ---> sno:"+sno);
        if(sno.equals("")){
            return null;
        }

        GraphQLTest t = new GraphQLTest();
        List<Device> list = new ArrayList();
        list.add(t.json());
        return t.json();
    }

    @GraphQLQuery(name="dept2")
    public Driver getDriver(@GraphQLContext Device dev) {

        return dev.getDriver();

    }

    @GraphQLQuery(name="depts")
    public String getDeps(@GraphQLContext Driver driver) {

        return "ytre";

    }

    @GraphQLQuery(name="deptArray")
    public String[] getDepArray(@GraphQLContext Driver driver) {

        return new String[]{"ytre","ppp"};

    }
    @GraphQLQuery(name="deptMap")
    public Gateway getDepMap(@GraphQLContext Driver driver) {
        Gateway kind = new Gateway();
        kind.setSno("6543");


        return kind;

    }

    @GraphQLQuery(name="max")
    public List<Gateway> getMax(@GraphQLContext Driver driver) {
        Gateway gateway = new Gateway();
        gateway.setSno("6543");


        return Arrays.asList(gateway,gateway);

    }
    @GraphQLQuery(name="devdri")
    public List<String> devdri(@GraphQLContext Driver driver, @GraphQLArgument(name = "sno") String sno) {
        Device device = new Device();
       // device =dev;

        return Arrays.asList(driver.toString(),sno);

    }

}