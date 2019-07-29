package cn.tico.iot.configmanger.iot.models.driver;

import cn.tico.iot.configmanger.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_iot_grades")
public class Grade extends I18NModel implements Comparable<Grade> {



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
    @Comment("级别;")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "grade", description = "级别")
    private String  grade ;


    @Many(field = "gradeid")
    @GraphQLQuery(name = "rulers", description = "规则")
    private List<Ruler> rulers;

    /**
     * 排序
     */
    @Column("logic")
    @Comment("逻辑")
    @ColDefine(type = ColType.VARCHAR)
    @GraphQLQuery(name = "logic", description = "逻辑")
    private String  logic ;


    /**
     * 排序
     */
    @Column("order_num")
    @Comment("排序")
    @ColDefine(type = ColType.INT)
    @GraphQLQuery(name = "order_num", description = "排序")
    private long  orderNum ;


    @Override
    public int compareTo(Grade o) {
        return this.orderNum>o.getOrderNum()?1:-1;
    }
}
