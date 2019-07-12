package cn.tico.iot.configmanger.iot.models.device;

import cn.tico.iot.configmanger.iot.models.DeviceEnvModel;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;


@Data
@Table("t_iot_devices")
public class Device extends DeviceEnvModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "sno", description = "机器码")
    private String sno;

    @Column("price")
    @Comment("价格")
    @ColDefine(type = ColType.FLOAT ,width = 20 ,precision = 2 )
    @GraphQLQuery(name = "price", description = "价格")
    private Double price;


    @Column("order_time")
    @Comment("购买日期")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "order_time", description = "购买日期")
    private String orderTime;



    @Column("quality")
    @Comment("使用年限")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "quality", description = "使用年限")
    private Integer quality;

    @Column("discard_time")
    @Comment("报废时间")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "discard_time", description = "报废时间")
    private String discardTime;


    /**
     * 网关
     */
    @Column("gateway_id")
    @Comment("网关")
    private String gatewayid;

    /**
     * 所属网关，SNO
     */
    @Column("gateway_extsno")
    @Comment("冗余的SNO")
    private String gatewayExtsno;

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
