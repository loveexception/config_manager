package cn.tico.iot.configmanger.iot.models.device;

import cn.tico.iot.configmanger.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

@Data
@Table("t_iot_assets")
public class Assets extends I18NModel implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 组织
     */
    @Column("device_id")
    @Comment("组织")
    private String deviceid;

    /**
     * 组织
      */
    @One(field = "deviceid",key = "id")
    private Device device;







}
