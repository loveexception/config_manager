package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.UpLoadUtil;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import cn.tico.iot.configmanger.iot.services.DriverService;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import cn.tico.iot.configmanger.iot.services.NormalService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 业务 信息操作处理
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean
@At("/iot/driver")
public class DriverController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private DriverService driverService;
	@Inject
	private NormalService normalService;
	@Inject
	private UserService userService;

	@RequiresPermissions("iot:driver:view")
	@At("")
	@Ok("th:/iot/driver/list.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询业务列表
	 */
	@At("/driver_list")
	@Ok("json")
	public Object driverList(HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		return driverService.tableList(0,1000,cnd,null,null,"^location|kind|$");
	}


	/**
	 * 删除业务
	 */
	@At("/driver_one")
	@Ok("json")
	@Slog(tag ="驱动", after= "删除驱动:${array2str(args[0])}")
	public Object fetch(@Param("id") String id, HttpServletRequest req) {
		try {
			Driver obj =  driverService.fetch(id);
			driverService.fetchLinks(obj,"^normals|kind$");

			return Result.success("system.success" ,obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除业务
	 */
	@At("/driver_remove")
	@Ok("json")
	@Slog(tag ="驱动", after= "删除驱动:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			int  i = driverService.vDelete(ids);
			return Result.success("system.success",i);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}







	/**
	 * 新增保存业务
	 */
	@At("/driver_insert_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object addAllDriver(@Param("data") Driver[] driver,HttpServletRequest req) {
		try {
			Object obj =  driverService.insertDrivers(Arrays.asList(driver));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 * 新增变更业务
	 */
	@At("/driver_update_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object editAllDriver(@Param("data") Driver[] driver,HttpServletRequest req) {
		try {
			Object obj =  	 driverService.updateDrivers(Arrays.asList(driver));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	/**
	 * 新增保存业务
	 */
	@At("/normal_insert_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object addAllnormal(@Param("data") Normal[] driver,HttpServletRequest req) {
		try {
			Object obj =  normalService.insertAllNormal(Arrays.asList(driver));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 * 新增变更业务
	 */
	@At("/normal_update_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object editAllNormal(@Param("data") Normal[] driver,HttpServletRequest req) {
		try {
			Object obj = normalService.updateAllNormal(Arrays.asList(driver));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	/**
	 * 新增保存业务
	 */
	@At("/normal_insert_all")
	@POST
	@Ok("json")
	@Slog(tag="业务", after="新增保存业务id=${args[0].id}")
	public Object addNormals(List<Normal> normals, HttpServletRequest req) {
		try {
			normalService.insertAllNormal(normals);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	/**
	 * 删除业务
	 */
	@At("/normal_remove")
	@Ok("json")
	@Slog(tag ="驱动", after= "删除驱动:${array2str(args[0])}")
	public Object removeNormal(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			int  i = normalService.vDelete(ids);
			return Result.success("system.success",i);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	@At("/dorp_types")
	@Ok("json")
	public Object dorpTypes(HttpServletRequest req) {
		NutMap map =NutMap.NEW();
		map.addv("deptName","所属组织");
		map.addv("locationName","所属地区");
		return Result.success("system.success" ,map);
	}

	@At("/driver_upload")
	@Ok("json")
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object upload(@Param("Filedata") TempFile tf, HttpServletRequest req, AdaptorErrorContext err) {
		try {
			if (err != null && err.getAdaptorErr() != null) {
				return NutMap.NEW().addv("code", 1).addv("msg", "文件不合法");
			} else if (tf == null) {
				return Result.error("空文件");
			} else {
				String url = UpLoadUtil.upLoadFileSysConfigPath(tf,"driver");
				String u = req.getServletContext().getContextPath();
				return Result.success("上传成功",  u + url );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("系统错误");
		} catch (Throwable e) {
			return Result.error("格式错误");
		}
	}






}