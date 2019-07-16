package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.base.Globals;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.common.utils.UpLoadUtil;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.iot.services.*;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
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
@At("/iot/device")
public class DeviceController implements AdminKey {
	private static final Log log = Logs.get();


	@Inject
	private UserService userService;



	@Inject
	private DeviceService deviceService;


	@Inject
	private  PersonService personService;

	@Inject
	private PersonGradeService personGradeService;

	@Inject
	private PersonRulerService personRulerService;


	@RequiresPermissions("iot:device:view")
	@At("")
	@Ok("th:/iot/device/list.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询业务列表
	 */
	@At("/device_list")
	@Ok("json")
	public Object deviceList(
			@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize
			, @Param("name") String name
			, @Param("orderByColumn") String orderByColumn
			, @Param("isAsc") String isAsc
			, @Param("deptid") String deptid
			, @Param("locationid") String locationid,
			HttpServletRequest req) {

		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)) {
			//cnd.and("name", "like", "%" + name +"%");
			SqlExpressionGroup group = Cnd.exps("cn_name", "like", "%" + name + "%")
					.or("en_name", "like", "%" + name + "%")
					.or("sno","like", "%" + name + "%");
			cnd.and(group);
		}
		Dept dept = new Dept();
		SqlExpressionGroup
				group = Cnd
				.exps("dept_id", "=", DEPT_ADMIN);
		if (!isAdmin()) {
			group.or("dept_id", "=", ShiroUtils.getSysUser().getDeptId());
			dept.setId(ShiroUtils.getSysUser().getDeptId());
		}else if(Strings.isNotBlank(deptid)){
			group.or("dept_id", "=",deptid);
		}else {
			group.or("1","=","1");
		}
		if(Strings.isNotBlank(locationid)){
			cnd.and("location_id","=",locationid);
		}


