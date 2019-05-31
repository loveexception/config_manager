package cn.tico.iot.configmanger.module.monitor.controller;

import cn.tico.iot.configmanger.common.base.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.monitor.services.LogininforService;
;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统访问记录 信息操作处理
 * 
 * @author haiming
 * @date 2019-04-18
 */
@IocBean
@At("/monitor/logininfor")
public class LogininforController {
	private static final Log log = Logs.get();

	@Inject
	private LogininforService logininforService;
	
	@RequiresPermissions("monitor:logininfor:view")
	@At("")
	@Ok("th:/monitor/logininfor/logininfor.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询系统访问记录列表
	 */
	@RequiresPermissions("monitor:logininfor:list")
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
		}
		return logininforService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}


	/**
	 * 删除系统访问记录
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("monitor:logininfor:remove")
	@Slog(tag ="系统访问记录", after= "删除系统访问记录:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			logininforService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	@At("/clean")
	@Ok("json")
	@Slog(tag="系统访问记录", after="清除系统访问记录")
	@RequiresPermissions("monitor:logininfor:remove")
	public Object clean()
	{
		logininforService.cleanLogininfor();
		return Result.success("system.success");
	}

}
