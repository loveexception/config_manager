package cn.tico.iot.configmanger.module.other.models;

import cn.tico.iot.configmanger.iot.bean.CommonModel;
import cn.tico.iot.configmanger.iot.bean.I18NModel;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

/**
 * 运维表 sys_dict
 *
 * @author haiming
 * @date 2019-04-16
 */
@Table("other_case")
@Comment("运维表")
public class Case extends CommonModel implements Serializable {
    private static final long serialVersionUID = 1L;



    /**
     * 标签名
     */
    @Column("sno")
    @Comment("设备序号")
    private String no;


    /**
     * 类型
     */
    @Column("type")
    @Comment("类型： ")
    private String type;




    /**
     * 备注信息
     */
    @Column("remarks")
    @Comment("备注信息 ")
    private String remarks;


}
