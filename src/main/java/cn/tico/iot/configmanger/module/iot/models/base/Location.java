package cn.tico.iot.configmanger.module.iot.models.base;

import cn.tico.iot.configmanger.module.iot.bean.FatherModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import groovy.transform.Sortable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 区域表 sys_area
 *
 * @author haiming
 * @date 2019-04-11
 */
@Data
@EqualsAndHashCode
@ToString(includeFieldNames = true,callSuper = true,doNotUseGetters = false,exclude = "id")
@Sortable(includes = "level")
@Table("t_iot_locations")
public class Location extends FatherModel implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 编码
     */
    @Column("dept_id")
    @Comment("组织")
    private String deptid;

    @One (field = "deptid",key = "id")
    private Dept dept;

    /**
     * 详细信息
     */
    @Column("desription")
    @Comment("会议室信息 ")
    @ColDefine(type = ColType.TEXT)
    private String desription;


    /**
     * 经度
     */
    @Column("lng")
    @Comment("经度 ")
    private double lng;

    /**
     * 纬度
     */
    @Column("lat")
    @Comment("纬度 ")
    private double lat;


    private String deptName;


}
