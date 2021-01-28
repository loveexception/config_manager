package cn.tico.iot.configmanger.module.iot.models.device;

import cn.tico.iot.configmanger.module.iot.models.DeviceEnvModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.Lang;

@Data
@Table("gateway_manage.t_iot_gateways")
public class Gateway extends DeviceEnvModel {

    /**
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @GraphQLQuery(name = "sno", description = "机器码")
    private String sno;


    /**
     *
     *  GIT 地址
     *
     *
     */
    @Column("git_path")
    @Comment("GIT 项目 地址")
    @ColDefine(type = ColType.TEXT)
    @GraphQLQuery(name = "git_path", description = "项目地址")
    private String gitPath;

    /**
     *
     *  描述
     *
     */
    @Column("desription")
    @Comment("描述")
    @ColDefine(type = ColType.TEXT)
    @GraphQLQuery(name = "desription", description = "描述")
    private String desription;

    @Column("subgateway_id")
    @Comment("绑定表")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String subid;

    @One(field = "subid",key="id")
    @GraphQLQuery(name = "subgateway", description = "注册绑定")
    private SubGateway subGateway;

    public String isHaveSno(){
        if(Lang.isEmpty(subGateway)){
            return "";
        }

        return subGateway.getExtSno();


    }




}
