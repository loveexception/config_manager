package cn.tico.iot.configmanger.module.iot.models.device;

import cn.tico.iot.configmanger.module.iot.bean.CommonModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

@Data
@Table("gateway_manager.t_iot_sub_gateways")
public class SubGateway extends CommonModel {



    /**
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "sno", description = "机器码")
    private String sno;

    /**
     * SNO  外网IP
     */
    @Column("ext_ip")
    @Comment("外网IP")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "ext_ip", description = "外网IP")
    private String extip;

    /**
     * SNO  内网IP
     */
    @Column("ip")
    @Comment("内网IP")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "ip", description = "内网IP")
    private String ip;

    /**
     * contrl_device_api_port  外网控制端口
     */
    @Column("port")
    @Comment("外网控制端口")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "port", description = "外网控制端口")
    private String port;

    /**
     * contrl_device_api_uri  外网控制接口
     */
    @Column("api")
    @Comment("外网控制接口")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "api", description = "外网控制接口")
    private String api;







    /**
     * SNO 生成机器码
     */
    @Column("ext_sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "ext_sno", description = "生成机器码")
    private String extSno;


    /**
     * gwid
     */
    @Column("gw_id")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "gateway_id", description = "生成机器码")
    private String gwid;



    @One(field = "gwid",key="id")
    @GraphQLQuery(name = "gateway", description = "生成机器码")
    private Gateway gateway;







}
