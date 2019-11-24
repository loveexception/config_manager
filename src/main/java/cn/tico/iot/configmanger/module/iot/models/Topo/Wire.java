package cn.tico.iot.configmanger.module.iot.models.Topo;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_topo_wires")
@Comment("拓扑图中的线")
public class Wire extends I18NModel{

    @Column("topo_id")
    @Comment("拓扑图")
    public String topoId;

    @One(field = "topoId",key = "id")
    public Topo topo;


    @One(field = "leftId",key = "id")
    public Square left;
    @One(field = "rightId",key = "id")
    public Square right;

    @Column("left_id")
    @Comment("来源设备方块连接")
    public String leftId;


    @Column("right_id")
    @Comment("到达设备方块连接")
    public String rightId;

    @Column("type")
    @Comment("连接线的类型")
    public String type ;

    @Column("Info")
    @Comment("线号，包括左线签")
    @ColDefine(type = ColType.TEXT)
    public String info;


    //左端口， 右端口 列表
    @Many(field = "wireId",key = "id")
    public List<Port> ports;




}
