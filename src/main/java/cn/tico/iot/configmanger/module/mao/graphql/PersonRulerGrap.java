package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.module.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.module.mao.common.BaseGrap;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;


@IocBean
public class PersonRulerGrap extends BaseGrap {


    @Inject
    private Dao dao;


    @GraphQLQuery
    public List<PersonRuler> rulers(@GraphQLContext PersonGrade grade) {
        Cnd cnd = useNormalCnd("");
        cnd.and("grade_id","=",grade.getId());
        return dao.query(PersonRuler.class,cnd);
    }

    public Cnd useNormalCnd(String tableName) {
        Cnd cnd =  Cnd.NEW();
        cnd.and(tableName+"delflag","=","false");
        return cnd ;
    }




}