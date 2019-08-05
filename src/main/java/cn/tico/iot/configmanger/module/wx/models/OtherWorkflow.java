package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 运维表 other_workflow
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@Table("other_workflow")
@Data
public class OtherWorkflow extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 日期 */
	@Column("day")
	@Comment("日期")
	private String day;

			/** 开始时间 */
	@Column("start_time")
	@Comment("开始时间")
	private String startTime;

			/** 开始时间 */
	@Column("end_time")
	@Comment("开始时间")
	private String endTime;

			/** 时长 */
	@Column("hours")
	@Comment("时长")
	private String hours;

			/** 名子 */
	@Column("user_name")
	@Comment("名子")
	private String userName;

			/** 部门 */
	@Column("dept_name")
	@Comment("部门")
	private String deptName;

			/** 职位 */
	@Column("title")
	@Comment("职位")
	private String title;

			/** 联系电话 */
	@Column("tel")
	@Comment("联系电话")
	private String tel;

			/** 状态 */
	@Column("status")
	@Comment("状态")
	private String status;

			/** 删除 */
	@Column("delflag")
	@Comment("删除")
	private String delflag;

			/** 创建者 */
	@Column("create_by")
	@Comment("创建者")
	private String createBy;

			/** 建立时间 */
	@Column("create_time")
	@Comment("建立时间")
	private Date createTime;

			/** 更新者 */
	@Column("update_by")
	@Comment("更新者")
	private String updateBy;

			/** 更新时间 */
	@Column("update_time")
	@Comment("更新时间")
	private Date updateTime;

	


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("day", getDay())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("hours", getHours())
            .append("userName", getUserName())
            .append("deptName", getDeptName())
            .append("title", getTitle())
            .append("tel", getTel())
            .append("status", getStatus())
            .append("delflag", getDelflag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