		cnd.and(group);
		cnd.and("delflag", "=", "false");
		Object obj = deviceService.tableList(pageNum, pageSize, cnd, orderByColumn, isAsc, "^dept|kind|location|driver|gateway$");

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
	 * 查寻业务
	 */
	@At("/device_one")
	@Ok("json")
	public Object deviceOne(@Param("id") String id, HttpServletRequest req) {
		try {
			Device obj =  deviceService.fetch(id);
			deviceService.fetchLinks(obj,"^normals|kind$");

			return Result.success("system.success" ,obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除业务
	 */
	@At("/device_remove")
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object deviceRemove(@Param("..")Device device, HttpServletRequest req) {
		try {
			int  i = deviceService.vDelete(device.getId());
			return Result.success("system.success",i);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 新增保存业务
	 */
	@At("/device_insert_update")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object deviceInsertUpdate(@Param("data") Device device, HttpServletRequest req) {
		try {
			Object obj = deviceService.insertUpdate(device);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}





	/**
	 * 新增保存业务
	 */
	@At("/device_insert_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object deviceInsertAll(@Param("data") Driver[] driver,HttpServletRequest req) {
		try {
			Object obj =  deviceService.insertAll(Arrays.asList(driver));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 新增变更业务
	 */
	@At("/device_update_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object editAllDriver(@Param("data") Driver[] driver,HttpServletRequest req) {
		try {
			Object obj =  	 deviceService.updateAll(Arrays.asList(driver));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	/**
	 *  个性化查寻
	 */
	@At("/person_list")
	@Ok("json")
	public Object personList(@Param("..") Person person, HttpServletRequest req) {
		try {

			Cnd cnd = Cnd.NEW();
			cnd.and("delflag","=","false");
			cnd.and("normal_id","=",person.getNormalid());
			cnd.orderBy("order_num","asc");
			Object obj = personService.query(cnd);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 新增变更业务
	 */
	@At("/person_ruler_change")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object personRulerChange(@Param("insert") PersonRuler[] addruler,@Param("update") PersonRuler[] editruler, @Param("normalid") String driverid,HttpServletRequest req) {
		try {
			List obj1 = personRulerService.insertAllRuler(Arrays.asList(addruler),driverid);

			int obj2 = personRulerService.updateAllRuler(Arrays.asList(editruler));
            Person normal = new Person();
            normal.setNormalid(driverid);
            return personList(normal,req);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	/**
	 * 新增保存业务
	 */
//	@At("/person_ruler_insert")
//	@POST
//	@AdaptBy(type = JsonAdaptor.class)
//	@Ok("json")
//	public Object addNormals(@Param("data") Normal[] normals , @Param("driverid") String driverid, HttpServletRequest req) {
//		try {
//			Object obj = normalService.insertAllNormal(Arrays.asList(normals),driverid);
//			Normal normal = new Normal();
//			normal.setDriverid(driverid);
//			return allNormals(normal,req);
//		} catch (Exception e) {
//			return Result.error("system.error");
//		}
//	}

//
//	/**
//	 * 删除业务
//	 */
//	@At("/normal_remove")
//	@Ok("json")
//	@POST
//	@AdaptBy(type = JsonAdaptor.class)
//	public Object removeNormal(@Param("data")String[] ids, HttpServletRequest req) {
//		try {
//			int  i = normalService.vDelete(ids);
//			return Result.success("system.success",i);
//		} catch (Exception e) {
//			return Result.error("system.error");
//		}
//	}
//
//
//
//	@At("/dorp_types")
//	@Ok("json")
//	public Object dorpTypes(HttpServletRequest req) {
//		NutMap map =NutMap.NEW();
//		map.addv("deptName","所属组织");
//		map.addv("locationName","所属地区");
//		return Result.success("system.success" ,map);
//	}
//
//	@At("/driver_upload")
//	@Ok("json")
//	@POST
//	@AdaptBy(type = UploadAdaptor.class)
//	public Object upload(@Param("Filedata") TempFile tf, HttpServletRequest req, AdaptorErrorContext err) {
//		try {
//			if (err != null && err.getAdaptorErr() != null) {
//				return NutMap.NEW().addv("code", 1).addv("msg", "文件不合法");
//			} else if (tf == null) {
//				return Result.error("空文件");
//			} else {
//				Map map = new HashMap<String,String>();
//				String name = tf.getName();
//				String url = UpLoadUtil.upLoadFileSysConfigPath(tf,"driver");
//				String u = req.getServletContext().getContextPath();
//				map.put("name",tf.getSubmittedFileName());
//				map.put("url",url);
//				map.put("file",tf.getFile().getName());
//				return Result.success("上传成功",  map );
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Result.error("系统错误");
//		} catch (Throwable e) {
//			return Result.error("格式错误");
//		}
//	}
//	@At("/normal_csv_path")
//	@Ok("json")
//	public Object csvPath( HttpServletRequest req) {
//
//				String url = Globals.AppUploadPath+"/csv/"+"normal.csv";
//				return Result.success("下载地址",   url );
//
//	}
//
//
//	@At("/grade_list")
//	@Ok("json")
//	public Object gradeList(@Param("..") Grade grade, HttpServletRequest req) {
//		Object obj = null;
//
//		Cnd cnd = Cnd.NEW();
//		cnd.and("normal_id","=",grade.getNormalid());
//		if(Strings.isNotBlank(grade.getGrade())){
//			cnd.and("grade","=",grade.getGrade());
//		}
//
//
//		obj =  gradeService.queryGrade(cnd);
//		return  Result.success(,   obj );
//
//	}
//	@At("/grade_add")
//	@POST
//	@AdaptBy(type = JsonAdaptor.class)
//	@Ok("json")
//	public Object gradeAdd(@Param("data") Grade grade, HttpServletRequest req) {
//
//		try {
//			Object obj =  gradeService.insert(grade);
//			return Result.success("system.success",obj);
//		} catch (Exception e) {
//			return Result.error("system.error");
//		}
//
//	}
//    @At("/ruler_add")
//    @POST
//    @AdaptBy(type = JsonAdaptor.class)
//    @Ok("json")
//    public Object rulerAdd(@Param("data") Ruler[] ruler, @Param("gradeid") String gradeid,HttpServletRequest req) {
//
//        try {
//            Object obj =  rulerService.insertRuler(ruler,gradeid);
//            return Result.success("system.success",obj);
//        } catch (Exception e) {
//            return Result.error("system.error");
//        }
//
//    }
//
//	/**
//	 * 删除业务
//	 */
//	@At("/grade_remove")
//	@Ok("json")
//    @POST
//	@AdaptBy(type = JsonAdaptor.class)
//	public Object removeGrade(@Param("data")String id, HttpServletRequest req) {
//		try {
//		    Grade grade = new Grade();
//		    grade.setId(id);
//		    grade = gradeService.fetchLinks(grade,"^rulers$");
//			int i = gradeService._deleteLinks(grade,"^rulers$");
//			return Result.success("system.success",i );
//		} catch (Exception e) {
//			return Result.error("system.error");
//		}
//	}
//
//    /**
//     * 删除业务
//     */
//    @At("/ruler_remove")
//    @Ok("json")
//    @POST
//    @AdaptBy(type = JsonAdaptor.class)
//    public Object removeRule(@Param("data")String ids, HttpServletRequest req) {
//        try {
//            int i = rulerService.delete(ids);
//            return Result.success("system.success",i);
//        } catch (Exception e) {
//            return Result.error("system.error");
//        }
//    }
//
//

}
