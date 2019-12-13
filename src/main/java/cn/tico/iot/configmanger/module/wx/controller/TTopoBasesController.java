package cn.tico.iot.configmanger.module.wx.controller;

import cn.tico.iot.configmanger.module.iot.models.Topo.Base;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.services.TopoBasesService;
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
 * 拓扑图存储 信息操作处理
 * 
 * @author maodajun
 * @date 2019-12-05
 */
@IocBean
@At("/wx/tTopoBases")
public class TTopoBasesController {
	private static final Log log = Logs.get();

	@Inject
	private TopoBasesService tTopoBasesService;
	@Inject
	private DeptService deptService;

	
	@RequiresPermissions("wx:tTopoBases:view")
	@At("")
	@Ok("th:/wx/tTopoBases/tTopoBases.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询拓扑图存储列表
	 */
	@RequiresPermissions("wx:tTopoBases:list")
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
			cnd.and("cn_name", "like", "%" + name +"%");
		}

		return tTopoBasesService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,"^dept|kind|location|driver|gateway$");
	}

	/**
	 * 新增拓扑图存储
	 */
	@At("/add")
	@Ok("th:/wx/tTopoBases/add.html")
	public void add( HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept = deptService.fetch(deptid);
		req.setAttribute("dept", dept);

	}

	/**
	 * 新增保存拓扑图存储
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tTopoBases:add")
	@Slog(tag="拓扑图存储", after="新增保存拓扑图存储 id=${args[0].id}")
	public Object addDo(@Param("..") Base tTopoBases,HttpServletRequest req) {
		try {
			tTopoBasesService.insert(tTopoBases);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改拓扑图存储
	 */
	@At("/edit/?")
	@Ok("th://wx/tTopoBases/edit.html")
	public void edit(String id, HttpServletRequest req) {
		Base tTopoBases = tTopoBasesService.fetch(id);
		req.setAttribute("tTopoBases",tTopoBases);
	}

	/**
	 * 修改保存拓扑图存储
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tTopoBases:edit")
	@Slog(tag="拓扑图存储", after="修改保存拓扑图存储")
	public Object editDo(@Param("..") Base tTopoBases,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(tTopoBases)){
				tTopoBases.setUpdateBy(ShiroUtils.getSysUserId());
				tTopoBases.setUpdateTime(new Date());
				tTopoBasesService.update(tTopoBases);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除拓扑图存储
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tTopoBases:remove")
	@Slog(tag ="拓扑图存储", after= "删除拓扑图存储:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			tTopoBasesService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
