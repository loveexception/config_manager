package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.base.Globals;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.UpLoadUtil;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import cn.tico.iot.configmanger.iot.services.DriverService;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import cn.tico.iot.configmanger.iot.services.GradeService;
import cn.tico.iot.configmanger.iot.services.NormalService;
import cn.tico.iot.configmanger.iot.services.RulerService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
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
import java.util.*;

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

	@Inject
	private GradeService gradeService;

	@Inject
    private RulerService rulerService;

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
		cnd.and("delflag","=","false");
		return driverService.tableList(0,1000,cnd,"updateTime","desc","^kind|normals$");
	}


	/**
	 * 查寻业务
	 */
	@At("/driver_one")
	@Ok("json")
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
	@AdaptBy(type = JsonAdaptor.class)
	public Object abc(@Param("..")Driver driver, HttpServletRequest req) {
		try {
			int  i = driverService.vDelete(driver.getId());
			return Result.success("system.success",i);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 新增保存业务
	 */
	@At("/driver_insert_update")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object addDriverupdate(@Param("data") Driver driver, HttpServletRequest req) {
		try {
			Object obj = driverService.insertNormal(driver);
			return Result.success("system.success",obj);
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
	@At("/normal_list")
	@Ok("json")
	public Object allNormals(@Param("..") Normal normal,HttpServletRequest req) {
		try {

			Cnd cnd = Cnd.NEW();
			cnd.and("delflag","=","false");
			cnd.and("driver_id","=",normal.getDriverid());
			cnd.orderBy("order_num","desc");
			Object obj = normalService.query(cnd);
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
	public Object editAllNormal(@Param("data") Normal[] normals,HttpServletRequest req) {
		try {
			Object obj = normalService.updateAllNormal(Arrays.asList(normals));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 * 新增变更业务
	 */
	@At("/normal_change")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object changeNormal(@Param("insert") Normal[] innormals,@Param("update") Normal[] upnormals, @Param("driverid") String driverid,HttpServletRequest req) {
		try {
			List obj1 = normalService.insertAllNormal(Arrays.asList(innormals),driverid);

			List obj2 = normalService.updateAllNormal(Arrays.asList(upnormals));
            Normal normal = new Normal();
            normal.setDriverid(driverid);
            return allNormals(normal,req);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	/**
	 * 新增保存业务
	 */
	@At("/normal_insert_all")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object addNormals(@Param("data") Normal[] normals , @Param("driverid") String driverid, HttpServletRequest req) {
		try {
			Object obj = normalService.insertAllNormal(Arrays.asList(normals),driverid);
			Normal normal = new Normal();
			normal.setDriverid(driverid);
			return allNormals(normal,req);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	/**
	 * 删除业务
	 */
	@At("/normal_remove")
	@Ok("json")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	public Object removeNormal(@Param("data")String[] ids, HttpServletRequest req) {
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
				Map map = new HashMap<String,String>();
				String name = tf.getName();
				String url = UpLoadUtil.upLoadFileSysConfigPath(tf,"driver");
				String u = req.getServletContext().getContextPath();
				map.put("name",tf.getSubmittedFileName());
				map.put("url",url);
				map.put("file",tf.getFile().getName());
				return Result.success("上传成功",  map );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("系统错误");
		} catch (Throwable e) {
			return Result.error("格式错误");
		}
	}
	@At("/normal_csv_path")
	@Ok("json")
	public Object csvPath( HttpServletRequest req) {

				String url = Globals.AppUploadPath+"/csv/"+"normal.csv";
				return Result.success("下载地址",   url );

	}


	@At("/grade_list")
	@Ok("json")
	public Object gradeList(@Param("..") Grade grade, HttpServletRequest req) {
		Object obj = null;

		Cnd cnd = Cnd.NEW();
		cnd.and("normal_id","=",grade.getNormalid());
		if(Strings.isNotBlank(grade.getGrade())){
			cnd.and("grade","=",grade.getGrade());
		}

		obj =  gradeService.query(cnd);
		return  Result.success("system.success",   obj );

	}
	@At("/grade_add")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object gradeAdd(@Param("..") Grade grade, HttpServletRequest req) {

		try {
			Object obj =  gradeService.insert(grade);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}

	}


	/**
	 * 删除业务
	 */
	@At("/grade_remove")
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object removeGrade(@Param("data")String[] ids, HttpServletRequest req) {
		try {
			int  i = gradeService.vDelete(ids);
			return Result.success("system.success",i);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

    /**
     * 删除业务
     */
    @At("/ruler_remove")
    @Ok("json")
    @AdaptBy(type = JsonAdaptor.class)
    public Object removeRule(@Param("data")String[] ids, HttpServletRequest req) {
        try {
            rulerService.delete(ids);
            return Result.success("system.success",ids);
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }


}
