package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.Driver;
import cn.tico.iot.configmanger.module.iot.models.Normal;
import cn.tico.iot.configmanger.module.iot.services.DriverService;
import cn.tico.iot.configmanger.module.iot.services.NormalService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 业务 信息操作处理
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean
@At("/iot/driver")
public class DriverController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private DriverService driverService;
	@Inject
	private NormalService normalService;
	@Inject
	private UserService userService;

	@RequiresPermissions("iot:driver:view")
	@At("")
	@Ok("th:/iot/driver/list.html")
	public void index(HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		Dept dept = new Dept();
		dept.setId(user.getDeptId());

		req.setAttribute("dept",dept);
	}

	/**
	 * 查询业务列表
	 */
	@At
	@Ok("json")
	public Object list(@Param("pageNum")int pageNum,
					   @Param("pageSize")int pageSize,
					   @Param("name") String name,
					   @Param("orderByColumn") String orderByColumn,
					   @Param("isAsc") String isAsc,
					   HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)){
			SqlExpressionGroup group = Cnd.exps("cn_name", "like", "%" + name + "%").or("sno", "like", "%" + name + "%");
			cnd.and(group);
		}

		if(!isAdmin()){
			return null;
		}
		return driverService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}


	/**
	 * 用户权限
	 * @return
	 */
	private boolean isAdmin() {

		User user = ShiroUtils.getSysUser();

		Set roles = userService.getRoleCodeList(user);

		return roles.contains(ROLE_ADMIN);
	}



	/**
	 * 新增保存业务
	 */
	@At
	@POST
	@Ok("json")
	@Slog(tag="业务", after="新增保存业务id=${args[0].id}")
	public Object addDo(@Param("..") Driver driver,HttpServletRequest req) {
		try {
			driverService.insertDriver(driver);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 新增保存业务
	 */
	@At
	@POST
	@Ok("json")
	@Slog(tag="业务", after="新增保存业务id=${args[0].id}")
	public Object addNormals(List<Normal> normals, HttpServletRequest req) {
		try {
			normalService.insertAllNormal(normals);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}





	/**
	 * 修改保存字典
	 */
	@At
	@POST
	@Ok("json")
	@Slog(tag="业务类型", after="修改保存业务类型")
	public Object editDo(@Param("..") Driver driver, HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(driver)){

				driverService.updateDriver(driver);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除业务
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("iot:driver:remove")
	@Slog(tag ="业务", after= "删除业务:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			driverService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
