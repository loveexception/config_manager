package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备资本表 t_iot_devices
 * 
 * @author maodajun
 * @date 2019-11-07
 */
@Table("t_iot_devices")
public class TIotDevices extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 机器码 */
	@Column("sno")
	@Comment("机器码")
	private String sno;

			/** 价格 */
	@Column("price")
	@Comment("价格")
	private BigDecimal price;

			/** 购买日期 */
	@Column("order_time")
	@Comment("购买日期")
	private String orderTime;

			/** 使用年限 */
	@Column("quality")
	@Comment("使用年限")
	private Long quality;

			/** 报废时间 */
	@Column("discard_time")
	@Comment("报废时间")
	private String discardTime;

			/** 网关 */
	@Column("gateway_id")
	@Comment("网关")
	private String gatewayId;

			/** 冗余的SNO */
	@Column("gateway_extsno")
	@Comment("冗余的SNO")
	private String gatewayExtsno;

			/** 驱动 */
	@Column("driver_id")
	@Comment("驱动")
	private String driverId;

			/** 类型 */
	@Column("kind_id")
	@Comment("类型")
	private String kindId;

			/** 类型冗余用于查寻 */
	@Column("kind_map")
	@Comment("类型冗余用于查寻")
	private String kindMap;

			/** ip  */
	@Column("ip")
	@Comment("ip ")
	private String ip;

			/** 采集间隔 */
	@Column("cycle")
	@Comment("采集间隔")
	private Integer cycle;

			/** 单位 */
	@Column("unit")
	@Comment("单位")
	private String unit;

			/** 用户名 */
	@Column("username")
	@Comment("用户名")
	private String username;

			/** 密码 */
	@Column("password")
	@Comment("密码")
	private String password;

			/** 组织 */
	@Column("dept_id")
	@Comment("组织")
	private String deptId;

			/** 地域 */
	@Column("location_id")
	@Comment("地域")
	private String locationId;

			/** 地域国 冗余用于查寻 */
	@Column("location_country")
	@Comment("地域国 冗余用于查寻")
	private String locationCountry;

			/** 地域 省冗余用于查寻 */
	@Column("location_state")
	@Comment("地域 省冗余用于查寻")
	private String locationState;

			/** 地域市余用于查寻 */
	@Column("location_city")
	@Comment("地域市余用于查寻")
	private String locationCity;

			/** 地域公司冗余用于查寻 */
	@Column("location_company")
	@Comment("地域公司冗余用于查寻")
	private String locationCompany;

			/** 地域 会议室 名冗余用于查寻 */
	@Column("location_room")
	@Comment("地域 会议室 名冗余用于查寻")
	private String locationRoom;

			/** 地域ID 用于查寻 */
	@Column("location_map")
	@Comment("地域ID 用于查寻")
	private String locationMap;

			/** 名称 */
	@Column("cn_name")
	@Comment("名称")
	private String cnName;

			/** 编码 */
	@Column("en_name")
	@Comment("编码")
	private String enName;

			/** 告警状态 */
	@Column("alert_status")
	@Comment("告警状态")
	private String alertStatus;

			/** 资产状态 */
	@Column("asset_status")
	@Comment("资产状态")
	private String assetStatus;

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

	public void setPrice(BigDecimal price) 
	{
		this.price = price;
	}

	public BigDecimal getPrice() 
	{
		return price;
	}

	public void setOrderTime(String orderTime) 
	{
		this.orderTime = orderTime;
	}

	public String getOrderTime() 
	{
		return orderTime;
	}

	public void setQuality(Long quality) 
	{
		this.quality = quality;
	}

	public Long getQuality() 
	{
		return quality;
	}

	public void setDiscardTime(String discardTime) 
	{
		this.discardTime = discardTime;
	}

	public String getDiscardTime() 
	{
		return discardTime;
	}

	public void setGatewayId(String gatewayId) 
	{
		this.gatewayId = gatewayId;
	}

	public String getGatewayId() 
	{
		return gatewayId;
	}

	public void setGatewayExtsno(String gatewayExtsno) 
	{
		this.gatewayExtsno = gatewayExtsno;
	}

	public String getGatewayExtsno() 
	{
		return gatewayExtsno;
	}

	public void setDriverId(String driverId) 
	{
		this.driverId = driverId;
	}

	public String getDriverId() 
	{
		return driverId;
	}

	public void setKindId(String kindId) 
	{
		this.kindId = kindId;
	}

	public String getKindId() 
	{
		return kindId;
	}

	public void setKindMap(String kindMap) 
	{
		this.kindMap = kindMap;
	}

	public String getKindMap() 
	{
		return kindMap;
	}

	public void setIp(String ip) 
	{
		this.ip = ip;
	}

	public String getIp() 
	{
		return ip;
	}

	public void setCycle(Integer cycle) 
	{
		this.cycle = cycle;
	}

	public Integer getCycle() 
	{
		return cycle;
	}

	public void setUnit(String unit) 
	{
		this.unit = unit;
	}

	public String getUnit() 
	{
		return unit;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getPassword() 
	{
		return password;
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

	public void setLocationCountry(String locationCountry) 
	{
		this.locationCountry = locationCountry;
	}

	public String getLocationCountry() 
	{
		return locationCountry;
	}

	public void setLocationState(String locationState) 
	{
		this.locationState = locationState;
	}

	public String getLocationState() 
	{
		return locationState;
	}

	public void setLocationCity(String locationCity) 
	{
		this.locationCity = locationCity;
	}

	public String getLocationCity() 
	{
		return locationCity;
	}

	public void setLocationCompany(String locationCompany) 
	{
		this.locationCompany = locationCompany;
	}

	public String getLocationCompany() 
	{
		return locationCompany;
	}

	public void setLocationRoom(String locationRoom) 
	{
		this.locationRoom = locationRoom;
	}

	public String getLocationRoom() 
	{
		return locationRoom;
	}

	public void setLocationMap(String locationMap) 
	{
		this.locationMap = locationMap;
	}

	public String getLocationMap() 
	{
		return locationMap;
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

	public void setAlertStatus(String alertStatus) 
	{
		this.alertStatus = alertStatus;
	}

	public String getAlertStatus() 
	{
		return alertStatus;
	}

	public void setAssetStatus(String assetStatus) 
	{
		this.assetStatus = assetStatus;
	}

	public String getAssetStatus() 
	{
		return assetStatus;
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
            .append("price", getPrice())
            .append("orderTime", getOrderTime())
            .append("quality", getQuality())
            .append("discardTime", getDiscardTime())
            .append("gatewayId", getGatewayId())
            .append("gatewayExtsno", getGatewayExtsno())
            .append("driverId", getDriverId())
            .append("kindId", getKindId())
            .append("kindMap", getKindMap())
            .append("ip", getIp())
            .append("cycle", getCycle())
            .append("unit", getUnit())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("deptId", getDeptId())
            .append("locationId", getLocationId())
            .append("locationCountry", getLocationCountry())
            .append("locationState", getLocationState())
            .append("locationCity", getLocationCity())
            .append("locationCompany", getLocationCompany())
            .append("locationRoom", getLocationRoom())
            .append("locationMap", getLocationMap())
            .append("cnName", getCnName())
            .append("enName", getEnName())
            .append("alertStatus", getAlertStatus())
            .append("assetStatus", getAssetStatus())
            .append("status", getStatus())
            .append("delflag", getDelflag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
