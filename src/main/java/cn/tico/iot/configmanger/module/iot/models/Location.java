package cn.tico.iot.configmanger.module.sys.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
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
@Table("sys_locations")
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
    private String ancestors;
    /**
     * 城市编码
     */
    @Column("citycode")
    @Comment("城市编码 ")
    private String citycode;
    /**
     * 区域编码
     */
    @Column("adcode")
    @Comment("区域编码 ")
    private String adcode;
    /**
     * 行政区名称
     */
    @Column("name")
    @Comment("行政区名称 ")
    private String name;
    /**
     * 行政区划级别
     */
    @Column("level")
    @Comment("行政区划级别 ")
    private String level;

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


    private String parentName;







}
