package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Person;
import cn.tico.iot.configmanger.module.mao.common.BaseGrap;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.List;


@IocBean
public class PersonGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public Person person(@GraphQLContext Device device) {
        Cnd cnd = useNormalCnd("");
        cnd.and("device_id","=",device.getId());
        List<Person> people =  dao.query(Person.class,cnd);
        if(Lang.isEmpty(people)){
            return null;
        }
        return people.iterator().next();
    }

    public Cnd useNormalCnd(String tableName) {

        Cnd cnd =  Cnd.NEW();



        cnd.and(tableName+"delflag","=","false");

        return cnd ;
    }



}