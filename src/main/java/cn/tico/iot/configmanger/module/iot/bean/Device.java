package cn.tico.iot.configmanger.module.iot.bean;

import lombok.*;
import org.nutz.dao.entity.annotation.*;

import java.util.List;


@Data
@RequiredArgsConstructor
@Table("T_Devices")
public class Device {
    @Id
    long id;

    @Column
    long created_time;

    public List<Tag> getTags(){
        return null;
    }
}
