package cn.tico.iot.configmanger.module.iot.bean;


import cn.tico.iot.configmanger.module.iot.bean.Device;
import lombok.*;
import org.nutz.dao.entity.annotation.*;
import  org.nutz.dao.pager.Pager;

import java.util.List;




@Data
@RequiredArgsConstructor
@Table("T_Drives")
public class Driver {
    @Id
    long id;

    @Column
    long created_time;

    public List<Device> devices(Pager page){
        return null;
    }

}
