package cn.tico.iot.configmanger.module.wx.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.OtherEmp;
import cn.tico.iot.configmanger.module.wx.services.OtherEmpService;
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
 * 运维人员 信息操作处理
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@IocBean
@At("/wx/otherEmp")
public class OtherEmpController {
	private static final Log log = Logs.get();

	@Inject
	private OtherEmpService otherEmpService;
	
	@RequiresPermissions("wx:otherEmp:view")
	@At("")
	@Ok("th:/wx/otherEmp/otherEmp.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询运维人员列表
	 */
	@RequiresPermissions("wx:otherEmp:list")
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
		return otherEmpService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增运维人员
	 */
	@At("/add")
	@Ok("th:/wx/otherEmp/add.html")
	public void add( HttpServletRequest req) {

	}

	/**
	 * 新增保存运维人员
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:otherEmp:add")
	//@Slog(tag="运维人员", after="新增保存运维人员 id=${args[0].id}")
	public Object addDo(@Param("..") OtherEmp otherEmp,HttpServletRequest req) {
		try {
			otherEmp.setCreateBy(ShiroUtils.getSysUserId());
			otherEmp.setCreateTime(new Date());
//			otherEmpService.insert(otherEmp);
			otherEmpService.addEmp(otherEmp);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改运维人员
	 */
	@At("/edit/?")
	@Ok("th://wx/otherEmp/edit.html")
	public void edit(String id, HttpServletRequest req) {
		OtherEmp otherEmp = otherEmpService.fetch(id);
		req.setAttribute("otherEmp",otherEmp);
	}

	/**
	 * 修改保存运维人员
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:otherEmp:edit")
	@Slog(tag="运维人员", after="修改保存运维人员")
	public Object editDo(@Param("..") OtherEmp otherEmp,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(otherEmp)){
				otherEmp.setUpdateBy(ShiroUtils.getSysUserId());
				otherEmp.setUpdateTime(new Date());
//				otherEmpService.update(otherEmp);
				otherEmpService.updateEmp(otherEmp);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除运维人员
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:otherEmp:remove")
	@Slog(tag ="运维人员", after= "删除运维人员:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			otherEmpService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
