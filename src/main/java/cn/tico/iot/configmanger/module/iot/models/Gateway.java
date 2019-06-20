package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.module.iot.bean.DeviceEnvModel;
import lombok.Data;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_iot_gateways")
public class Gateway extends DeviceEnvModel {

}
