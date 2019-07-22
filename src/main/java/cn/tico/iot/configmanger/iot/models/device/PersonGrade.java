package cn.tico.iot.configmanger.iot.models.device;

import cn.tico.iot.configmanger.iot.bean.I18NModel;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;


@Data
@Table("t_iot_person_grade")
@Comment("个性化等级")
public class PersonGrade extends I18NModel {

    /**
     * 属性
     */
    @Column("person_id")
    @Comment("属性")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "person_id", description = "属性")
    private String  personid ;

    @One(field = "personid",key = "id")
    private Person person;

    /**
     * 级别
     */
    @Column("grade")
    @Comment("级别")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "grade", description = "级别")
    private String  grade ;


    @Many(field = "gradeid" ,key = "id")
    private List<PersonRuler> personRulers;

}
