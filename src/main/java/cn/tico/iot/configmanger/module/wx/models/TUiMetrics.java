package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import cn.tico.iot.configmanger.module.iot.bean.I18NModel;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import com.sun.imageio.plugins.common.I18N;
import lombok.Data;
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
@Data
public class TUiMetrics extends I18NModel implements Serializable {
	private static final long serialVersionUID = 1L;


			/** 指标所属设备类型id */
	@Column("kind_type_id")
	@Comment("指标所属设备类型id")
	private String kindTypeId;

	@One(field ="kindTypeId",key = "id")
	private Kind kind;

			/** 指标所属设备类型 */
	@Column("kind_type")
	@Comment("指标名")
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
	@Comment("正常显示")
	private String enumTrue;

			/** 展示数据 */
	@Column("enum_false")
	@Comment("异常显示")
	private String enumFalse;

			/** 指标最大值 */
	@Column("max_value")
	@Comment("最大值")
	private String maxValue;

			/** 指标最小值 */
	@Column("min_value")
	@Comment("最小值")
	private String minValue;

			/**  */
	@Column("view_metrics")
	@Comment("一键寻检")
	private String viewMetrics;

			/**  */
	@Column("view_table")
	@Comment("开关指标")
	private String viewTable;

			/**  */
	@Column("view_graph")
	@Comment("图表")
	private String viewGraph;




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
            .append("delflag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
