package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.GatewayService;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
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
import java.util.Set;

/**
 * 网关 信息操作处理
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean
@At("/iot/gateway")
public class GatewayController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private GatewayService gatewayService;

	@Inject
	private UserService userService;
	@Inject
	private KindService kindService;
	@Inject
	private LocationService locationService;
	@Inject
	private DeptService deptService;

	@RequiresPermissions("iot:gateway:view")
	@At("")
	@Ok("th:/iot/gateway/list.html")
	public void index(HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		Dept dept = new Dept();
		dept.setId(user.getDeptId());

		req.setAttribute("dept",dept);
	}

	/**
	 * 查询网关列表
	 */
	@RequiresPermissions("iot:gateway:list")
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
			//cnd.and("name", "like", "%" + name +"%");
			SqlExpressionGroup group = Cnd.exps("cn_name", "like", "%" + name + "%").or("en_name", "like", "%" + name + "%");
			cnd.and(group);
		}
		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}
		cnd.and("delflag","=","false");
		Object obj =gatewayService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,"^dept|subGateway|kind|location$");

		return obj;
	}
	@At("/gateway_list")
	@Ok("json")
	public Object gatewayList( HttpServletRequest req){
		return Result.success("system.success" ,list(0,1000,null,null,null,req));
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
	 * 新增网关
	 */
	@At("/add")
	@Ok("th:/iot/gateway/add.html")
	public void add( HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept =deptService.fetch(deptid);
		req.setAttribute("dept",dept);

		Kind kind =  kindService.fetch(KIND_ROOT);

		req.setAttribute("kind", kind);

		Location location = locationService.fetch(LOCATION_ROOT);

		req.setAttribute("location", location);




	}

	/**
	 * 新增保存网关
	 */
	@RequiresPermissions("iot:gateway:add")
	@At
	@POST
	@Ok("json")
	@Slog(tag="网关", after="新增保存网关id=${args[0].id}")
	public Object addDo(@Param("..") Gateway gateway, HttpServletRequest req) {
		try {
			gatewayService.insertEntity(gateway);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改网关
	 */
	@At("/edit/?")
	@Ok("th://iot/gateway/edit.html")
	public void edit(String id, HttpServletRequest req) {
		Gateway gateway = gatewayService.fetch(id);
		gateway = gatewayService.fetchLinks(gateway,"^dept|subGateway|kind|location$");
		req.setAttribute("gateway",gateway);
	}

	/**
	 * 修改保存网关
	 */
	@RequiresPermissions("iot:gateway:edit")
	@At
	@POST
	@Ok("json")
	@Slog(tag="业务类型", after="修改保存业务类型")
	public Object editDo(@Param("..") Gateway gateway, HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(gateway)){

				gatewayService.updateEntity(gateway);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除网关
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("iot:gateway:remove")
	@Slog(tag ="网关", after= "删除网关:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			gatewayService.vDelete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 选择GATEWAY注册表
	 */
	@At("/selectExtSno")
	@Ok("th:/iot/gateway/extsno.html")
	public void selectExtSno(HttpServletRequest req) {

		//req.setAttribute("dept", deptService.fetch(id));

	}

	/**
	 * 删除网关
	 */
	@At("/subgateway")
	@Ok("json")
	@Slog(tag ="注册过网关", after= "注册过的网关")
	public Object subgateway(@Param("pageNum")int pageNum,
									   @Param("pageSize")int pageSize,
									   @Param("name") String name,
									   @Param("orderByColumn") String orderByColumn,
									   @Param("isAsc") String isAsc,
									   HttpServletRequest req) {

		Cnd cnd = Cnd.NEW();


		Object obj =gatewayService.selectSub(pageNum,pageSize,cnd,orderByColumn,isAsc,null);

		return obj;
	}


}
