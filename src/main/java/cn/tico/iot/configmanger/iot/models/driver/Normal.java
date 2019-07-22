package cn.tico.iot.configmanger.iot.models.driver;


import cn.tico.iot.configmanger.iot.bean.I18NModel;
import cn.tico.iot.configmanger.iot.models.device.Person;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_iot_normals")
public class Normal extends I18NModel implements  Comparable<Normal>{


    /**
     * 驱动
     */
    @Column("driver_id")
    @Comment("驱动")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "driver_id", description = "驱动")

    private String  driverid ;

    @One(field = "driverid",key="id")
    private Driver driver;




    /**
     * 操作码
     */
    @Column("operate_key")
    @Comment("操作码")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "operate_key", description = "驱动")

    private String  operateKey ;


    /**
     * 操作码
     */
    @Column("unit")
    @Comment("单位")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "unit", description = "单位")
    private String  unit ;


    @Many(field = "normalid")
    @GraphQLQuery(name = "grades", description = "级别")

    private List<Grade> grades;



    @GraphQLQuery(name = "personals", description = "个人设置")
    private Person person;


    @Column("order_num")
    @Comment("顺序")
    @ColDefine(type = ColType.INT)
    @GraphQLQuery(name = "order_num", description = "顺序")
    private long  orderNum ;

    private String key ;


    @Override
    public int     compareTo(Normal o) {
        return this.orderNum > o.orderNum? 1: -1;
    }
}
