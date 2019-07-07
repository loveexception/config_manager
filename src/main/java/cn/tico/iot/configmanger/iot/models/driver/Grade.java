package cn.tico.iot.configmanger.iot.models.driver;

import cn.tico.iot.configmanger.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_iot_grades")
public class Grade extends I18NModel {



    /**
     * 属性
     */
    @Column("normal_id")
    @Comment("属性")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "normal_id", description = "属性")
    private String  normalid ;

    @One(field = "normalid",key = "id")
    private Normal normal;

    /**
     * 级别
     */
    @Column("grade")
    @Comment("级别")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "grade", description = "级别")
    private String  grade ;


    @Many(field = "gradeid")
    private List<Ruler> rulers;





}
