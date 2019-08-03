package cn.tico.iot.configmanger.realui.models;


import cn.tico.iot.configmanger.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

import java.util.List;

@Data
@Table("t_ui_dash_boards")
public class DashBoard extends I18NModel {

    @Column("kind_id")
    @Comment("类型")
    public String kindid;

    @Column("driver_id")
    @Comment ("驱动ID")
    public String driverid;

    @Column("location_id")
    @Comment("地理ID")
    public String locationid;

    @Column("error_code")
    @Comment("错误类型")
    public String errorCode;

    @ManyMany(relation = "t_ui_dash_panel",from = "dash_id",to="panel_id",key = "id")
    @GraphQLQuery(name = "panels",description = "面板个数")
    List<Panel> panels;

}
