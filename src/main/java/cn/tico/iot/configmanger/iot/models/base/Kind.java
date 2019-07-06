package cn.tico.iot.configmanger.iot.models.base;

import cn.tico.iot.configmanger.iot.bean.FatherModel;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@Data
@Table("t_iot_kinds")
public class Kind extends FatherModel implements Serializable {
    private static final long serialVersionUID = 1L;



}
