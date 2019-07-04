package cn.tico.iot.configmanger.module.iot.bean;

import cn.tico.iot.configmanger.module.iot.models.Kind;
import cn.tico.iot.configmanger.module.iot.models.Location;
import cn.tico.iot.configmanger.module.iot.models.Normal;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
public class DriverModel extends I18NModel{

    /**
     * 地域
     */
    @Column("kind_id")
    @Comment("地域")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String kindid;

    @Column("kind_kind")
    @Comment("大类 冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String kindKind;

    @Column("kind_subkind")
    @Comment("子类 冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String kindSubkind;

    @Column("kind_company")
    @Comment("公司冗用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String kindCompany;

    @Column("kind_type")
    @Comment("类型冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String kindType;

    @Column("kind_ver")
    @Comment("硬件版本 名冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String kindVer;




    @One(field = "kindid",key="id")
    private Kind kind;

    private List<Kind> kinds;



    /**
     * 类型
     */
    @Column("driver_ver")
    @Comment("驱动版本")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String driverVer;




}
