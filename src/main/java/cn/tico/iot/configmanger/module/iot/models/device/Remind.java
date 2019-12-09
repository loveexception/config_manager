package cn.tico.iot.configmanger.module.iot.models.device;

import cn.tico.iot.configmanger.common.utils.excel.annotation.ExcelField;
import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.nutz.dao.entity.annotation.*;
import org.nutz.mvc.annotation.Param;

import java.util.Date;

@Data
@Table("t_iot_owner")
@Comment("资产管理维护")
public class Owner extends I18NModel implements Comparable<Owner> {

	@Column
	@Comment("下次检查时间")
	public String time;

	@Column
	@Comment("下次检查时间间隔（天）")
	public String cycle;

	@Column(value = "device_id")
	@Comment("检修记录")
	public String deviceid;

	@One(field = "deviceid", key = "id")
	public Device device;

	@Override
	public int compareTo(Owner o) {
		return CompareToBuilder.reflectionCompare(o.getTime(), this.getTime());
		// return o.time.compareTo(this.time);
	}

	// @Column("status")
	// @Comment("资产状态")
	// @ColDefine(type = ColType.VARCHAR, width = 32)
	// @GraphQLQuery(name = "status", description = "资产状态")
	// @ExcelField(title="状态",value="status")
	// public String status;
	//
	// @Column("code")
	// @Comment("资产编码")
	// @ColDefine(type = ColType.VARCHAR, width = 256)
	// @GraphQLQuery(name = "code", description = "资产编码")
	// @ExcelField(title="资产编码",value="status")
	// public String code;
	//
	// @Column("type")
	// @Comment("资产分类")
	// @ColDefine(type = ColType.VARCHAR, width = 32)
	// @GraphQLQuery(name = "type", description = "资产分类")
	// @ExcelField(title="资产分类",value="type")
	// public String type;
	//
	//
	// @Column("device_id")
	// @Comment("资产分类")
	//
	// public String deviceid;

}
