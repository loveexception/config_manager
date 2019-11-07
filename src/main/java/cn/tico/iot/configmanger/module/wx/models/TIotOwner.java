package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 资产管理表 t_iot_owner
 * 
 * @author maodajun
 * @date 2019-11-06
 */
@Table("t_iot_owner")
public class TIotOwner extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 名称 */
	@Column("cn_name")
	@Comment("名称")
	private String cnName;

			/** 编码 */
	@Column("en_name")
	@Comment("编码")
	private String enName;

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

	public void setCnName(String cnName) 
	{
		this.cnName = cnName;
	}

	public String getCnName() 
	{
		return cnName;
	}

	public void setEnName(String enName) 
	{
		this.enName = enName;
	}

	public String getEnName() 
	{
		return enName;
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
