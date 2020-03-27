package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.DriverService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Logs;

import java.util.Iterator;
import java.util.List;


@IocBean
public class DriverGrap {

    @Inject
    private Dao dao;

    @GraphQLQuery
    public Driver driver(@GraphQLContext Device device) {

        return driver(device.getDriverid());
    }
    @GraphQLQuery
    public Driver driver(@GraphQLArgument(name="id") String id) {

        return dao.fetch(Driver.class ,id);
    }


}