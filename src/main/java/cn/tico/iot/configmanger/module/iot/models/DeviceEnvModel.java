package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.common.utils.excel.annotation.ExcelField;
import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.nutz.integration.json4excel.annotation.J4EName;
import org.nutz.lang.util.NutMap;

import java.util.List;
import java.util.Map;

@Data
public class DeviceEnvModel extends I18NModel {




    /**
     * 类型
     */
    @Column("kind_id")
    @Comment("类型")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @J4EName( "型号")
    private String kindid;

    @Column("kind_map")
    @Comment("类型冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "kindmap", description = "全类图")
    @J4EName( "型号全长")
    private String kindmap;

    @One(field = "kindid",key = "id")
    @GraphQLQuery(name = "kind", description = "类型")
    private Kind kind;


    private List<Kind> kinds;


    /**
     * 业务标签
     */
    @ManyMany(relation ="t_tag_dev",from = "dev_id",to="tag_id")
    @GraphQLQuery(name = "tags", description = "全业务")
    private List<Tag> tags ;

    /**
     * ip
     */
    @Column("ip")
    @Comment("ip ")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @J4EName( "IP地址")
    private String ip;

    /**
     * 采集间隔
     */
    @Column("cycle")
    @Comment("采集间隔")
    @J4EName( "采集间隔")

    private int cycle;

    /**
     * 单位 默认 ms
     */
    @Column("unit")
    @Comment("单位")

    @ColDefine(type = ColType.VARCHAR, width = 32)
    @J4EName( "单位")

    private String unit="ms";

    /**
     * 用户名
     */
    @Column("username")
    @Comment("用户名")

    @ColDefine(type = ColType.VARCHAR, width = 255)
    @J4EName( "用户名")

    private String username;

    /**
     * 密码
     */
    @Column("password")
    @Comment("密码")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @J4EName( "密码")

    private String password;
    /**
     * 组织
     */
    @Column("dept_id")
    @Comment("组织")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @J4EName( "所属组织")
    private String deptid;


    @One(field = "deptid",key = "id")
    @GraphQLQuery(name = "dept", description = "所属公司")
    private Dept dept;

    /**
     * 地域
     */
    @Column("location_id")
    @Comment("地域")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @J4EName("位置信息")
    private String locationid;

    @Column("location_country")
    @Comment("地域国 冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String locationCountry;

    @Column("location_state")
    @Comment("地域 省冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String locationState;

    @Column("location_city")
    @Comment("地域市余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String locationCity;

    @Column("location_company")
    @Comment("地域公司冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String locationCompany;

    @Column("location_room")
    @Comment("地域 会议室 名冗余用于查寻")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String locationRoom;


    @Column("location_map")
    @Comment("地域ID 用于查寻")
    @ColDefine(type = ColType.TEXT, width = 255)
    @GraphQLQuery(name = "locationmap", description = "全类图")
    @J4EName( "位置信息")
    private String locationMap;


    @One(field = "locationid",key="id")
    @GraphQLQuery(name = "location", description = "地理位置")
    private Location location;


    private List<Location> locations;

    @GraphQLQuery(name = "env", description = "运行时数据")
    public Map<String,Object> getEnv(){
        DeviceEnvModel env =  new DeviceEnvModel();
        env.setIp(this.ip);
        env.setCycle(this.cycle);
        env.setUnit(this.unit);
        env.setUsername(this.username);
        env.setPassword(this.password);
        NutMap result = NutMap.NEW();
        result
            .addv("ip",this.ip)
            .addv("cycle",this.cycle)
            .addv("unit",this.unit)
            .addv("username",this.username)
            .addv("password",this.password);


        return result;
    }


}
