package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.module.mao.common.BaseGrap;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;


@IocBean
public class RulerGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public List<Ruler> subRulers(@GraphQLContext Grade grade) {
        Cnd cnd = Cnd.NEW();
        cnd.and("grade_id","=",grade.getId());
        return dao.query(Ruler.class,cnd);
    }
    @GraphQLQuery
    public List<Ruler> rulers(@GraphQLContext Driver driver) {
        SimpleCriteria simpleCriteria = new SimpleCriteria(
                "INNER JOIN t_iot_grades grade on grade_id = grade.id "
                        +"INNER JOIN t_iot_normals normal on grade.normal_id = normal.id "
        );

        simpleCriteria.where().and("driver_id","=",driver.getId());

        return dao.query(Ruler.class,simpleCriteria);
    }


    @GraphQLQuery
    public Normal tie(@GraphQLContext Ruler ruler) {
        SimpleCriteria simpleCriteria = new SimpleCriteria(
                "INNER JOIN t_iot_grades grade on grade.normal_id = t_iot_normals.id " +
                        "INNER JOIN t_iot_rulers ruler on ruler.grade_id = grade.id "
        );

        simpleCriteria.where().and("ruler.id","=",ruler.getId());

        return dao.query(Normal.class,simpleCriteria).iterator().next();
    }



    @GraphQLQuery
    public Grade level(@GraphQLContext Ruler ruler) {

        return dao.fetch(Grade.class,ruler.getGradeid());
    }



    @GraphQLQuery
    public Ruler ruler(@GraphQLArgument(name="id") String id) {

        return dao.fetch(Ruler.class,id);
    }


}