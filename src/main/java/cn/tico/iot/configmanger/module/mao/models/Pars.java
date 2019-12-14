package cn.tico.iot.configmanger.module.mao.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 备品备件表 other_parts
 * 
 * @author maodajun
 * @date 2019-12-14
 */
@Table("other_parts")
public class Pars extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 类型 */
	@Column("kind_id")
	@Comment("类型")
	private String kindId;

			/** 组织 */
	@Column("dept_id")
	@Comment("组织")
	private String deptId;

			/** 地址 */
	@Column("location_id")
	@Comment("地址")
	private String locationId;

			/** 购买时间 */
	@Column("order_time")
	@Comment("购买时间")
	private String orderTime;

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

	public void setKindId(String kindId) 
	{
		this.kindId = kindId;
	}

	public String getKindId() 
	{
		return kindId;
	}

	public void setDeptId(String deptId) 
	{
		this.deptId = deptId;
	}

	public String getDeptId() 
	{
		return deptId;
	}

	public void setLocationId(String locationId) 
	{
		this.locationId = locationId;
	}

	public String getLocationId() 
	{
		return locationId;
	}

	public void setOrderTime(String orderTime) 
	{
		this.orderTime = orderTime;
	}

	public String getOrderTime() 
	{
		return orderTime;
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
            .append("kindId", getKindId())
            .append("deptId", getDeptId())
            .append("locationId", getLocationId())
            .append("orderTime", getOrderTime())
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
