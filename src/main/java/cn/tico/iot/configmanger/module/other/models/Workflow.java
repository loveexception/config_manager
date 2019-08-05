package cn.tico.iot.configmanger.module.other.models;

import cn.tico.iot.configmanger.module.iot.bean.CommonModel;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 字典表 sys_dict
 *
 * @author haiming
 * @date 2019-04-16
 */
@Table("other_workflow")
@Comment("运维表")
public class Workflow extends CommonModel implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 标签名
     */
    @Column("day")
    @Comment("日期")
    private String day;

    /**
     * 标签名
     */
    @Column("start_time")
    @Comment("开始时间")
    private String startTime;
    /**
     * 标签名
     */
    @Column("end_time")
    @Comment("开始时间")
    private String endTime;
    /**
     * 标签名
     */
    @Column("hours")
    @Comment("时长")
    private String hours;

    /**
     * 标签名
     */
    @Column("user_name")
    @Comment("名子")
    private String userName;

    /**
     * 标签名
     */
    @Column("dept_name")
    @Comment("部门")
    private String deptName;

    /**
     * 标签名
     */
    @Column("title")
    @Comment("职位")
    private String title;


    /**
     * 标签名
     */
    @Column("tel")
    @Comment("联系电话")
    private String tel;




}
