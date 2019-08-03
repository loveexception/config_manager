package cn.tico.iot.configmanger.realui.models;


import cn.tico.iot.configmanger.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("t_ui_panel_links")
@Data
public class PanelLink extends I18NModel {


    @Column("panel_id")
    @Comment("面板ID")
    @GraphQLQuery(name = "panelid", description = "面板ID")
    public String panelid;

    @GraphQLQuery(name = "panel",description = "面板")
    @One(field = "panelid",key = "id")
    public Panel panel;

    @Column("metric_id")
    @Comment("属性ID")
    @GraphQLQuery(name = "metricid",description = "属性ID")
    public String metricid;

    @Column("order_num")
    @Comment("排序")
    @GraphQLQuery(name = "order_num",description = "排序")
    public long orderNum;


    @One(field = "metricid",key = "id")
    @GraphQLQuery(name = "metric",description = "属性表")
    public Metric metric;

}
