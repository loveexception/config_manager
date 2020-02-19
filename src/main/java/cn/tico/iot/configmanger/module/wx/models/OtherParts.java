package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import lombok.Data;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 运维表 other_parts
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@Table("other_parts")
@Data
@Comment("备品备件")
public class OtherParts extends I18NModel {

	@Column("dept_id")
	@Comment("组织")
	@ColDefine(type = ColType.VARCHAR, width = 32)
	public String deptId;
	@One(field = "deptId", key = "id")
	private Dept dept;

	@Column("adress")
	@Comment("地址")
	private String adress;
	@Column("kind_id")
	@Comment("类型")
	@ColDefine(type = ColType.VARCHAR, width = 32)
	private String kindId;
	@One(field = "kindId", key = "id")
	private Kind kind;
	private Map<String, Kind> kindParents;
	private List<Kind> kindChildren;

	@Column("location_id")
	@Comment("地址")
	@ColDefine(type = ColType.VARCHAR, width = 32)
	private String locationId;
	@One(field = "locationId", key = "id")
	private Location location;
	private Map<String, Location> locationParents;
	private List<Location> locationChildren;

	@Column("order_time")
	@Comment("购买时间")
	private Date orderTime;

	@Column("ip")
	@Comment("IP地址")
	@ColDefine(type = ColType.VARCHAR, width = 255)
	private String ip;

	@Column("sno")
	@Comment("设备编号")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	private String sno;

	@Column("total")
	@Comment("总数量")
	@ColDefine(type = ColType.INT, width = 32)
	private int total;

	@Column("version")
	@Comment("版本")
	@ColDefine(type = ColType.VARCHAR, width = 255)
	private String version;

}
