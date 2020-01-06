package cn.tico.iot.configmanger.module.mao.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.tico.iot.configmanger.module.mao.models.Upgrades;
import cn.tico.iot.configmanger.module.mao.services.UpgradesService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;

import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 告警升级 信息操作处理
 * 
 * @author maodajun
 * @date 2020-01-03
 */
@IocBean
@At("/mao/upgrades")
public class UpgradesController {
	private static final Log log = Logs.get();

	@Inject
	private UpgradesService upgradesService;

	@Inject DeptService deptService;
	
	@At("")
	@Ok("th:/mao/upgrades/upgrades.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询告警升级列表
	 */
	@At
	@Ok("json")
	public Object list(@Param("pageNum")int pageNum,
					   @Param("pageSize")int pageSize,
					   @Param("cnName") String name,
					   @Param("beginTime") Date beginTime,
					   @Param("endTime") Date endTime,
					   @Param("orderByColumn") String orderByColumn,
					   @Param("isAsc") String isAsc,
					   @Param("adress") String adress,
					   @Param("countDown") int countDown,
					   HttpServletRequest req) {
						String deptid = ShiroUtils.getSysUser().getDeptId();
						Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)){

			cnd.and("cnName", "like", "%" + name +"%");
		}
		if(Lang.isNotEmpty(beginTime)){
			cnd.and("create_time",">=", beginTime);
		}
		if(Lang.isNotEmpty(endTime)){
			cnd.and("create_time","<=", endTime);
		}
		if(Strings.isNotBlank(adress)){
			cnd.and("adress","like","%"+adress+"%");
		}
		if(Strings.isNotBlank(deptid)){
			cnd.and("dept_id","=", deptid);
			cnd.and("countDown","=", countDown);
			//countDown
		}
		return upgradesService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}
	/**
	 * 新增告警升级
	 */
	@At("/add")
	@Ok("th:/mao/upgrades/add.html")
	public void add( HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept = deptService.fetch(deptid);
		req.setAttribute("dept", dept);

	}

	/**
	 * 新增保存告警升级
	 */
	@At
	@POST
	@Ok("json")
	@Slog(tag="告警升级", after="新增保存告警升级 id=${args[0].id}")
	public Object addDo(@Param("..") Upgrades upgrades,HttpServletRequest req) {
		try {
			String deptid = ShiroUtils.getSysUser().getDeptId();
			if(Strings.isNotBlank(deptid)){
				upgrades.setDeptId(deptid);
			}
			upgradesService.insert(upgrades);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改告警升级
	 */
	@At("/edit/?")
	@Ok("th:/mao/upgrades/edit.html")
	public void edit(String id, HttpServletRequest req) {
		Upgrades upgrades = upgradesService.fetch(id);
		req.setAttribute("upgrades",upgrades);
	}

	/**
	 * 修改保存告警升级
	 */
	@At
	@POST
	@Ok("json")
	@Slog(tag="告警升级", after="修改保存告警升级")
	public Object editDo(@Param("..") Upgrades upgrades,HttpServletRequest req) {
		// try {
			if(Lang.isNotEmpty(upgrades)){
				upgrades.setUpdateBy(ShiroUtils.getSysUserId());
				upgrades.setUpdateTime(new Date());
				upgradesService.update(upgrades);
				return Result.success("system.success");
			}else{
				return Result.error("system.error");
			}
		// } catch (Exception e) {
		// }
	}

	/**
	 * 删除告警升级
	 */
	@At("/remove")
	@Ok("json")
	@Slog(tag ="告警升级", after= "删除告警升级:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			upgradesService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
