package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.Person;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;


@IocBean
public class DeptGrap {

    @Inject
    private Dao dao;

    @GraphQLQuery
    public Dept dept(@GraphQLContext Device device) {

        return dept(device.getDeptid());
    }
    @GraphQLQuery
    public Dept dept(@GraphQLContext Gateway gateway) {

        return dept(gateway.getDeptid());
    }
    @GraphQLQuery
    public Dept dept(@GraphQLContext Location location) {

        return dept(location.getDeptid());
    }
    @GraphQLQuery
    public Dept dept(@GraphQLContext Tag tag) {

        return dept(tag.getDeptid());
    }
    @GraphQLQuery
    public Dept dept(@GraphQLArgument(name="id") String id) {

        return dao.fetch(Dept.class ,id);
    }


}