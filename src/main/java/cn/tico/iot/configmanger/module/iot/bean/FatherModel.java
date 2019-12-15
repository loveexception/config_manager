package cn.tico.iot.configmanger.module.iot.bean;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import org.nutz.dao.entity.annotation.*;

import java.util.List;
import java.util.Map;

@Data
public  class FatherModel extends I18NModel {

    /**
     * 父节点
     */
    @Column("parent_id")
    @Comment("父节点 ")
    private String parentId;
    /**
     * 祖节点
     */
    @Column("ancestors")
    @Comment("祖节点 ")
    @ColDefine(type = ColType.TEXT)
    private String ancestors;
    /**
     * 级别
     */
    @Column("level")
    @Comment("级别 ")
    @GraphQLQuery(name = "level", description = "层级")
    private String level="0";
    /**
     * 排序
     */
    @Column("order_num")
    @Comment("排序 ")
    @ColDefine(type = ColType.INT)
    @GraphQLQuery(name = "order_num", description = "排序")
    private int orderNum;





    private String parentName;

    private List children ;
    private Map parents;


}
