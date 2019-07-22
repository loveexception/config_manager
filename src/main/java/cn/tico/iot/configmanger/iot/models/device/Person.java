package cn.tico.iot.configmanger.iot.models.device;

import cn.tico.iot.configmanger.iot.bean.I18NModel;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_iot_persons")
@Comment("设备个性化")
public class Person extends I18NModel {

    /**
     * 属性
     */
    @Column("normal_id")
    @Comment("属性")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "normal_id", description = "属性")
    private String  normalid ;

    @One(field = "normalid",key = "id")
    @GraphQLQuery(name = "normal", description = "属性")
    private Normal normal;



    /**
     * sno
     */
    @Column("sno")
    @Comment("sno")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "sno", description = "sno")
    private String  sno ;


    /**
     * 驱动
     */
    @Column("device_id")
    @Comment("驱动")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "device_id", description = "驱动")
    private String  deviceid ;


    @One(field = "deviceid",key = "id")
    @GraphQLQuery(name = "device", description = "设备")
    private Device device;



    @Many(field = "personid",key = "id")
    @GraphQLQuery(name = "personGrades", description = "规则")
    private List<PersonGrade> personGrades;




}
