package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 运维表 other_workflow
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@Table("other_workflow")
@Data
@Comment("运维班组")
public class OtherWorkflow extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;


	/** 班组名子 */
	@Column("cn_name")
	@Comment("名子")
	private String cnName;

	/** 班组编号 */
	@Column("en_name")
	@Comment("编号")
	private String enName;

	@ManyMany(relation ="other_work_emp",from = "work_id",to="emp_id")
	private List<OtherEmp> emps;

	private String empIds;




			/** 班组开始时间 */
	@Column("start_time")
	@Comment("班组计划开始时间")
	private Date startTime;

	/** 结束时间 */
	@Column("end_time")
	@Comment("班组计划结束时间")
	private Date endTime;


	/** 结束时间 */
	@Column("cycle")
	@Comment("循环周期第几周生效 值为 1-4 四周一轮换，本条目第一周生效 ，0 为不考虑周期")
	private Date cycle;



	/** 周一 */
	@Column("on_mon")
	@Comment("周一上班时间")
	private String onMon;
	/** 周一 */
	@Column("off_mon")
	@Comment("周一下班时间")
	private String offMon;

	/** 周二 */
	@Column("on_tue")
	@Comment("周二上班时间")
	private String onTue;
	/** 周二 */
	@Column("off_tue")
	@Comment("周二下班时间")
	private String offTue;

	/** 周三 */
	@Column("on_wed")
	@Comment("周三上班时间")
	private String onWed;
	/** 周三 */
	@Column("off_wed")
	@Comment("周三下班时间")
	private String offWed;

	/** 周四 */
	@Column("on_thu")
	@Comment("周四上班时间")
	private String onThu;
	/** 周四 */
	@Column("off_thu")
	@Comment("周四下班时间")
	private String offThu;

	/** 周五 */
	@Column("on_fri")
	@Comment("周五上班时间")
	private String onFri;
	/** 周五 */
	@Column("off_fri")
	@Comment("周五下班时间")
	private String offFri;

	/** 周六 */
	@Column("on_sat")
	@Comment("周六上班时间")
	private String onSat;
	/** 周六 */
	@Column("off_sat")
	@Comment("周六下班时间")
	private String offSat;


	/** 周日 */
	@Column("on_sun")
	@Comment("周日上班时间")
	private String onSun;
	/** 周日 */
	@Column("off_sun")
	@Comment("周日下班时间")
	private String offSun;




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



	

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("cycle", getCycle())
            .append("cnName", getCnName())
            .append("enName", getEnName())

            .append("status", getStatus())
            .append("delflag", getDelflag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
