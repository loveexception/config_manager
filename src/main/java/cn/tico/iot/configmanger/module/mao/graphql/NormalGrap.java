package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.module.mao.common.BaseGrap;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;


@IocBean
public class NormalGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public List<Normal> normals(@GraphQLContext Driver driver) {
        Cnd cnd = useNormalCnd("");
        cnd.and("driver_id","=",driver.getId());
        return dao.query(Normal.class,cnd);
    }

    public Cnd useNormalCnd(String tableName) {

        Cnd cnd =  Cnd.NEW();



        cnd.and(tableName+"delflag","=","false");

        return cnd ;
    }

    @GraphQLQuery
    public Normal normal(@GraphQLArgument(name="id") String id) {

        return dao.fetch(Normal.class,id);
    }
    @GraphQLQuery
    public Normal normal(@GraphQLContext Ruler ruler) {

        return normal(ruler.getNormalid());
    }
    @GraphQLQuery
    public Normal normal(@GraphQLContext PersonRuler ruler) {

        return normal(ruler.getNormalid());
    }
    @GraphQLQuery
    public Normal normal(@GraphQLContext Grade grade) {

        return normal(grade.getNormalid());
    }
//    @GraphQLQuery
//    public Normal normal(@GraphQLContext PersonGrade grade) {
//
//        return normal(grade.getNormalid());
//    }
}