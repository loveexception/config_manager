package cn.tico.iot.configmanger.module.mao.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 备品备件表 other_parts
 *
 * @author maodajun
 * @date 2019-12-14
 */
@Data
@Table("sys_pushs")
@Comment("告警升级")
public class Pushs extends I18NModel implements Serializable {

    @Column("dept_id")
    @Comment("公司")
    private String deptId;


    @Column("type")
    @Comment("通知方式")
    private String type;


    @Column("level")
    @Comment("指标级别")
    private String level;

    @One(field = "deptId", key = "id")
    private Dept dept;

}
