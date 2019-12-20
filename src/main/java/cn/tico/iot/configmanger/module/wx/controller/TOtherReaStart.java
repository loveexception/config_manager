package cn.tico.iot.configmanger.module.wx.controller;

import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.TOtherMessages;
import cn.tico.iot.configmanger.module.wx.services.TOtherMessagesService;
import io.leangen.graphql.execution.relay.Page;
import cn.tico.iot.configmanger.common.base.Result;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
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
import cn.tico.iot.configmanger.common.utils.ShiroUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * kafka的推送 信息操作处理
 * 
 * @author maodajun
 * @date 2019-11-21
 */
@IocBean
@At("/wx/tOtherReaStart") //
public class TOtherReaStart {
	private static final Log log = Logs.get();

	@Inject
	private TOtherMessagesService tOtherMessagesService;
	@Inject
	public DeptService deptService;

	@RequiresPermissions("wx:tOtherReaStart:view")
	@At("")
	@Ok("th:/wx/tOtherReaStart/tOtherReaStart.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询kafka的推送列表
	 */
	@RequiresPermissions("wx:tOtherMessages:list")
	@At
	@Ok("json")
	public Object list(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("name") String name,
			@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,
			@Param("orderByColumn") String orderByColumn, @Param("isAsc") String isAsc, HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)) {
			// cnd.and("name", "like", "%" + name +"%");
		}
		if (Lang.isNotEmpty(beginTime)) {
			cnd.and("create_time", ">=", beginTime);
		}
		if (Lang.isNotEmpty(endTime)) {
			cnd.and("create_time", "<=", endTime);
		}
		return tOtherMessagesService.tableList(pageNum, pageSize, cnd, orderByColumn, isAsc, "dept");
	}

	/**
	 * 新增kafka的推送
	 */
	@At("/add")
	@Ok("th:/wx/tOtherMessages/add.html")
	public void add(HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept = deptService.fetch(deptid);
		req.setAttribute("dept", dept);
	}

	/**
	 * 新增保存kafka的推送
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tOtherMessages:add")
	public Object addDo(@Param("..") TOtherMessages tOtherMessages, HttpServletRequest req) {
		try {
			tOtherMessagesService.insert(tOtherMessages);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改kafka的推送
	 */
	@At("/edit/?")
	@Ok("th://wx/tOtherMessages/edit.html")
	public void edit(String id, HttpServletRequest req) {
		TOtherMessages tOtherMessages = tOtherMessagesService.fetch(id);
		tOtherMessages = tOtherMessagesService.fetchLinks(tOtherMessages, "dept");

		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept = deptService.fetch(deptid);
		//
		req.setAttribute("tOtherMessages", tOtherMessages);
	}

	/**
	 * 修改保存kafka的推送
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tOtherMessages:edit")
	@Slog(tag = "kafka的推送", after = "修改保存kafka的推送")
	public Object editDo(@Param("..") TOtherMessages tOtherMessages, HttpServletRequest req) {
		try {
			if (Lang.isNotEmpty(tOtherMessages)) {
				tOtherMessages.setUpdateBy(ShiroUtils.getSysUserId());
				tOtherMessages.setUpdateTime(new Date());
				tOtherMessagesService.update(tOtherMessages);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除kafka的推送
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tOtherMessages:remove")
	public Object remove(@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			tOtherMessagesService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	@At("/change")
	@Ok("json")
	public Object count(@Param("..") TOtherMessages tOtherMessages, HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		String deptid = ShiroUtils.getSysUser().getDeptId();

		cnd.and("dept_id", "=", deptid);
		Object obj = tOtherMessagesService.count(cnd);
		// cnd.and("delflag", "=", "false");
		// tOtherMessagesService.insert(tOtherMessages);
		return Result.success("system.success", obj);
		// return Result.error("system.error");
	}

	// @At("/count") 资产个数
	// @Ok("json")
	// @Slog(tag = "设备资本", after = "删除设备资本:${array2str(args[0])}")
	// public Object count(@Param("next_time") String time, HttpServletRequest req)
	// {
	// try {
	// String deptid = null;
	// if (!isAdmin()) {
	// deptid = ShiroUtils.getSysUser().getDeptId();
	// }
	// List<Map> deviceIds = tIotOwnerService.queryCountTimeOutDeviceIds(deptid,
	// time);

	// NutMap map = NutMap.NEW();
	// map.addv("count", deviceIds.size());

	// return Result.success("system.success", map);
	// } catch (Exception e) {
	// return Result.error("system.error");
	// }
	// }

	/**
	 * 删除资产管理 //
	 */
	// @At("/remove")
	// @Ok("json")
	// @RequiresPermissions("wx:tIotOwner:remove")
	// @Slog(tag = "资产管理", after = "删除资产管理:${array2str(args[0])}")
	// public Object remove(@Param("ids") String[] ids, HttpServletRequest req) {
	// try {
	// tIotOwnerService.delete(ids);
	// return Result.success("system.success");
	// } catch (Exception e) {
	// return Result.error("system.error");
	// }
	// }

	/**
	 * 过期设备列表
	 */
	@At("/messages")
	@Ok("json")
	public Object messages(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, HttpServletRequest req) {

		String deptid = null;
		deptid = ShiroUtils.getSysUser().getDeptId();
		Cnd cnd = Cnd.NEW();
		cnd.and("dept_id", "=", deptid);
		Pager pager = new Pager();
		List list = tOtherMessagesService.query(cnd, pager);
		return Result.success("system.success", list);
		// List<Map> deviceIds = tIotOwnerService.queryCountTimeOutDeviceIds(deptid,
		// time);

		// List<String> ids = deviceIds.stream().map(m ->
		// m.get("id").toString()).collect(Collectors.toList());

		// Cnd cnd = Cnd.NEW();
		// cnd.and("id", "in", ids);

		// cnd.and("delflag", "=", "false");
		// cnd.and("status", "=", "true");
		// cnd.and("asset_status", "=", "2");
		// TableDataInfo info = deviceService.tableList(pageNum, pageSize, cnd,
		// orderByColumn, isAsc,
		// "^dept|kind|owner|next$");
		// List<Device> list = (List<Device>) info.getRows();
		// List<Kind> kinds = kindService.query(Cnd.NEW().and("delflag", "=",
		// "false").and("level", "=", "3"));
		// myKindNameFind(list, kinds);
		// return info;
	}
}
