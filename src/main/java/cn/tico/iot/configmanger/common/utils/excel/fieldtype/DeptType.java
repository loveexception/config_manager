/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tico.iot.configmanger.common.utils.excel.fieldtype;


import cn.tico.iot.configmanger.common.utils.StringUtils;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

/**
 * 部门查询名称
 */
public class DeptType {
	public static Ioc ioc;

	private static DeptService deptService;

	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*anoo"));
			deptService = ioc.get(DeptService.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Dept e : deptService.query()){
			if (StringUtils.trimToEmpty(val).equals(e.getDeptName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Dept)val).getDeptName() != null){
			return ((Dept)val).getDeptName();
		}
		return "";
	}
}
