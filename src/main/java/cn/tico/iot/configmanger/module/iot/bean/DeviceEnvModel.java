package cn.tico.iot.configmanger.module.iot.bean;

import cn.tico.iot.configmanger.module.iot.bean.FatherModel;
import cn.tico.iot.configmanger.module.iot.models.Kind;
import cn.tico.iot.configmanger.module.iot.models.Location;
import cn.tico.iot.configmanger.module.iot.models.Tag;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;

import java.util.List;

@Data
public class DeviceEnvModel extends I18NModel {


    /**
     * SNO 机器码
     */
    @Column("sno")
    @Comment("机器码")
    @GraphQLQuery(name = "sno", description = "机器码")
    private String sno;

    /**
     * 类型
     */
    @Column("kind_id")
    @Comment("类型")
    private String kindid;

    @One(field = "kindid",key = "id")
    private Kind kind;

    private List<Kind> kinds;


    /**
     * 业务标签
     */
    @ManyMany(relation ="t_iot_tag_dev",from = "dev_id",to="tag_id")
    private List<Tag> tags ;

    /**
     * ip
     */
    @Column("ip")
    @Comment("ip ")

    private String ip;
    /**
     * 采集间隔
     */
    @Column("cycel")
    @Comment("采集间隔")
    private int cycle;

    /**
     * 单位 默认 ms
     */
    @Column("unit")
    @Comment("单位")
    private String unit="ms";

    /**
     * 用户名
     */
    @Column("username")
    @Comment("用户名")
    private String username="";

    /**
     * 密码
     */
    @Column("password")
    @Comment("密码")
    private String password="";
    /**
     * 组织
     */
    @Column("dept_id")
    @Comment("组织")
    private String deptid;



    @One(field = "deptid",key = "id")
    private Dept dept;
    /**
     * 地域
     */
    @Column("location_id")
    @Comment("地域")
    private String locationid;

    @One(field = "locationid",key="id")
    private Location location;

    private List<Location> locations;


    public DeviceEnvModel getEnv(){
        DeviceEnvModel env =  new DeviceEnvModel();
        env.setIp(this.ip);
        env.setCycle(this.cycle);
        env.setUnit(this.unit);
        env.setUsername(this.username);
        env.setPassword(this.password);
        return env;
    }


}
