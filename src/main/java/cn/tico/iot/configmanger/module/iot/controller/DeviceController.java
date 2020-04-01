package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.common.utils.excel.ImportExcel;
import cn.tico.iot.configmanger.module.iot.bean.GitBean;
import cn.tico.iot.configmanger.module.iot.graphql.GitBlock;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.*;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import cn.tico.iot.configmanger.module.iot.services.*;
import cn.tico.iot.configmanger.module.mao.services.ExcelDeviceService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.integration.json4excel.J4E;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.UploadAdaptor;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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
	private DriverService driverService;

	@Inject
	private GatewayService gatewayService;
	@Inject
    private SubGatewayService subGatewayService;


	@Inject
	private PersonService personService;

	@Inject
	private PersonGradeService personGradeService;

	@Inject
	private PersonRulerService personRulerService;


	@Inject
	private GitBlock  gitBlock;


	@Inject
	private TagService tagService;

	@Inject
	private ExcelDeviceService excelDeviceService;

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
			, @Param("cnName") String name
			, @Param("orderByColumn") String orderByColumn
			, @Param("isAsc") String isAsc
			, @Param("deptid") String deptid
			, @Param("locationid") String locationid,
			HttpServletRequest req) {

		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)) {
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
		cnd.and("status", "=", "true");
		cnd.and("asset_status", "=", "2");

		Object obj = deviceService.tableList(pageNum, pageSize, cnd, orderByColumn, isAsc, "^dept|kind|location|driver|gateway|tags$");

		return Result.success("system.success",obj);

	}
	/**
	 * 用户权限
	 * @return
	 */
	private boolean isAdmin() {

		User user = ShiroUtils.getSysUser();
		if(Lang.isEmpty(user)){
			return false;
		}

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
			obj  = deviceService.fetchLinks(obj,"^dept|kind|location|driver|gateway|tags$");

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
			device = deviceService.fetch(device.getId());
			int  i = deviceService.vDelete(device.getId());

            String extsno = getExtsno(device);
            subGatewayService.kafka(extsno);
			deviceService.kafka(Arrays.asList(device));
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
	@At("/device_upload")
	@POST
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public Object deviceUpload(@Param("devices") File f, HttpServletRequest req) {
		try {
			User user =	ShiroUtils.getSysUser();
			String deptid = user.getDeptId();
			InputStream is = Files.findFileAsStream(f.getAbsolutePath());
			List<Device> deviceList =  J4E.fromExcel(is, Device.class, null);
			List <Device> result  = deviceList.stream().map(
					dev ->{
					NutMap map = excelDeviceService.mapById(dev);
					map = excelDeviceService.cnNamesChanges(map);
					NutMap d = Lang.obj2nutmap(dev);
					d.putAll(map);
					return d;

			}).map(
					map -> Mapl.maplistToT(map,Device.class)

			).collect(Collectors.toList());
			//新增项目
			result.stream().filter(device -> Strings.isNotBlank(device.getSno()))
					.filter(device -> Strings.isBlank(device.getId()))
					.map(device -> {
						device.setAssetStatus("2");
						device.setAlertStatus("normal");
						if(!isAdmin()){
							device.setDeptid(deptid);
						}
						return device;
					})
					.forEach(device -> deviceService.insertUpdate(device));
			//修改
			result.stream().filter(device -> Strings.isNotBlank(device.getSno()))
					.filter(device -> Strings.isNotBlank(device.getId()))
					.forEach(
							device -> deviceService.insertUpdate(device)
					);



			return Result.success("system.success",result);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 *
	 */
	@At("/device_check")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Object deviceChecked(@Param("data") Device device, HttpServletRequest req) {
		try {
			if(Lang.isEmpty(device.getKindid())||device.getKindid().length()==0){
				return Result.error(9,"not choose kind ");
			}
			Cnd cnd = Cnd.where("kind_id","=",device.getKindid());

			List<Driver> drivers = driverService.query(cnd);

			if(Lang.isEmpty(drivers)){
				return Result.error(10,"not find driver");
			}
			cnd = Cnd.NEW();

			List<Gateway> gateways = gatewayService.query(cnd);

			if(Lang.isEmpty(gateways)){
				return Result.error(11,"not find gate way ");
			}

			return deviceInsertUpdate(device,req);
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
			person = personService.insertEntity(person,"^grades$");
			return Result.success("system.success",person);
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
	public Object personAdd(@Param("id") String id , HttpServletRequest req) {
		try {

			int index = personService.vDelete(id);
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
	public Object personRulerChange(@Param("insert") PersonRuler[] addruler, @Param("update") PersonRuler[] editruler, @Param("normalid") String driverid, HttpServletRequest req) {
		try {
			List obj1 = personRulerService.insertEntitys(Arrays.asList(addruler),driverid);

			int obj2 = personRulerService.updateEntitys(Arrays.asList(editruler));
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
		obj =  personGradeService.queryEntity(cnd);
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
			personGrade = personGradeService.insertEntity(personGrade);
			return Result.success("system.success",personGrade);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	/**
	 *  个性化查寻
	 */
	@At("/person_grade_remove")
	@POST
	@Ok("json")
	public Object personGradeRemove(@Param("id") String id , HttpServletRequest req) {
		try {
			int del = personGradeService.deleteEntity(id);
			return Result.success("system.success",del);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}


	/**
	 *  个性化查寻
	 */
	@At("/person_grade_add_all")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object personGradeAddAll(@Param("data") PersonGrade[] grades , HttpServletRequest req) {
		try {
			List<PersonGrade> result = personGradeService.saveEntitys(grades);
			return Result.success("system.success",result);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	@At("/over")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object over(@Param("data")Device device ,HttpServletRequest req ){
	    //先取出原始的EXTSNO
        String oldextsno = getExtsno(device);


		deviceService.insertUpdate(device);
		device = deviceService._fetch(device);
        deviceService.kafka(Arrays.asList(device));

        //再取出新的EXTSNO
        String extsno = getExtsno(device);

        if(Strings.isNotBlank(extsno)){
			subGatewayService.kafka(extsno);
		}
		if(Strings.isBlank(oldextsno)){

		}else if(!Strings.equals(extsno,oldextsno)){
			subGatewayService.kafka(extsno);
		}

		return  Result.success("system.success",device);
	}



    public String getExtsno(@Param("data") Device device) {
        if(Lang.isEmpty(device)){
        	return null;
		}
		if(Strings.isBlank(device.getId())){
            return null;
        }
        Device dev = deviceService.fetch(device.getId()) ;
        if(Lang.isEmpty(dev)){
        	return null;
		}
        if (Strings.isBlank(dev.getGatewayid())){
            return null;
        }
        Gateway gateway = gatewayService.fetch(dev.getGatewayid());
        if(Lang.isEmpty(gateway)){
            return null;
        }
        if(Strings.isBlank(gateway.getSubid())){
        	return null;
		}
        SubGateway sub = subGatewayService.fetch(gateway.getSubid());
        if(Lang.isEmpty(sub)){
            return null;
        }

        return sub.getExtSno();
    }



	@At("/drivers_update")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object updataDrivers(@Param("data")List<String> deviceids
			,@Param("driverid") String driverid
			,HttpServletRequest req ){


		Cnd cnd = Cnd.NEW();

		cnd.and("id","in",deviceids	);

		List<Device> devices = deviceService.query(cnd);
		List<Device> result = Lists.newArrayList();
		for (int i = 0; i < deviceids.size(); i++) {
			Device device = devices.get(i);
			device.setDriverid(driverid);
			device = deviceService.extAttr(device);
			result.add(device);
		}
		deviceService.update(result);




		deviceService.kafka(result);

		Set<String> extsnos = new HashSet<String>();
		for (Device device:result){
            //changGit(device);
            String extsno = getExtsno(device);
            extsnos.add(extsno);
        }

        for (String extsno : extsnos){
			subGatewayService.kafka(extsno);
        }

		return  Result.success("system.success",result);
	}



	@At("/drivers_tags")
	@POST
	@Ok("json")
	@AdaptBy(type = JsonAdaptor.class)
	public Object updataTags(@Param("data")List<String> deviceids
			,@Param("tagid") String tagid
			,HttpServletRequest req ){


		Cnd cnd = Cnd.NEW();

		cnd.and("id","in",deviceids	);


		List<Device> devices = deviceService.query(cnd);

		Tag tag = tagService.fetch(tagid);



		List<Device> result = Lists.newArrayList();
		for (int i = 0; i < deviceids.size(); i++) {
			Device device = devices.get(i);
			device = deviceService.extAttr(device);
			if(Lang.isEmpty(device.getTags())){
				device.setTags(new ArrayList<Tag>());
			}
			device.getTags().add(tag);

			result.add(device);
			deviceService.dao().insertRelation(device, "tags");

		}
		deviceService.update(result);

		deviceService.kafka(result);


		return  Result.success("system.success",result);
	}
}
