package cn.tico.iot.configmanger.module.wx.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.TUiMetrics;
import cn.tico.iot.configmanger.module.wx.services.TUiMetricsService;
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
 * 一键寻检数据 信息操作处理
 * 
 * @author maodajun
 * @date 2019-08-21
 */
@IocBean
@At("/wx/tUiMetrics")
public class TUiMetricsController {
	private static final Log log = Logs.get();

	@Inject
	private TUiMetricsService tUiMetricsService;
	
	@RequiresPermissions("wx:tUiMetrics:view")
	@At("")
	@Ok("th:/wx/tUiMetrics/tUiMetrics.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询一键寻检数据列表
	 */
	@RequiresPermissions("wx:tUiMetrics:list")
	@At
	@Ok("json")
	public Object list(@Param("pageNum")int pageNum,
					   @Param("pageSize")int pageSize,
					   @Param("name") String name,
					   @Param("beginTime") Date beginTime,
					   @Param("endTime") Date endTime,
					   @Param("orderByColumn") String orderByColumn,
					   @Param("isAsc") String isAsc,
					   HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)){
			//cnd.and("name", "like", "%" + name +"%");
		}
		if(Lang.isNotEmpty(beginTime)){
			cnd.and("create_time",">=", beginTime);
		}
		if(Lang.isNotEmpty(endTime)){
			cnd.and("create_time","<=", endTime);
		}
		return tUiMetricsService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增一键寻检数据
	 */
	@At("/add")
	@Ok("th:/wx/tUiMetrics/add.html")
	public void add( HttpServletRequest req) {

	}

	/**
	 * 新增保存一键寻检数据
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tUiMetrics:add")
	@Slog(tag="一键寻检数据", after="新增保存一键寻检数据 id=${args[0].id}")
	public Object addDo(@Param("..") TUiMetrics tUiMetrics,HttpServletRequest req) {
		try {
			tUiMetricsService.insert(tUiMetrics);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改一键寻检数据
	 */
	@At("/edit/?")
	@Ok("th://wx/tUiMetrics/edit.html")
	public void edit(String id, HttpServletRequest req) {
		TUiMetrics tUiMetrics = tUiMetricsService.fetch(id);
		req.setAttribute("tUiMetrics",tUiMetrics);
	}

	/**
	 * 修改保存一键寻检数据
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tUiMetrics:edit")
	@Slog(tag="一键寻检数据", after="修改保存一键寻检数据")
	public Object editDo(@Param("..") TUiMetrics tUiMetrics,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(tUiMetrics)){
				tUiMetrics.setUpdateBy(ShiroUtils.getSysUserId());
				tUiMetrics.setUpdateTime(new Date());
				tUiMetricsService.update(tUiMetrics);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除一键寻检数据
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tUiMetrics:remove")
	@Slog(tag ="一键寻检数据", after= "删除一键寻检数据:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			tUiMetricsService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
