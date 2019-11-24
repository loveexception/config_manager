package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.Topo.Topo;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import cn.tico.iot.configmanger.module.iot.services.TagService;
import cn.tico.iot.configmanger.module.iot.services.TopoService;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DictService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CrossOriginFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 业务 信息操作处理
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean
@At("/topo/")
public class TopoController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private UserService userService;

	@Inject
	private DeviceService deviceService;

	@Inject
	private TagService tagService;


	@Inject
	public 	DictService dictService;

	@Inject
	public TopoService topoService;

	@Inject
	public KindService kindService;

	//
	public static final String KEYS="adcode,dept_id,location,location_id,login_name,Referer,token,user_id";





	@At("/add_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object addTag(@Param("..")Tag tag , HttpServletRequest req){
		if(Lang.isEmpty(tag)){
			return Result.error("system.error");
		}
		if(Strings.isEmpty(tag.getCnName())){
			return Result.error("system.error");

		}
		if(Strings.isEmpty(tag.getDeptid())){
			return Result.error("system.error");
		}



		tag.setDeptid(tag.getDeptid());

		tag.setCreateBy("API");
		tag.setCreateTime(new Date());
		tagService.dao().insert(tag);
		return makeTopo(tag);
	}
	@At("/del_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object removeTag(@Param("..")Tag tag , HttpServletRequest req){
		tagService.dao().clearLinks(tag,"devices");
		tagService.dao().clear(Topo.class,Cnd.NEW().and("tag_id","=",tag.getId()));

		return Result.success("system.success");
	}

	private Object makeTopo(Tag tag) {
		Topo topo = new Topo();
		topo.setIsCheck("false");
		topo.setTagId(tag.getId());
		topo.setCnName(tag.getCnName()+"拓扑图");
		topo.setStatus("true");
		topo.setDelFlag("false");
		topo.setCreateTime(new Date());
		topo.setCreateBy("API");
		topo = tagService.dao().insert(topo);
		topo.setTag(tag);
		return Result.success("system.success",topo);
	}

	@At("/check_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object checkTag(@Param("..")Tag tag,@Param("check")String check , HttpServletRequest req){
		Cnd cnd = Cnd.NEW();
		cnd.and("tag_id","=",tag.getId());

		List<Topo> topos = topoService.dao().queryByJoin(Topo.class,"tag",cnd);

		if(Lang.isEmpty(topos)){
			return Result.error("not have one topo in tag");
		}
		Topo topo = topos.get(0);
		topo.setIsCheck(check);

		topoService.update(topo);


		return Result.success("system.success",topo);
	}
	@At("/push_device_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object pushTag(@Param("..")Tag tag ,@Param("deviceid") String deviceId , HttpServletRequest req){


		Device device = deviceService.fetch(deviceId);
		if(Lang.isEmpty(device)){
			return Result.error("system.error");
		}



		device = deviceService.fetchLinks(device,"tags");


		tag = tagService.fetch(tag.getId());

		if(Lang.isEmpty(device)){
			return Result.error("system.error");
		}
		if(Lang.isEmpty(tag)){
			return Result.error("system.error");
		}


		device = deviceService.extAttr(device);
		if(Lang.isEmpty(device.getTags())){
			device.setTags(new ArrayList<Tag>());
			device.getTags().add(tag);

		}else{
			Set<Tag> set = new TreeSet<Tag>();
			set.addAll(device.getTags());
			set.add(tag);
			device.getTags().clear();;
			device.getTags().addAll(set);
		}

		deviceService.dao().insertRelation(device, "tags");

		deviceService.update(device);

		deviceService.kafka(Lists.newArrayList(device));

		return Result.success("system.success",device);
	}

	@At("/pop_device_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object popTag(@Param("..")Tag tag ,@Param("deviceid") String deviceId , HttpServletRequest req){


		Device device = deviceService.fetch(deviceId);
		device = deviceService.fetchLinks(device,"tags");


		tag = tagService.fetch(tag.getId());

		if(Lang.isEmpty(device)){
			return Result.error("system.error");
		}
		if(Lang.isEmpty(tag)){
			return Result.error("system.error");
		}


		device = deviceService.extAttr(device);
		if(Lang.isEmpty(device.getTags())){
			device.setTags(new ArrayList<Tag>());
		}
		List<Tag> tags = device.getTags();
		String tagid = tag.getId();
		List<Tag> result= tags.stream().filter(temp->!Strings.equals(tagid,temp.getId())).collect(Collectors.toList());




		deviceService.dao().clearLinks(device,"tags");

		device.setTags(result);

		deviceService.dao().insertRelation(device, "tags");

		deviceService.update(device);

		deviceService.kafka(Lists.newArrayList(device));


		return Result.success("system.success",device);
	}

	@At("/list_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object listTag(@Param("deptid")String deptId,
			@Param("check")String check
			, HttpServletRequest req){


		Cnd cnd = Cnd.NEW().and("dept_id","=",deptId);
		if(Strings.isNotBlank(check)){
			cnd.and("is_check","=",check);
		}
		List<Topo> topos = tagService.dao().queryByJoin(Topo.class,"tag",cnd);
		List<String> tagids =topos.stream().map(topo -> topo.getTagId()).collect(Collectors.toList());
		cnd = Cnd.NEW();
		cnd.and("id","in",tagids);
		cnd.and("status","=","true");
		cnd.and("delflag","=","false");

		List<Tag> tags = tagService.dao().query(Tag.class,cnd);
		List<Topo> result = Lists.newArrayList();
		for (Tag tag:tags) {
			for (Topo topo:topos) {
				if(Strings.equals(topo.getTagId(),tag.getId())){
					topo.setTag(tag);
					result.add(topo);
				}
			}
		}


		return Result.success("system.success",result);
	}


	@At("/devices_in_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object devicesTag(@Param("..")Tag tag
			,@Param("deptid")String deptId
			,@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize
			,HttpServletRequest req){

		tag = tagService.fetchLinks(tag,"devices");
		List<Device> devices = tag.devices;
		Pager pager = new Pager(
				pageNum,pageSize
		);
		if(Lang.isEmpty(devices)){
			return Result.success("system.success",Lists.newArrayList());
		}
		List<String> list = devices.stream().map(device -> device.getId()).collect(Collectors.toList());
		Cnd cnd = Cnd.NEW();
		cnd.and("id","in",list);
		cnd.and("status","=","true");
		cnd.and("delflag","=","false");
		cnd.and("asset_status","=","2");
		cnd.and("dept_id","=",deptId);
		//devices = deviceService.query(cnd,pager);
		Object obj = deviceService.tableList(pageNum,pageSize,cnd);


		return Result.success("system.success",obj);
	}
	@At("/devices_not_in_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object devicesNotInTag(@Param("..")Tag tag
			,@Param("deptid") String deptId
			,@Param("kindid") String kindid
			,@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize
			, HttpServletRequest req){

		tag = tagService.fetchLinks(tag,"devices");
		List<Device> devices = tag.devices;
		List<String> kindids = Lists.newArrayList();
		if(Strings.isNotBlank(kindid)){
			 kindids = kindService.kindAllSonIds(kindid);
		}


		Cnd cnd = Cnd.NEW()
		.and("status","=","true")
		.and("delflag","=","false")
		.and("asset_status","=","2")
		.and("dept_id","=",deptId);
		if(Lang.isNotEmpty(kindids)){
			cnd.and("kind_id","in",kindids);
		}


		if(Lang.isEmpty(devices)){

			Object obj = deviceService.tableList(pageNum,pageSize,cnd);

			return Result.success("system.success",obj);
		}
		List<String> list = devices.stream().map(device -> device.getId()).collect(Collectors.toList());
		cnd.and("id","not in",list);

		Object obj = deviceService.tableList(pageNum,pageSize,cnd);

		return Result.success("system.success",obj);
	}


	@At("/full_topo")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object fullTopo(@Param("deptid") String deptid , HttpServletRequest req){

		return topoService.drawByAll(deptid);
	}




	@At("/save_topo")
	@Ok("json")
    @POST
	@AdaptBy(type = JsonAdaptor.class)
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object saveTopo(@Param("data")Topo topo  , HttpServletRequest req){
		 topoService.update(topo);
		 return Result.success("system.success",topo);
	}


	@At("/view_topo")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object viewTopo(@Param("..")Tag tag , HttpServletRequest req){
		List<Topo> topos = topoService.query(Cnd.NEW().and("tag_id","=",tag.getId()));
		if(Lang.isEmpty(topos)){
			return Result.error("not find topo by tag");
		}

		return topos.get(0);
	}

}
