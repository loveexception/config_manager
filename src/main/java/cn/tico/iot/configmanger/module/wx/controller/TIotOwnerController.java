package cn.tico.iot.configmanger.module.wx.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.TIotOwner;
import cn.tico.iot.configmanger.module.wx.services.TIotOwnerService;
import cn.tico.iot.configmanger.common.base.Result;;
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
 * 资产管理 信息操作处理
 * 
 * @author maodajun
 * @date 2019-11-06
 */
@IocBean
@At("/wx/tIotOwner")
public class TIotOwnerController {
	private static final Log log = Logs.get();

	@Inject
	private TIotOwnerService tIotOwnerService;

	@RequiresPermissions("wx:tIotOwner:view")
	@At("")
	@Ok("th:/wx/tIotOwner/tIotOwner.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询资产管理列表
	 */
	@RequiresPermissions("wx:tIotOwner:list")
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
		return tIotOwnerService.tableList(pageNum, pageSize, cnd, orderByColumn, isAsc, null);
	}

	/**
	 * 新增资产管理
	 */
	@At("/add")
	@Ok("th:/wx/tIotOwner/add.html")
	public void add(HttpServletRequest req) {

	}

	/**
	 * 新增保存资产管理
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tIotOwner:add")
	@Slog(tag = "资产管理", after = "新增保存资产管理 id=${args[0].id}")
	public Object addDo(@Param("..") TIotOwner tIotOwner, HttpServletRequest req) {
		try {
			tIotOwnerService.insert(tIotOwner);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改资产管理
	 */
	@At("/edit/?")
	@Ok("th://wx/tIotOwner/edit.html")
	public void edit(String id, HttpServletRequest req) {
		TIotOwner tIotOwner = tIotOwnerService.fetch(id);
		req.setAttribute("tIotOwner", tIotOwner);
	}

	/**
	 * 修改保存资产管理
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tIotOwner:edit")
	@Slog(tag = "资产管理", after = "修改保存资产管理")
	public Object editDo(@Param("..") TIotOwner tIotOwner, HttpServletRequest req) {
		try {
			if (Lang.isNotEmpty(tIotOwner)) {
				tIotOwner.setUpdateBy(ShiroUtils.getSysUserId());
				tIotOwner.setUpdateTime(new Date());
				tIotOwnerService.update(tIotOwner);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除资产管理
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tIotOwner:remove")
	@Slog(tag = "资产管理", after = "删除资产管理:${array2str(args[0])}")
	public Object remove(@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			tIotOwnerService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
