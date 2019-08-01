package cn.tico.iot.configmanger.realui.models;


import cn.tico.iot.configmanger.iot.bean.I18NModel;
import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.base.Tag;
import com.sun.imageio.plugins.common.I18N;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_ui_panel")
public class Panel extends I18NModel {

    @Column
    @GraphQLQuery(name = "kindid", description = "设备类型")
    public String kindid;

    @One(field = "kind",key = "id")
    @GraphQLQuery(name = "kind", description = "设备类型")
    public Kind kind;

    @Column
    @GraphQLQuery(name="Y",description = "Y轴显示名称")
    public String y;


    @ManyMany(relation ="t_ui_panel_metrics",from = "panel_id",to="metrics")
    @GraphQLQuery(name = "metrics" ,description = "指标项")
    public List<Metric> metrics;





}
