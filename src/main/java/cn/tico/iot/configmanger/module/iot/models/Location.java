package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import cn.tico.iot.configmanger.module.iot.bean.FatherModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 区域表 sys_area
 *
 * @author haiming
 * @date 2019-04-11
 */
@Data
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
    @Column("context")
    @Comment("会议室信息 ")
    @ColDefine(type = ColType.TEXT)
    private String context;


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
    private String parentName;


}
