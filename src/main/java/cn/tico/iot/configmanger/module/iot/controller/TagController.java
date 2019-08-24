package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.services.TagService;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 业务 信息操作处理
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean
@At("/iot/tag")
public class TagController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private TagService tagService;

	@Inject
	private UserService userService;

	@RequiresPermissions("iot:tag:view")
	@At("")
	@Ok("th:/iot/tag/list.html")
	public void index(HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		Dept dept = new Dept();
		dept.setId(user.getDeptId());

		req.setAttribute("dept",dept);
	}

	/**
	 * 查询业务列表
	 */
	@RequiresPermissions("iot:tag:list")
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
			SqlExpressionGroup group = Cnd.exps("cn_name", "like", "%" + name + "%").or("en_name", "like", "%" + name + "%");
			cnd.and(group);
		}

		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}
		//cnd.and("del_flag","=",false);
		return tagService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 查询业务列表
	 */

	@At("/tag_page")
	@Ok("json")
	public Object page(@Param("pageNum")int pageNum,
					   @Param("pageSize")int pageSize,
					   @Param("name") String name,
					   @Param("orderByColumn") String orderByColumn,
					   @Param("isAsc") String isAsc,
					   HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)){
			SqlExpressionGroup group = Cnd.exps("cn_name", "like", "%" + name + "%").or("en_name", "like", "%" + name + "%");
			cnd.and(group);
		}

		if(!isAdmin()){
			SqlExpressionGroup
					group = Cnd
					.exps("dept_id", "=", DEPT_ADMIN)
					.or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
			cnd.and(group);
		}
		Object obj =  tagService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
		return Result.success("system.success",obj);
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
	 * 新增业务
	 */
	@At("/add")
	@Ok("th:/iot/tag/add.html")
	public void add( HttpServletRequest req) {

	}

	/**
	 * 新增保存业务
	 */
	@RequiresPermissions("iot:tag:add")
	@At
	@POST
	@Ok("json")
	@Slog(tag="业务", after="新增保存业务id=${args[0].id}")
	public Object addDo(@Param("..") Tag tag,HttpServletRequest req) {
		try {
			tagService.insertTag(tag);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改业务
	 */
	@At("/edit/?")
	@Ok("th://iot/tag/edit.html")
	public void edit(String id, HttpServletRequest req) {
		Tag tag = tagService.fetch(id);
		req.setAttribute("tag",tag);
	}

	/**
	 * 修改保存字典
	 */
	@RequiresPermissions("iot:tag:edit")
	@At
	@POST
	@Ok("json")
	@Slog(tag="业务类型", after="修改保存业务类型")
	public Object editDo(@Param("..") Tag tag, HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(tag)){

				tagService.updateTag(tag);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除业务
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("iot:tag:remove")
	@Slog(tag ="业务", after= "删除业务:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			tagService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
