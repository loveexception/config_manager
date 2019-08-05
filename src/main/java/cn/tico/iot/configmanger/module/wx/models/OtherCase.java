package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 运维表 other_case
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@Table("other_case")
public class OtherCase extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 设备序号 */
	@Column("sno")
	@Comment("设备序号")
	private String sno;

			/** 类型：  */
	@Column("type")
	@Comment("类型： ")
	private String type;

			/** 备注信息  */
	@Column("remarks")
	@Comment("备注信息 ")
	private String remarks;

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

	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}

	public void setSno(String sno) 
	{
		this.sno = sno;
	}

	public String getSno() 
	{
		return sno;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}

	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setDelflag(String delflag) 
	{
		this.delflag = delflag;
	}

	public String getDelflag() 
	{
		return delflag;
	}

	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}

	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}

	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sno", getSno())
            .append("type", getType())
            .append("remarks", getRemarks())
            .append("status", getStatus())
            .append("delflag", getDelflag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
