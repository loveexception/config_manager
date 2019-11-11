package cn.tico.iot.configmanger.module.iot.models.device;

import cn.tico.iot.configmanger.common.utils.excel.annotation.ExcelField;
import cn.tico.iot.configmanger.module.iot.models.DeviceEnvModel;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
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
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "sno", description = "机器码")
    @ExcelField(title="sno",value="sno")
    private String sno;

    @Column("price")
    @Comment("价格")
    @ColDefine(type = ColType.FLOAT  )
    @GraphQLQuery(name = "price", description = "价格")
    @ExcelField(title="价格",value = "")

    private Double price;


    @Column("order_time")
    @Comment("购买日期")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "order_time", description = "购买日期")
    @ExcelField(title="购买日期")
    private String orderTime;



    @Column("quality")
    @Comment("使用年限")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "quality", description = "使用年限")
    @ExcelField(title="使用年限")
    private Integer quality;

    @Column("discard_time")
    @Comment("报废时间")
    @ColDefine(type = ColType.INT, width = 32)
    @GraphQLQuery(name = "discard_time", description = "报废时间")
    @ExcelField(title="报废时间")
    private String discardTime;

    @Column("asset_status")
    @Comment("资产状态,0,1,2")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "asset_status", description = "资产状态")
    @ExcelField(title="资产状态")
    private String assetStatus ="0";


    @Column("alert_status")
    @Comment("告警状态")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "alert_status", description = "告警状态")
    @ExcelField(title="告警状态")
    private String alertStatus="normal";

    /**
     * 网关
     */
    @Column("gateway_id")
    @Comment("网关")
    @ExcelField(title="所属网关")
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
    @ExcelField(title = "驱动名称")
    private String driverid;


    @One(field = "driverid",key="id")
    @GraphQLQuery(name = "driver", description = "驱动")
    private Driver driver;


    @Many(field = "deviceid")
    @GraphQLQuery(name = "persons", description = "个性化规则")
    private List<Person> persons;

    @Many(field = "deviceid",key="id" )
    @GraphQLQuery(name="next",description = "检修")
    private List<Owner> next;





}
