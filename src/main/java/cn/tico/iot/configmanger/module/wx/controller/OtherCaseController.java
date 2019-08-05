package cn.tico.iot.configmanger.module.wx.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.OtherCase;
import cn.tico.iot.configmanger.module.wx.services.OtherCaseService;
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
 * 运维 信息操作处理
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@IocBean
@At("/wx/otherCase")
public class OtherCaseController {
	private static final Log log = Logs.get();

	@Inject
	private OtherCaseService otherCaseService;
	
	@RequiresPermissions("wx:otherCase:view")
	@At("")
	@Ok("th:/wx/otherCase/otherCase.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询运维列表
	 */
	@RequiresPermissions("wx:otherCase:list")
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
		return otherCaseService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增运维
	 */
	@At("/add")
	@Ok("th:/wx/otherCase/add.html")
	public void add( HttpServletRequest req) {

	}

	/**
	 * 新增保存运维
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:otherCase:add")
	@Slog(tag="运维", after="新增保存运维 id=${args[0].id}")
	public Object addDo(@Param("..") OtherCase otherCase,HttpServletRequest req) {
		try {
			otherCaseService.insert(otherCase);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改运维
	 */
	@At("/edit/?")
	@Ok("th://wx/otherCase/edit.html")
	public void edit(String id, HttpServletRequest req) {
		OtherCase otherCase = otherCaseService.fetch(id);
		req.setAttribute("otherCase",otherCase);
	}

	/**
	 * 修改保存运维
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:otherCase:edit")
	@Slog(tag="运维", after="修改保存运维")
	public Object editDo(@Param("..") OtherCase otherCase,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(otherCase)){
				otherCase.setUpdateBy(ShiroUtils.getSysUserId());
				otherCase.setUpdateTime(new Date());
				otherCaseService.update(otherCase);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除运维
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:otherCase:remove")
	@Slog(tag ="运维", after= "删除运维:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			otherCaseService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
