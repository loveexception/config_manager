package cn.tico.iot.configmanger.module.iot.models;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mapl.Mapl;

import java.util.*;

public class DevService {

    @GraphQLQuery(name = "dev")
    public Device getDev(@GraphQLArgument(name = "sno") String sno) {
        System.out.println("maodajun ---> sno:"+sno);
        if(sno.equals("")){
            return null;
        }

        DevTest t = new DevTest();
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

}