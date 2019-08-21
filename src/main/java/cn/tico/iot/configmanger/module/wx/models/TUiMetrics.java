package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 一键寻检数据表 t_ui_metrics
 * 
 * @author maodajun
 * @date 2019-08-21
 */
@Table("t_ui_metrics")
public class TUiMetrics extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 指标所属设备类型id */
	@Column("kind_type_id")
	@Comment("指标所属设备类型id")
	private String kindTypeId;

			/** 指标所属设备类型 */
	@Column("kind_type")
	@Comment("指标所属设备类型")
	private String kindType;

			/** 指标展示顺序 */
	@Column("order_num")
	@Comment("指标展示顺序")
	private Long orderNum;

			/** 指标单位 */
	@Column("unit")
	@Comment("指标单位")
	private Long unit;

			/** 展示数据 */
	@Column("enum_true")
	@Comment("展示数据")
	private String enumTrue;

			/** 展示数据 */
	@Column("enum_false")
	@Comment("展示数据")
	private String enumFalse;

			/** 指标最大值 */
	@Column("max_value")
	@Comment("指标最大值")
	private String maxValue;

			/** 指标最小值 */
	@Column("min_value")
	@Comment("指标最小值")
	private String minValue;

			/**  */
	@Column("view_metrics")
	@Comment("")
	private String viewMetrics;

			/**  */
	@Column("view_table")
	@Comment("")
	private String viewTable;

			/**  */
	@Column("view_graph")
	@Comment("")
	private String viewGraph;

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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getKindTypeId()
	{
		return kindTypeId;
	}

	public void setKindTypeId(String kindTypeId)
	{
		this.kindTypeId = kindTypeId;
	}

	public String getKindType()
	{
		return kindType;
	}

	public void setKindType(String kindType)
	{
		this.kindType = kindType;
	}

	public Long getOrderNum()
	{
		return orderNum;
	}

	public void setOrderNum(Long orderNum)
	{
		this.orderNum = orderNum;
	}

	public Long getUnit()
	{
		return unit;
	}

	public void setUnit(Long unit)
	{
		this.unit = unit;
	}

	public String getEnumTrue()
	{
		return enumTrue;
	}

	public void setEnumTrue(String enumTrue)
	{
		this.enumTrue = enumTrue;
	}

	public String getEnumFalse()
	{
		return enumFalse;
	}

	public void setEnumFalse(String enumFalse)
	{
		this.enumFalse = enumFalse;
	}

	public String getMaxValue()
	{
		return maxValue;
	}

	public void setMaxValue(String maxValue)
	{
		this.maxValue = maxValue;
	}

	public String getMinValue()
	{
		return minValue;
	}

	public void setMinValue(String minValue)
	{
		this.minValue = minValue;
	}

	public String getViewMetrics()
	{
		return viewMetrics;
	}

	public void setViewMetrics(String viewMetrics)
	{
		this.viewMetrics = viewMetrics;
	}

	public String getViewTable()
	{
		return viewTable;
	}

	public void setViewTable(String viewTable)
	{
		this.viewTable = viewTable;
	}

	public String getViewGraph()
	{
		return viewGraph;
	}

	public void setViewGraph(String viewGraph)
	{
		this.viewGraph = viewGraph;
	}

	public String getCnName()
	{
		return cnName;
	}

	public void setCnName(String cnName)
	{
		this.cnName = cnName;
	}

	public String getEnName()
	{
		return enName;
	}

	public void setEnName(String enName)
	{
		this.enName = enName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDelflag()
	{
		return delflag;
	}

	public void setDelflag(String delflag)
	{
		this.delflag = delflag;
	}

	public String getCreateBy()
	{
		return createBy;
	}

	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getUpdateBy()
	{
		return updateBy;
	}

	public void setUpdateBy(String updateBy)
	{
		this.updateBy = updateBy;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("kindTypeId", getKindTypeId())
            .append("kindType", getKindType())
            .append("orderNum", getOrderNum())
            .append("unit", getUnit())
            .append("enumTrue", getEnumTrue())
            .append("enumFalse", getEnumFalse())
            .append("maxValue", getMaxValue())
            .append("minValue", getMinValue())
            .append("viewMetrics", getViewMetrics())
            .append("viewTable", getViewTable())
            .append("viewGraph", getViewGraph())
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
