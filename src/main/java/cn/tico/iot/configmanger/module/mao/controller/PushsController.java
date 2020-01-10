package cn.tico.iot.configmanger.module.mao.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.mao.models.Pushs;
import cn.tico.iot.configmanger.module.mao.services.PushsService;
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
 * 推送方式 信息操作处理
 * 
 * @author maodajun
 * @date 2020-01-10
 */
@IocBean
@At("/mao/pushs")
public class PushsController {
	private static final Log log = Logs.get();

	@Inject
	private PushsService pushsService;
	
	@RequiresPermissions("mao:pushs:view")
	@At("")
	@Ok("th:/mao/pushs/pushs.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询推送方式列表
	 */
	@RequiresPermissions("mao:pushs:list")
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
		return pushsService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增推送方式
	 */
	@At("/add")
	@Ok("th:/mao/pushs/add.html")
	public void add( HttpServletRequest req) {

	}

	/**
	 * 新增保存推送方式
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("mao:pushs:add")
	@Slog(tag="推送方式", after="新增保存推送方式 id=${args[0].id}")
	public Object addDo(@Param("..") Pushs pushs,HttpServletRequest req) {
		try {
			pushsService.insert(pushs);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改推送方式
	 */
	@At("/edit/?")
	@Ok("th://mao/pushs/edit.html")
	public void edit(String id, HttpServletRequest req) {
		Pushs pushs = pushsService.fetch(id);
		req.setAttribute("pushs",pushs);
	}

	/**
	 * 修改保存推送方式
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("mao:pushs:edit")
	@Slog(tag="推送方式", after="修改保存推送方式")
	public Object editDo(@Param("..") Pushs pushs,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(pushs)){
				pushs.setUpdateBy(ShiroUtils.getSysUserId());
				pushs.setUpdateTime(new Date());
				pushsService.update(pushs);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除推送方式
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("mao:pushs:remove")
	@Slog(tag ="推送方式", after= "删除推送方式:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			pushsService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
