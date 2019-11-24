package cn.tico.iot.configmanger.module.iot.models.base;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.Strings;

import java.io.Serializable;
import java.util.List;

@Data
@Table("t_iot_tags")
public class Tag extends I18NModel implements Comparable<Tag> {




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


    /**
     * 设备列表
     */
    @ManyMany(from = "tag_id",relation = "t_tag_dev", to="dev_id")
    public List<Device> devices;


    @Override
    public int compareTo(Tag o) {
        if(Strings.isEmpty(getId())||Strings.isEmpty(o.getId())){
            return CompareToBuilder.reflectionCompare(getCnName(),o.getCnName());
        }
        return CompareToBuilder.reflectionCompare(getId(),o.getId());
    }
}
