package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 故障原因表 t_iot_devops_reason
 * 
 * @author maodajun
 * @date 2019-10-11
 */
@Table("t_iot_devops_reason")
public class TIotDevopsReason  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Comment("Id")
	@ColDefine(type = ColType.INT)
	private Integer id;

			/** 故障原因 */
	@Column("fault_reason")
	@Comment("故障原因")
	private String faultReason;

			/** 类型 */
	@Column("fault_type")
	@Comment("类型")
	private String faultType;

			/** 类型英文 */
	@Column("fault_type_en")
	@Comment("类型英文")
	private String faultTypeEn;

	
	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}

	public void setFaultReason(String faultReason) 
	{
		this.faultReason = faultReason;
	}

	public String getFaultReason() 
	{
		return faultReason;
	}

	public void setFaultType(String faultType) 
	{
		this.faultType = faultType;
	}

	public String getFaultType() 
	{
		return faultType;
	}

	public void setFaultTypeEn(String faultTypeEn) 
	{
		this.faultTypeEn = faultTypeEn;
	}

	public String getFaultTypeEn() 
	{
		return faultTypeEn;
	}


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("faultReason", getFaultReason())
            .append("faultType", getFaultType())
            .append("faultTypeEn", getFaultTypeEn())
            .toString();
    }
}
