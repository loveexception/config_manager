package cn.tico.iot.configmanger.module.wx.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.TIotDevopsReason;
import cn.tico.iot.configmanger.module.wx.services.TIotDevopsReasonService;
import cn.tico.iot.configmanger.common.base.Result;
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
import cn.tico.iot.configmanger.common.utils.ShiroUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 故障原因 信息操作处理
 * 
 * @author maodajun
 * @date 2019-10-11
 */
@IocBean
@At("/wx/tIotDevopsReason")
public class TIotDevopsReasonController {
	private static final Log log = Logs.get();

	@Inject
	private TIotDevopsReasonService tIotDevopsReasonService;

	@RequiresPermissions("wx:tIotDevopsReason:view")
	@At("")
	@Ok("th:/wx/tIotDevopsReason/tIotDevopsReason.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询故障原因列表
	 */
	@RequiresPermissions("wx:tIotDevopsReason:list")
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
		return tIotDevopsReasonService.tableList(pageNum, pageSize, cnd, orderByColumn, isAsc, null);
	}

	/**
	 * 新增故障原因
	 */
	@At("/add")
	@Ok("th:/wx/tIotDevopsReason/add.html")
	public void add(HttpServletRequest req) {

	}

	/**
	 * 新增保存故障原因
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tIotDevopsReason:add")
	@Slog(tag = "故障原因", after = "新增保存故障原因 id=${args[0].id}")
	public Object addDo(@Param("..") TIotDevopsReason tIotDevopsReason, HttpServletRequest req) {
		try {
			tIotDevopsReasonService.insert(tIotDevopsReason);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改故障原因
	 */
	@At("/edit/?")
	@Ok("th://wx/tIotDevopsReason/edit.html")
	public void edit(Integer id, HttpServletRequest req) {
		TIotDevopsReason tIotDevopsReason = tIotDevopsReasonService.fetch(id);
		req.setAttribute("tIotDevopsReason", tIotDevopsReason);
	}

	/**
	 * 修改保存故障原因
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tIotDevopsReason:edit")
	@Slog(tag = "故障原因", after = "修改保存故障原因")
	public Object editDo(@Param("..") TIotDevopsReason tIotDevopsReason, HttpServletRequest req) {
		try {
			if (Lang.isNotEmpty(tIotDevopsReason)) {
				// tIotDevopsReason.setUpdateBy(ShiroUtils.getSysUserId());
				// tIotDevopsReason.setUpdateTime(new Date());
				tIotDevopsReasonService.update(tIotDevopsReason);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除故障原因
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tIotDevopsReason:remove")
	@Slog(tag = "故障原因", after = "删除故障原因:${array2str(args[0])}")
	public Object remove(@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			tIotDevopsReasonService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
