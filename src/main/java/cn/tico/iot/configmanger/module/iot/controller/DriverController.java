package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Globals;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.UpLoadUtil;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.services.*;
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


	@Inject
	private DeviceService deviceService;

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
	 * 查询业务列表
	 */
	@At("/driver_page")
	@Ok("json")
	public Object driverPage(
			@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize
			, @Param("orderByColumn") String orderByColumn
			, @Param("isAsc") String isAsc
			, HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		cnd.and("delflag","=","false");

		Object obj =  driverService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,"^kind|normals$");

		return Result.success("system.success" ,obj);
	}

	/**
	 * 包起来
	 * @param req
	 * @return
	 */
	@At("/drivers")
	@Ok("json")
	public Object drivers(HttpServletRequest req) {

		return Result.success("system.success" ,driverList(req));
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
			Object obj = driverService.insertSubs(driver);
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
			Object obj =  driverService.insertEntitys(Arrays.asList(driver));
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
			Object obj =  	 driverService.updateEntitys(Arrays.asList(driver));
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
	public Object allNormals(@Param("..") Normal normal, HttpServletRequest req) {
		try {

			Cnd cnd = Cnd.NEW();
			cnd.and("delflag","=","false");
			cnd.and("driver_id","=",normal.getDriverid());
			cnd.orderBy("order_num","asc");
			Object obj = normalService.query(cnd);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 新增保存业务
	 */
	@At("/normal_names")
	@Ok("json")
	public Object allNormalByNames(@Param("cn_name") String name ,@Param("driverid") String driverid, HttpServletRequest req) {
		try {
			Cnd cnd = Cnd.NEW();

			if(Lang.isNotEmpty(name)){
				cnd.and("cn_name","like","%"+name+"%");
			}
			cnd.and("delflag","=","false");
			cnd.and("driver_id","=",driverid);
			cnd.limit(1,50);
			cnd.orderBy("order_num","asc");
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
	public Object editAllNormal(@Param("data") Normal[] normals,@Param("start") Integer start,HttpServletRequest req) {
		try {
			if(Lang.isEmpty(start)){
				start = 0;
			}

			Object obj = normalService.updateAllNormal(Arrays.asList(normals),start);
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

			List obj2 = normalService.updateAllNormal(Arrays.asList(upnormals),obj1.size());
            Normal normal = new Normal();
            normal.setDriverid(driverid);
            List<Device> devices =  deviceService.query(Cnd.where("driver_id","=",driverid));
            deviceService.kafka(devices);


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

		cnd.orderBy("order_num","asc");

		obj =  gradeService.queryEntity(cnd);
		return  Result.success("system.success",   obj );

	}


	@At("/grade_all_save")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object gradeAllSave(@Param("data") Grade[] grades, HttpServletRequest req) {

		Object  obj =  gradeService.saveEntitys(grades);
		return  Result.success("system.success",   obj );

	}
	@At("/grade_add")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object gradeAdd(@Param("data") Grade grade, HttpServletRequest req) {

		try {
			int i = Integer.parseInt(""+(new Date().getTime()%1000000000));
			grade.setOrderNum(i);
			Object obj =  gradeService.insert(grade);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}

	}
    @At("/ruler_add")
    @POST
    @AdaptBy(type = JsonAdaptor.class)
    @Ok("json")
    public Object rulerAdd(@Param("data") Ruler[] ruler, @Param("gradeid") String gradeid,HttpServletRequest req) {

        try {
            Object obj =  rulerService.insertRuler(ruler,gradeid,new Date().getTime());
            return Result.success("system.success",obj);
        } catch (Exception e) {
            return Result.error("system.error");
        }

    }
	/**
	 * 新增变更业务
	 */
	@At("/ruler_change")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object rulerChange(@Param("insert") Normal[] innormals,@Param("update") Normal[] upnormals, @Param("driverid") String driverid,HttpServletRequest req) {
		try {
			List obj1 = normalService.insertAllNormal(Arrays.asList(innormals),driverid);

			List obj2 = normalService.updateAllNormal(Arrays.asList(upnormals),obj1.size());
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
	@At("/grade_remove")
	@Ok("json")
    @POST
	// @AdaptBy(type = JsonAdaptor.class)
	public Object removeGrade(@Param("id")String id, HttpServletRequest req) {
		try {
		    int i = gradeService.deleteEntityAndSub(id);
			return Result.success("system.success",i );
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

    /**
     * 删除业务
     */
    @At("/ruler_remove")
    @Ok("json")
    @POST
    // @AdaptBy(type = JsonAdaptor.class)
    public Object removeRule(@Param("id")String id, HttpServletRequest req) {
        try {

            int i = rulerService.delete(id);
            return Result.success("system.success",i);
        } catch (Exception e) {
        	e.printStackTrace();
            return Result.error("system.error");
        }
    }
	@At("/driver_temple")
	@Ok("json")
	public Object gradeList(@Param("kind") String kind, HttpServletRequest req) {
		Object obj = null;
		obj =  Globals.MyConfig.get(kind);
		return  Result.success("system.success",   obj );

	}
	@At("/driver_kind")
	@Ok("json")
	public Object driverKind(@Param("kindid") String kindid, HttpServletRequest req) {
    	Cnd cnd = Cnd.NEW();
    	if(Lang.isEmpty(kindid)){

		}

    	List<Driver> drivers = driverService.query(cnd);
		return  Result.success("system.success",  drivers );

	}

}
