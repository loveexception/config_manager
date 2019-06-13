package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 区域表 sys_area
 *
 * @author haiming
 * @date 2019-04-11
 */
@Data
@Table("t_iot_locations")
public class Location extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Name
    @Column("id")
    @Comment
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @Prev(els = {@EL("uuid()")})
    private String id;

    /**
     * 父节点
     */
    @Column("parent_id")
    @Comment("父节点 ")
    private String parentId;
    /**
     * 祖节点
     */
    @Column("ancestors")
    @Comment("祖节点 ")
    @ColDefine(type = ColType.TEXT)
    private String ancestors;

    /**
     * 名称
     */
    @Column("cn_name")
    @Comment("名称")
    private String cnName;
    /**
     * 编码
     */
    @Column("en_name")
    @Comment("编码")
    private String enName;

    /**
     * 编码
     */
    @Column("dept_id")
    @Comment("组织")
    private String deptid;

    /**
     * 级别
     */
    @Column("level")
    @Comment("级别 ")
    private String level="0";

    /**
     * 详细信息
     */
    @Column("context")
    @Comment("会议室信息 ")
    @ColDefine(type = ColType.TEXT)
    private String context;


    /**
     * 级别
     */
    @Column("lng")
    @Comment("级别 ")
    private double lng;

    /**
     * 级别
     */
    @Column("lat")
    @Comment("级别 ")
    private double lat;

    /**
     * 创建者
     */
    @Column("create_by")
    @Comment("创建者 ")
    @Prev(els = @EL("$me.uid()"))
    private String createBy;

    /**
     * 创建时间
     */
    @Column("create_time")
    @Comment("创建时间 ")
    @Prev(els = {@EL("$me.now()")})
    private Date createTime;

    /**
     * 更新者
     */
    @Column("update_by")
    @Comment("更新者 ")
    @Prev(els = @EL("$me.uid()"))
    private String updateBy;

    /**
     * 更新时间
     */
    @Column("update_time")
    @Comment("更新时间 ")
    @Prev(els = {@EL("$me.now()")})
    private Date updateTime;

    /**
     * 状态
     */
    @Column("status")
    @Comment("状态，0正常，1异常状态，")
    private  String status="0";

    private String deptName;
    private String parentName;


}
