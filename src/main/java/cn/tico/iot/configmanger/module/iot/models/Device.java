package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.module.iot.bean.DeviceEnvModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;


@Data
@Table("t_iot_devices")
public class Device extends DeviceEnvModel implements Serializable {

    private static final long serialVersionUID = 1L;












    /**
     * 网关
     */
    @Column("gateway_id")
    @Comment("网关")
    private String gatewayid;

    @One(field = "gatewayid",key="id")
    private Gateway gateway;

    /**
     * 驱动
     */
    @Column("driver_id")
    @Comment("驱动")
    private String driverid;

    @One(field = "driverid",key="id")
    private Driver driver;








}
