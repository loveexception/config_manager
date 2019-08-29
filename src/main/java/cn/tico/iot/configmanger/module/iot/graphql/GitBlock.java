package cn.tico.iot.configmanger.module.iot.graphql;


import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import lombok.Data;
import org.nutz.ioc.loader.annotation.IocBean;

@Data
@IocBean
public class GitBlock {


    public Gateway createProject(Gateway gateway) {


        return gateway;
    }
}
