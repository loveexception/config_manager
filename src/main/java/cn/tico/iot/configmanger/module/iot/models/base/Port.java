package cn.tico.iot.configmanger.module.iot.models.base;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

@Data
@Table("t_iot_port")
public class Port extends I18NModel implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 组织
     */
    @Column("dept_id")
    @Comment("组织")
    private String deptid;

    @One(field = "deptid",key = "id")
    private Dept dept;


    @Column("device_id")
    @Comment("驱动")
    private String deviceid;

    @One(field = "deviceid",key = "id")
    private Device device;

    @Column("card")
    @Comment("卡名")
    private String card;

    @Column("port")
    @Comment("口名")
    private String port;



}
