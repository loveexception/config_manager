package cn.tico.iot.configmanger.module.iot.models.Topo;


import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.sys.models.Dept;
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


    @Column("gateway_id")
    @Comment("设备所在的GATEWAY信息")
    public String gatewayId;

    @One(field = "gatewayId",key = "id")
    public Gateway gateway;


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

    @ManyMany(relation = "t_topo_dev",from = "base_id",to = "device_id")
    public List<Device> devices;


}
