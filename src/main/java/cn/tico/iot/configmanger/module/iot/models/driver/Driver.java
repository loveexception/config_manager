package cn.tico.iot.configmanger.module.iot.models.driver;


import cn.tico.iot.configmanger.module.iot.models.DriverModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;


@Data
@Table("t_iot_drives")
public class Driver extends DriverModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 存储位置
     */
    @Column("path")
    @Comment("存储位置")

    @ColDefine(type = ColType.VARCHAR, width = 255)
    @GraphQLQuery(name = "path", description = "驱动模版")

    private String  path ;


    @Many(field = "driverid" ,key = "id")
    @GraphQLQuery(name = "normals", description = "项目")
    private List<Normal> normals;


}
