package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.iot.services.DeviceService;
import cn.tico.iot.configmanger.iot.services.PersonGradeService;
import cn.tico.iot.configmanger.iot.services.PersonRulerService;
import cn.tico.iot.configmanger.iot.services.PersonService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
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
import java.util.ArrayList;
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
	private  PersonService personService;

	@Inject
	private PersonGradeService personGradeService;

	@Inject
	private PersonRulerService personRulerService;

	@Inject
    private KafkaBlock kafkaBlock;


	/**
	 *  个性化查寻
	 */
	@At("/person_list")
	@Ok("json")
	public Object personList(@Param("..") Person person , HttpServletRequest req) {
		try {

			Cnd cnd = Cnd.NEW();
			cnd.and("delflag","=","false");
			cnd.and("normal_id","=",person.getNormalid());
			Object obj = personService.query(cnd);
			return Result.success("system.success",obj);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	/**
	 *  个性化查寻
	 */
	@At("/person_add_update")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object personAdd(@Param("data") Person person , HttpServletRequest req) {
		try {

			person = personService.insertEntity(person);
			return Result.success("system.success",person);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 *  个性化查寻
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
	 * 新增变更业务
	 */
	@At("/person_add_all")
	@Ok("json")
	public Object personAddAll(@Param("..") Person person,HttpServletRequest req) {
		try {
			Cnd cnd = Cnd.NEW();
			cnd.and("deviceid","=",person.getDeviceid())
					.and("normalid","=",person.getNormalid());
			List<Person> result = personService.dao().queryByJoin(Person.class,"^normal|device|grades$",cnd);
			if(Lang.isNotEmpty(result)){
                for (Person p:result) {
                    for (PersonGrade grade:p.getGrades()) {
                        personService.dao().fetchLinks(grade,"^rulers$");
                    }
                }
                return Result.success("system.success",result);

			}else{
			    cnd = Cnd.NEW();
			    cnd.and("normalid","=",person.getNormalid());
			    List<Grade> grades = personService.dao().queryByJoin(Grade.class,"^rulers$",cnd);
			    List<PersonGrade> personGrades = new ArrayList<PersonGrade>();
                for (Grade grade:grades
                     ) {
                    PersonGrade personGrade = new PersonGrade();
                    personGrade.setGrade(grade.getGrade());
                    personGrade.setCnName(grade.getCnName());
                    personGrade.setEnName(grade.getEnName());
                    List<PersonRuler> personRulers = new ArrayList<PersonRuler>();
                    for(Ruler ruler:grade.getRulers()){
                    	PersonRuler personRuler = new PersonRuler();
                    	personRuler.setNormalid(ruler.getNormalid());
                    	personRuler.setLogic(ruler.getLogic());
                    	personRuler.setVal(ruler.getVal());
                    	personRuler.setSymble(ruler.getSymble());
                    	personRuler.setOrderNum(ruler.getOrderNum());

                    	personRulers.add(personRuler);

                    }
                    personGrade.setRulers(personRulers);

                    personGrades.add(personGrade);

                }

			    person.setGrades(personGrades);
                result = new ArrayList<Person>();

                result.add(person);

            }

			return Result.success("system.success",result);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	@At("/person_grades")
	@Ok("json")
	public Object personGrades(@Param("..") PersonGrade grade, HttpServletRequest req) {
		Object obj = null;

		Cnd cnd = Cnd.NEW();
		if(Strings.isBlank(grade.getPersonid())){
			return Result.success("system.success",null);
		}
		cnd.and("personid","=",grade.getPersonid());
		if(Strings.isNotBlank(grade.getGrade())){
			cnd.and("grade","=",grade.getGrade());
		}
		obj =  personGradeService.queryPersonGrade(cnd);
		return  Result.success("system.success",   obj );
	}
	/**
	 *  个性化查寻
	 */
	@At("/person_grade_add")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object personGradeAdd(@Param("data") PersonGrade personGrade , HttpServletRequest req) {
		try {

			personGrade = personGradeService.insertPersonGrade(personGrade);
			return Result.success("system.success",personGrade);
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