package cn.tico.iot.configmanger.module.iot.models;


import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.Device;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.*;
import org.nutz.dao.entity.annotation.*;
import  org.nutz.dao.pager.Pager;

import java.io.Serializable;
import java.util.List;




@Data
@Table("t_iot_drives")
public class Driver extends I18NModel implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 组织ID
     */
    @Column("dept_id")
    @Comment("组织")
    private String deptid;
    /**
     * 组织
     */
    @One(field = "deptid",key = "id")
    private Dept dept;

}
