package cn.tico.iot.configmanger.iot.graphql;

import cn.tico.iot.configmanger.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.monitor.entity.Sys;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

@IocBean
public class GatewayBlock implements Block {


    @Override
    public void exec(String topic, String key, String value, long offset) {
        System.out.println(value);
        System.out.println(Json.fromJson(SubGateway.class,value));

    }
}
