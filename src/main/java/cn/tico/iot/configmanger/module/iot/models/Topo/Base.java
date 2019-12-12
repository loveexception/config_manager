package cn.tico.iot.configmanger.module.iot.models.Topo;


import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_topo_bases")
@Comment("拓扑图存储")
public class Base extends I18NModel{


    @Column("graph")
    @Comment("对外显示拓扑图内容")
    @ColDefine(type = ColType.TEXT)
    public String graph;


    @Column("kind_id")
    @Comment("设备类型信息")
    public String kindId;


    @One(field = "kindId",key = "id")
    public Kind kind;


    @Column("location_id")
    @Comment("设备地址信息")
    public String locationId;


    @One(field = "locationId",key = "id")
    public Location location;

    @Column("hide_tag_id")
    @Comment("隐藏的设备Tag")
    public String hideTagId;
    @One(field = "hideTagId",key = "id")
    public Tag hide;


    @Column("show_tag_id")
    @Comment("用于选择的设备Tag")
    public String showTagId;
    @One(field = "showTagId",key = "id")
    public Tag show;


    @Column("driver_id")
    @Comment("设备所使用的驱动信息")
    public String driverId;

    @One(field = "driverId",key = "id")
    public Driver driver;

    @Column("dept_id")
    @Comment("公司信息")
    public String deptId;

    @One(field = "deptId",key = "id")
    public Dept dept;

    @Many(field = "baseId",key = "id")
    public List<Topo> topos;

//    @ManyMany(relation = "t_topo_dev",from = "base_id",to = "device_id")
//    public List<Device> devices;

}
