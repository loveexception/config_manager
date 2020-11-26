package cn.tico.iot.configmanger.module.wx.controller;

import cn.tico.iot.configmanger.module.iot.controller.AdminKey;
import cn.tico.iot.configmanger.module.iot.models.Topo.Base;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.services.TagService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.Role;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.services.TopoBasesService;
import cn.tico.iot.configmanger.common.base.Result;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 拓扑图存储 信息操作处理
 * 
 * @author maodajun
 * @date 2019-12-05
 */
@IocBean
@At("/wx/tTopoBases")
public class TTopoBasesController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private TopoBasesService tTopoBasesService;
	@Inject
	private DeptService deptService;
	@Inject
	private TagService tagService;

	@Inject
	private UserService userService;

	
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
		if(!isAdmin()){
			cnd.and("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());;
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
		Cnd cnd = Cnd.NEW();

		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}

		cnd.and("delflag","=","false");

        cnd.and("en_name","like","%\\_show");

		List<Tag> tags =tagService.query(cnd);


		req.setAttribute("show",tags);

		cnd = Cnd.NEW();
		cnd.and("delflag","=","false");
		cnd.and("en_name","like","%\\_unshow");
		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}
	 	tags =tagService.query(cnd);
		req.setAttribute("hide",tags);

	}
	/**
	 * 用户权限
	 * @return
	 */
	private boolean isAdmin() {

		User user = ShiroUtils.getSysUser();

		Set roles = userService.getRoleCodeList(user);

		return roles.contains(ROLE_ADMIN);
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
		Base base = tTopoBasesService.fetch(id);
		base = tTopoBasesService.fetchLinks(base,"");

		req.setAttribute("tTopoBases",base);
		final  Base tTopoBases = base;
		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();

		Cnd cnd = Cnd.NEW();

		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}

		cnd.and("delflag","=","false");

		cnd.and("en_name","like","%\\_show");

		List<Tag> tags =tagService.query(cnd);
		List<NutMap> result =  tags.stream().map(tag -> {
			NutMap map = NutMap.NEW()
					.addv("id",tag.getId())
					.addv("cnName",tag.getCnName())
					.addv("enName",tag.getEnName())
					.addv("deptId",tag.getDeptid())
					;
			if(Strings.equalsIgnoreCase(tTopoBases.getHideTagId(),tag.getId())){
				map.addv("hide","true");
			}
			if(Strings.equalsIgnoreCase(tTopoBases.getShowTagId(),tag.getId())){
				map.addv("show","true");
			}

			return map;
		}).collect(Collectors.toList());

		req.setAttribute("show",result);

		cnd = Cnd.NEW();
		cnd.and("delflag","=","false");
		cnd.and("en_name","like","%\\_unshow");
		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}
		tags =tagService.query(cnd);


		result =  tags.stream().map(tag -> {
			NutMap map = NutMap.NEW()
					.addv("id",tag.getId())
					.addv("cnName",tag.getCnName())
					.addv("enName",tag.getEnName())
					.addv("deptId",tag.getDeptid())
					;
			if(Strings.equalsIgnoreCase(tTopoBases.getHideTagId(),tag.getId())){
				map.addv("hide","true");
			}
			if(Strings.equalsIgnoreCase(tTopoBases.getShowTagId(),tag.getId())){
				map.addv("show","true");
			}

			return map;
		}).collect(Collectors.toList());
		req.setAttribute("hide",result);






//		List<NutMap> result =  tags.stream().map(tag -> {
//			NutMap map = NutMap.NEW()
//					.addv("id",tag.getId())
//					.addv("cnName",tag.getCnName())
//					.addv("enName",tag.getEnName())
//					.addv("deptId",tag.getDeptid())
//					;
//			if(Strings.equalsIgnoreCase(tTopoBases.getHideTagId(),tag.getId())){
//				map.addv("hide","true");
//			}
//			if(Strings.equalsIgnoreCase(tTopoBases.getShowTagId(),tag.getId())){
//				map.addv("show","true");
//			}
//
//			return map;
//		}).collect(Collectors.toList());
//		req.setAttribute("show",result);
//		req.setAttribute("hide",result);
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
