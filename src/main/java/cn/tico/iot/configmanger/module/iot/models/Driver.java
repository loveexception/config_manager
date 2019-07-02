package cn.tico.iot.configmanger.module.iot.models;


import cn.tico.iot.configmanger.module.iot.bean.DeviceEnvModel;
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
public class Driver extends DeviceEnvModel implements Serializable {

    private static final long serialVersionUID = 1L;


}
