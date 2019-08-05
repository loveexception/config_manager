package cn.tico.iot.configmanger.realui.models;


import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

@Table("t_ui_metrics")
@Data
public class Metric extends I18NModel {

    @Column
    @Comment("指标所属设备类型id")
    public String kind_type_id;

    @Column
    @Comment("指标所属设备类型")
    public String kind_type;



    @Column
    @Comment("指标展示顺序")
    @GraphQLQuery(name = "order_num",description = "指标展示顺序")
    public long order_num;

    @Column
    @Comment("指标单位")
    @GraphQLQuery(name = "unit",description = "指标单位")
    public long unit;

    //  `enum_true` varchar(32) DEFAULT NULL COMMENT '展示数据',
    //  `enum_false` varchar(32) DEFAULT NULL COMMENT '展示数据',
    //  `max_value` varchar(32) DEFAULT NULL COMMENT '指标最大值',
    //  `min_value` varchar(32) DEFAULT NULL COMMENT '指标最小值',

    @Column
    @Comment("展示数据")
    @GraphQLQuery(name = "enum_true",description = "展示数据")
    public String enum_true;

    @Column
    @Comment("展示数据")
    @GraphQLQuery(name = "enum_false",description = "展示数据")
    public String enum_false;

    @Column
    @Comment("指标最大值")
    @GraphQLQuery(name = "max_value",description = "指标最大值")
    public String max_value;

    @Column
    @Comment("指标最小值")
    @GraphQLQuery(name = "min_value",description = "指标最小值")
    public String min_value;

    //  `view_metrics` varchar(32) DEFAULT NULL COMMENT '指标示意图是否展示',
    //  `view_table` varchar(32) DEFAULT NULL COMMENT '状态数据是否展示',
    //  `view_graph` varchar(32) DEFAULT NULL COMMENT '趋势数据是否展示',

    @Column
    public String view_metrics;
    @Column
    public String view_table;
    @Column
    public String view_graph;



}
