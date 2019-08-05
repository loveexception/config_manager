package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Person;
import cn.tico.iot.configmanger.module.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.module.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.services.*;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;

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
@At("/iot/person")
public class PersonController implements AdminKey {
	private static final Log log = Logs.get();


	@Inject
	private UserService userService;



	@Inject
	private DeviceService deviceService;


	@Inject
	private PersonService personService;

	@Inject
	private PersonGradeService personGradeService;

	@Inject
	private PersonRulerService personRulerService;

	@Inject
	private NormalService normalService;

	@Inject
    private KafkaBlock kafkaBlock;


	/**
	 *  个性化查寻
	 */
	@At("/person")
	@Ok("json")
	public Object person(@Param("..") Person person , HttpServletRequest req) {
		try {
			if(Strings.isEmpty(person.getNormalid())){
				return Result.success("system.success", Lists.newArrayList());
			}
			if(Strings.isEmpty(person.getDeviceid())){
				return Result.success("system.success", Lists.newArrayList());
			}
			Cnd cnd = Cnd.NEW();
			cnd.and("delflag","=","false");
			cnd.and("normal_id","=",person.getNormalid());
			cnd.and("device_id","=",person.getDeviceid());
			List<Person> persons = personService.query(cnd);
			if(Lang.isEmpty(persons)){
				return Result.success("system.success",null);
			}
			Object	obj = personService.queryEntityDeep(persons.get(0));
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	@At("/person_id")
	@Ok("json")
	public Object personID(@Param("..") Person person , HttpServletRequest req) {
		try {
			if(Strings.isEmpty(person.getId())){
				return Result.success("system.success", Lists.newArrayList());
			}
			person =  personService.fetch(person.getId());
			person = personService.queryEntityDeep(person);
			Normal normal = personService.dao().fetchLinks(person.getNormal(),"^driver$");
			person.setNormal(normal);
			return Result.success("system.success",person);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 *  个性化查寻
	 */
	@At("/person_update")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object personUpdate(@Param("data") Person person , HttpServletRequest req) {
		try {
			//Person obj = personService.fetch(person.getId());
			//obj.setStatus(person.getStatus());
			personService.updateEntity(person);

			return Result.success("system.success",person);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 *  个性化删除
	 */
	@At("/person_remove")
	@POST
	@Ok("json")
	public Object personDel(@Param("id") String id , HttpServletRequest req) {
		try {

			int index = personService.deleteEntity(id);
			return Result.success("system.success",index);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}



	/**
	 * 新增个性化标签
	 */
	@At("/find_add_one")
	@POST
	@Ok("json")
	public Object  personAddOne(@Param("..") Person person,HttpServletRequest req) {
		try {
			if(Lang.isEmpty(person)){
				return Result.success("system.success",new Object());
			}

			if(Strings.isEmpty(person.getNormalid())){
				return Result.success("system.success",new Object());
			}
			if(Strings.isEmpty(person.getDeviceid())){
				return Result.success("system.success",new Object());

			}


			Person result = personService.queryEntityDeep(person);


			if(Lang.isNotEmpty(result)){
                return Result.success("system.success",result);

			}else{

			    List<Grade> grades = normalService.querySubs(person.getNormalid());

                List<PersonGrade> personGrades = personGradeService.changeFromGrade(grades) ;

				person.setGrades(personGrades);
				person = personService.insertEntity(person,"^grades|rulers$");

            }

			return Result.success("system.success",person);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	@At("/grade_list")
	@Ok("json")
	public Object  personGrades(@Param("..") PersonGrade grade, HttpServletRequest req) {

		Cnd cnd = Cnd.NEW();
		if(Strings.isBlank(grade.getPersonid())){
			return Result.success("system.success",null);
		}
		cnd.and("personid","=",grade.getPersonid());
		if(Strings.isNotBlank(grade.getGrade())){
			cnd.and("grade","=",grade.getGrade());
		}
		Object obj =  personGradeService.queryEntity(cnd);
		return  Result.success("system.success",   obj );
	}
	/**
	 *  个性化查寻
	 */
	@At("/grade_add")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object personGradeAdd(@Param("data") PersonGrade personGrade , HttpServletRequest req) {
		try {

			personGrade = personGradeService.insertEntity(personGrade);
			return Result.success("system.success",personGrade);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	@At("/grade_remove")
	@POST
	@Ok("json")
	public Object gradeRemove(@Param("id") String id, HttpServletRequest req) {

		try {
			int obj =  personGradeService.deleteEntity(id);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}

	}

    @At("/ruler_add")
    @POST
    @AdaptBy(type = JsonAdaptor.class)
    @Ok("json")
    public Object rulerAdd(@Param("data") PersonRuler[] rulers, @Param("gradeid") String gradeid, HttpServletRequest req) {
        try {
            Object obj =  personRulerService.insertEntitys(Arrays.asList(rulers),gradeid);
            return Result.success("system.success",obj);
        } catch (Exception e) {
            return Result.error("system.error");
        }

    }

	@At("/ruler_remove")
	@POST
	@Ok("json")
	public Object remove(@Param("id") String id, HttpServletRequest req) {
		try {
			int obj =  personRulerService.delete(id);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 * 个性化驱动配置
	 */
	@At("/normal_list")
	@Ok("json")
	public Object allNormals(@Param("deviceid") String deviceid, @Param("driverid")String driverid,  HttpServletRequest req) {
		try {
			Cnd cnd = Cnd.NEW();
			cnd.and("delflag","=","false");
			cnd.and("driver_id","=",driverid);
			cnd.orderBy("order_num","asc");

			List<Normal> normals = normalService.query(cnd);
			normals = personService.checkStatus(normals,deviceid);
			return Result.success("system.success",normals);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

    @At("/grade_all_save")
    @POST
    @AdaptBy(type = JsonAdaptor.class)
    @Ok("json")
    public Object gradeAllSave(@Param("data") PersonGrade[] grades, HttpServletRequest req) {
        List<PersonGrade>  obj =  personGradeService.saveEntitys(grades);
        return  Result.success("system.success",   obj );

    }
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
