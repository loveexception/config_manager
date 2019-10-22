package cn.tico.iot.configmanger.module.wx.controller;

import cn.tico.iot.configmanger.module.other.models.Emp;
import cn.tico.iot.configmanger.module.sys.models.Role;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.wx.models.OtherEmp;
import cn.tico.iot.configmanger.module.wx.services.OtherEmpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.OtherWorkflow;
import cn.tico.iot.configmanger.module.wx.services.OtherWorkflowService;
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
import java.util.List;

/**
 * 运维 信息操作处理
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@IocBean
@At("/wx/otherWorkflow")
public class OtherWorkflowController {
	private static final Log log = Logs.get();

	@Inject
	private OtherWorkflowService otherWorkflowService;

	@Inject
	private OtherEmpService otherEmpService;

	@RequiresPermissions("wx:otherWorkflow:view")
	@At("")
	@Ok("th:/wx/otherWorkflow/otherWorkflow.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询运维列表
	 */
	@RequiresPermissions("wx:otherWorkflow:list")
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
		return otherWorkflowService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增运维
	 */
	@At("/add")
	@Ok("th:/wx/otherWorkflow/add.html")
	public void add( HttpServletRequest req) {
		List<OtherEmp> emps = otherEmpService.query(
				Cnd.where("status","=",true)
						.and("delflag","=",false));
		req.setAttribute("emps",emps);

	}

	/**
	 * 新增保存运维
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:otherWorkflow:add")
//	@Slog(tag="运维班组", after="新增保存运维班组 id=${args[0].id}")
	public Object addDo(@Param("..") OtherWorkflow otherWorkflow,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(otherWorkflow)){
				otherWorkflow.setCreateBy(ShiroUtils.getSysUserId());
				otherWorkflow.setCreateTime(new Date());
				otherWorkflow.setDelflag("false");
				otherWorkflowService.insertEntity(otherWorkflow);
				return Result.success("system.success",otherWorkflow);

			}
			return Result.success("system.error");

		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改运维
	 */
	@At("/edit/?")
	@Ok("th://wx/otherWorkflow/edit.html")
	public void edit(String id, HttpServletRequest req) {
		OtherWorkflow otherWorkflow = otherWorkflowService.fetch(id);
		req.setAttribute("otherWorkflow",otherWorkflow);

		otherWorkflowService.fetchLinks(otherWorkflow,"emps");
		List<OtherEmp> emps = otherEmpService.query(Cnd.where("status","=",true).and("delflag","=",false));
		emps.forEach(emp -> {
			if(otherWorkflow.getEmps()!=null && otherWorkflow.getEmps().size()>0){
				emp.setFlag(""+otherWorkflow.getEmps().contains(emp));
			}
		});
		req.setAttribute("emps",emps);
	}

	/**
	 * 修改保存运维
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:otherWorkflow:edit")
//	@Slog(tag="运维班组", after="修改保存运维班组")
	public Object editDo(@Param("..") OtherWorkflow otherWorkflow,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(otherWorkflow)){
				otherWorkflow.setUpdateBy(ShiroUtils.getSysUserId());
				otherWorkflow.setUpdateTime(new Date());
				otherWorkflowService.updateEntity(otherWorkflow);
			}
			return Result.success("system.success",otherWorkflow);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除运维班组
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:otherWorkflow:remove")
//	@Slog(tag ="运维班组", after= "删除运维班组:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			otherWorkflowService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
