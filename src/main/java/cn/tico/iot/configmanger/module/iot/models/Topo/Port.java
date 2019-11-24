package cn.tico.iot.configmanger.module.iot.models.Topo;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;


@Table("t_topo_ports")
@Comment("端口连接到哪里")
@Data
public class Port extends I18NModel{

    @Column("square_id")
    @Comment("设备ID从哪个设施上开始")
    public String squareId;

    @One(field = "squareId",key = "id")
    public Square square;

    @Column("type")
    @Comment("接口类型应与wire类型一致")
    public String type;

    @Column("row")
    @Comment("背版的所处位置行")
    public int row;

    @Column("col")
    @Comment("背版的所处位置列")
    public int col;


    @Column("wire_id")
    @Comment("连接线ID")
    public String wireId;


    @One(field = "wireId",key = "id")
    public Wire wire;


}
