package cn.tico.iot.configmanger.module.iot.models.Topo;


import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.graphql.Block;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_topo_graphs")
@Comment("拓扑图存储")
public class Topo extends I18NModel{

    @Column("tag_id")
    @Comment("业务标签")
    public String tagId;

    @One(field = "tagId",key = "id")
    public Tag tag;

    @Column("graph")
    @Comment("对外显示拓扑图内容")
    @ColDefine(type = ColType.TEXT)
    public String graph;


    @Column("hide_map")
    @Comment("不显示的拓扑图")
    @ColDefine(type = ColType.TEXT)
    public String hideMap;

    @Column("base_id")
    @Comment("使用的什么母版")
    public String baseId;

    @One(field = "baseId",key = "id")
    public Base base;


    @Many(field = "topoId",key = "id")
    public List<Square> squares ;

    @Many(field = "topoId",key = "id")
    public List<Wire>  wires;


    @Column("is_check")
    @Comment("是否侦听系统信息")
    public String isCheck;

    /**
     * 排序
     */
    @Column("order_num")
    @Comment("排序")
    @ColDefine(type = ColType.INT ,width = 50 )
   // @GraphQLQuery(name = "order_num", description = "排序")
    private long  orderNum ;

}
