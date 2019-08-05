package cn.tico.iot.configmanger.module.iot.models.base;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@Data
@Table("t_iot_tags")
public class Tag extends I18NModel implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 组织
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
