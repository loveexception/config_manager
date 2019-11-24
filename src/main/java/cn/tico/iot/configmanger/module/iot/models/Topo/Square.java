package cn.tico.iot.configmanger.module.iot.models.Topo;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import com.google.common.collect.Maps;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.Lang;

import java.util.List;
import java.util.Map;

@Data
@Table("t_topo_squares")
@Comment("拓扑图中的方块")
public class Square extends I18NModel{

    @Column("topo_id")
    @Comment("拓扑图")
    public String topoId;

    @One(field = "topoId",key = "id")
    public Topo topo;

    @Column("sno")
    @Comment("机器码")
    public String sno;

    @One(field = "deviceId",key = "id")
    public Device device;

    @Comment("设备ID")
    @Column("device_id")
    public String deviceId;

    @Column("message")
    @Comment("显示备注信息")
    @ColDefine(type = ColType.TEXT)
    public String message;

    @Many(field = "squareId",key = "id")
    public List<Port> ports;

    public Map<Point,Port> getBackMap(){
        Map map = Maps.newTreeMap();
        if(Lang.isEmpty(ports)){
            return Maps.newHashMap();
        }
        for (Port port: ports) {
            Point point = new Point(port.row,port.col);
            map.put(point,port);

        }
        return map;

    }


}
