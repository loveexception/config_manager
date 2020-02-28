package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
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
public class RulerGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public List<Ruler> rulers(@GraphQLContext Grade grade) {
        Cnd cnd = useNormalCnd("");
        cnd.and("grade_id","=",grade.getId());
        return dao.query(Ruler.class,cnd);
    }

    public Cnd useNormalCnd(String tableName) {
        Cnd cnd =  Cnd.NEW();
        cnd.and(tableName+"delflag","=","false");
        return cnd ;
    }

    @GraphQLQuery
    public Ruler ruler(@GraphQLArgument(name="id") String id) {

        return dao.fetch(Ruler.class,id);
    }


}