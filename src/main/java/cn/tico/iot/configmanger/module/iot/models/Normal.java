package cn.tico.iot.configmanger.module.iot.models;


import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

@Data
@Table("t_iot_normals")
public class Normal extends I18NModel {


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
    private List<Grade> grades;




    @Many(field = "normalid")
    private List<Personal> personals;











}
