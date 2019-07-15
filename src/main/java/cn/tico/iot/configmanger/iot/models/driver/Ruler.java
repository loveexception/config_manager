package cn.tico.iot.configmanger.iot.models.driver;

import cn.tico.iot.configmanger.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

@Data
@Table("t_iot_rulers")
public class Ruler extends I18NModel {

    /**
     * 操作码
     */
    @Column("grade_id")
    @Comment("级别")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "grade_id", description = "级别")
    private String  gradeid ;

    @One(field = "gradeid",key="id")
    private Grade grade;

    /**
     * 操作码
     */
    @Column("normal_id")
    @Comment("列名")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @GraphQLQuery(name = "normal_id", description = "列名")
    private String  normalid ;

    @One(field = "normalid",key="id")
    private Normal normal;


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
    @ColDefine(type = ColType.INT)
    @GraphQLQuery(name = "order_num", description = "排序")
    private long  orderNum ;




}
