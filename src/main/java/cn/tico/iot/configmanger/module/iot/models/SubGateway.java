package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.module.iot.bean.DeviceEnvModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

@Data
@Table("t_iot_sub_gateways")
public class SubGateway  {

    @Name
    @Column("id")
    @Comment
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @Prev(els = {@EL("uuid()")})
    @GraphQLQuery
    private String id;

    /**
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "sno", description = "机器码")
    private String sno;


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
