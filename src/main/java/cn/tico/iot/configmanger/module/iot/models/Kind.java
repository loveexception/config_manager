package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.module.iot.bean.FatherModel;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@Data
@Table("t_iot_kinds")
public class Kind extends FatherModel implements Serializable {
    private static final long serialVersionUID = 1L;



}
