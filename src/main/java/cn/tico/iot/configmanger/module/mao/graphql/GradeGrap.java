package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.mao.common.BaseGrap;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;


@IocBean
public class GradeGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public List<Grade> grades(@GraphQLContext Normal normal) {
        Cnd cnd = useNormalCnd("");
        cnd.and("normal_id","=",normal.getId());
        return dao.query(Grade.class,cnd);
    }
    @GraphQLQuery
    public List<Grade> grades(@GraphQLContext Driver driver) {
        Criteria criteria = Cnd.cri();
        criteria.where().andInBySql("normal_id", " select id from t_iot_normals where driver_id = '%s'", driver.getId());
        //criteria.where().and("driver_id","=",driver.getId());
        return dao.query(Grade.class,criteria);
    }
    public Cnd useNormalCnd(String tableName) {
        Cnd cnd =  Cnd.NEW();
        cnd.and(tableName+"delflag","=","false");
        return cnd ;
    }

    @GraphQLQuery
    public Normal grade(@GraphQLArgument(name="id") String id) {
        return dao.fetch(Normal.class,id);
    }


}