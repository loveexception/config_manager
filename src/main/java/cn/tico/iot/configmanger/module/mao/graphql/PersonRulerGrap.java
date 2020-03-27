package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Person;
import cn.tico.iot.configmanger.module.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.module.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.module.mao.common.BaseGrap;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;


@IocBean
public class PersonRulerGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public List<PersonRuler> subRulers(@GraphQLContext PersonGrade grade) {
        Cnd cnd = useNormalCnd("");
        cnd.and("grade_id","=",grade.getId());
        return dao.query(PersonRuler.class,cnd);
    }

    @GraphQLQuery
    public List<PersonRuler> personRulers(@GraphQLContext Device device) {
        SimpleCriteria simpleCriteria = new SimpleCriteria(
                "INNER JOIN t_iot_person_grade grade on t_iot_person_rulers.grade_id = grade.id "
                        +"INNER JOIN t_iot_persons person on grade.person_id = person.id "
        );
        simpleCriteria.where().and("person.device_id","=",device.getId());
        return dao.query(PersonRuler.class,simpleCriteria);
    }

    @GraphQLQuery
    public Normal tie(@GraphQLContext PersonRuler ruler) {
        SimpleCriteria simpleCriteria = new SimpleCriteria(
                " INNER JOIN t_iot_persons person on t_iot_normals.id = person.normal_id " +
                        " INNER JOIN t_iot_person_grade grade ON grade.person_id = person.id " +
                        " INNER JOIN t_iot_person_rulers ruler ON ruler.grade_id = grade.id "
        );

        simpleCriteria.where().and("ruler.id","=",ruler.getId());

        return dao.query(Normal.class,simpleCriteria).iterator().next();
    }



    @GraphQLQuery
    public PersonGrade level(@GraphQLContext PersonRuler ruler) {

        return dao.fetch(PersonGrade.class,ruler.getGradeid());
    }

    public Cnd useNormalCnd(String tableName) {
        Cnd cnd =  Cnd.NEW();
        cnd.and(tableName+"delflag","=","false");
        return cnd ;
    }




}