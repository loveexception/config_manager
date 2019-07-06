package cn.tico.iot.configmanger.iot.models.device;


import cn.tico.iot.configmanger.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

@Data
@Table("t_iot_person_rulers")
public class PersonRuler extends I18NModel {
    /**
     * sno
     */
    @Column("person_id")
    @Comment("设备个性化")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "person_id", description = "设备个性化")
    private String  personid ;

    @One(field = "personid",key="id")
    private Personal personal;


    /**
     * 操作码
     */
    @Column("operate_key")
    @Comment("操作码")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "operate_key", description = "驱动")
    private String  operateKey ;


    /**
     * 关系
     */
    @Column("logic")
    @Comment("关系")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "logic", description = "关系 and ,or ")
    private String  logic ;



    /**
     * 符号
     */
    @Column("symble")
    @Comment("符号")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "symble", description = "符号 > ,= ,< , !=")
    private String  symble ;


    /**
     * 值
     */
    @Column("val")
    @Comment("值")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "val", description = "值")
    private String  val ;



    /**
     * 排序
     */
    @Column("order_num")
    @Comment("排序")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "order_num", description = "排序")

    private String  orderNum ;


}
