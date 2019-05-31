/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tico.iot.configmanger.common.utils.excel.fieldtype;

import cn.tico.iot.configmanger.common.utils.StringUtils;
import cn.tico.iot.configmanger.module.sys.models.Area;
import cn.tico.iot.configmanger.module.sys.services.AreaService;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

/**
 * 区域查询
 */
public class AreaType {
	public static Ioc ioc;

	private static AreaService areaService;

	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*anoo"));
			areaService = ioc.get(AreaService.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Area e : areaService.query()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}
