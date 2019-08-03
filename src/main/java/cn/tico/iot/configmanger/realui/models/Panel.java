package cn.tico.iot.configmanger.realui.models;


import cn.tico.iot.configmanger.iot.bean.I18NModel;
import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.base.Tag;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_ui_panels")
public class Panel extends I18NModel {


    @Column("left_y")
    @GraphQLQuery(name="lefty",description = "Y轴显示名称")
    public String lefty;


    @Many(field = "panelid",key = "id")
    @GraphQLQuery(name = "metrics" ,description = "指标项")
    public List<PanelLink> metrics;

    @Column
    @GraphQLQuery(name = "type",description = "表格类型")
    public String type;

    @Column("order_num")
    @GraphQLQuery(name = "order_num",description = "排序")
    public long orderNum;


    @ManyMany(relation = "t_ui_dash_panel",from = "panel_id",to = "dash_id",key = "id")
    @GraphQLQuery(name = "dashBoards",description = "页面组")
    public List<DashBoard> dashBoards;


}
