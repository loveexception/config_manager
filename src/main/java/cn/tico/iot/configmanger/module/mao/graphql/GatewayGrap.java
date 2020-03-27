package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;


@IocBean
public class GatewayGrap {


    @Inject
    private Dao dao;

    @GraphQLQuery
    public Gateway gateway(@GraphQLContext Device device) {
        return gateway(device.getGatewayid());
    }
    @GraphQLQuery
    public Gateway gateway(@GraphQLArgument(name="id") String id) {
        return dao.fetch(Gateway.class,id);
    }


}