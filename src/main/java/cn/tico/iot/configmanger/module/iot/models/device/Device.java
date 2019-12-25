package cn.tico.iot.configmanger.module.iot.models.device;

import cn.tico.iot.configmanger.module.iot.models.DeviceEnvModel;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.nutz.dao.entity.annotation.*;
import org.nutz.integration.json4excel.annotation.J4EDateFormat;
import org.nutz.integration.json4excel.annotation.J4EName;

import java.io.Serializable;
import java.util.List;


@Data
@Table("t_iot_devices")
@J4EName("设备")
public class Device extends DeviceEnvModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "sno", description = "机器码")
    @J4EName("sno")
    private String sno;

    @Column("price")
    @Comment("价格")
    @ColDefine(type = ColType.FLOAT  )
    @GraphQLQuery(name = "price", description = "价格")
    @J4EName("价格")

    private Double price;


    @Column("order_time")
    @Comment("购买日期")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "order_time", description = "购买日期")
    @J4EName("购买日期")
    @J4EDateFormat(from = "yyyy-mm-dd",to = "yymmdd")
    private Long orderTime;



    @Column("quality")
    @Comment("使用年限")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "quality", description = "使用年限")
    @J4EName("使用年限")
    private Integer quality;

    @Column("discard_time")
    @Comment("报废时间")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "discard_time", description = "报废时间")
    @J4EName("报废时间")
    @J4EDateFormat(from = "yyyy-mm-dd",to = "yymmdd")

    private Long discardTime;

    @Column("asset_status")
    @Comment("资产状态,0,1,2")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "asset_status", description = "资产状态")
    @J4EName("资产状态")
    private String assetStatus ="0";


    @Column("alert_status")
    @Comment("告警状态")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "alert_status", description = "告警状态")
    @J4EName("告警状态")
    private String alertStatus="normal";

    /**
     * 网关
     */
    @Column("gateway_id")
    @Comment("网关")
    @J4EName("所属网关")
    private String gatewayid;

    /**
     * 所属网关，SNO
     */
    @Column("gateway_extsno")
    @Comment("冗余的SNO")
    private String gatewayExtsno;

    @One(field = "gatewayid",key="id")
    @GraphQLQuery(name = "gateway", description = "网关")
    private Gateway gateway;

    /**
     * 驱动
     */
    @Column("driver_id")
    @Comment("驱动")
    @J4EName( "驱动名称")
    private String driverid;


    @One(field = "driverid",key="id")
    @GraphQLQuery(name = "driver", description = "驱动")
    private Driver driver;


    @Many(field = "deviceid")
    @GraphQLQuery(name = "persons", description = "个性化规则")
    private List<Person> persons;





}
