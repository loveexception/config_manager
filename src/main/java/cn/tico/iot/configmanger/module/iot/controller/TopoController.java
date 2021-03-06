package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.module.iot.models.Topo.Base;
import cn.tico.iot.configmanger.module.iot.models.Topo.Topo;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import cn.tico.iot.configmanger.module.iot.services.TagService;
import cn.tico.iot.configmanger.module.iot.services.TopoService;
import cn.tico.iot.configmanger.module.sys.services.DictService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import cn.tico.iot.configmanger.module.wx.services.TopoBasesService;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.cri.SqlExpressionGroup;
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

	@Inject
    public TopoBasesService topoBasesService;

	//
	public static final String KEYS="adcode,dept_id,location,location_id,login_name,Referer,token,user_id";





	@At("/add_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object addTag(@Param("..")Tag tag,@Param("baseId")String baseId ,
						 @Param("order_num")long orderNum,
						 HttpServletRequest req){
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

		Topo topo = tagService.createTopo(tag, baseId,orderNum);
		topoService.insert(topo);
		tag = topoService.initTag(tag, baseId);
		topo.setTag(tag);

		return Result.success("system.success",topo);
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
		tag = tagService.fetch(tag.getId());
		tag.setDelFlag("true");
		int delete = tagService.dao().update(tag);
		deviceService.kafka(tag);
		return Result.success("system.success",delete);
	}

	@At("/check_tag")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*"
			, "GET, POST, PUT, DELETE, OPTIONS, PATCH"
			, "Origin, Content-Type, Accept, X-Requested-With"
			+ KEYS
			, "true"})})
	public Object checkTag(
			@Param("..")Tag tag
			,@Param("check")String check
			,@Param("order_num")long ordernum
			, HttpServletRequest req){
//		Cnd cnd = Cnd.NEW();
//		cnd.and("tag_id","=",tag.getId());
//		List<Topo> topos = topoService.dao().queryByJoin(Topo.class,"tag",cnd);
//		if(Lang.isEmpty(topos)){
//			return Result.error("not have one topo in tag");
//		}
		Topo topo = topoService.getToPoByTagId(tag.getId());

		if(Strings.isNotBlank(check)){
			topo.setIsCheck(check);
		}
		if(ordernum>0){
			topo.setOrderNum(ordernum);
		}
		topoService.updateEntity(topo);
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

		//deviceService.kafka(Lists.newArrayList(device));

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
	public Object listTag(
			@Param("deptid")String deptId
			, @Param("check")String check
			, @Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize
			, @Param("name") String name
			, @Param("orderByColumn") String orderByColumn
			, @Param("isAsc") String isAsc
			, HttpServletRequest req){

		Cnd cnd = Cnd.NEW().and("tag.dept_id","=",deptId);
		if(Strings.isNotBlank(check)){
			cnd.and("t_topo_graphs.is_check","=",check);
		}
		if(Strings.isNotBlank(name)){
			cnd.and("tag.cn_name","like","%"+name+"%");
		}
		if (!Strings.isBlank(name)) {
			SqlExpressionGroup group = Cnd.exps("tag.cn_name", "like", "%" + name + "%")
					.or("tag.en_name", "like", "%" + name + "%");
			cnd.and(group);
		}
		if(Strings.isNotBlank(orderByColumn)) {
			cnd.orderBy(orderByColumn, isAsc);
		}else if(Strings.equalsIgnoreCase("cnName",orderByColumn)){
			cnd.orderBy("CONVERT(cn_name using gbk)","asc");
		}else {
			cnd.orderBy("t_topo_graphs.order_num","desc");
		}
		List<Topo> topos = tagService.dao().queryByJoin(Topo.class,"tag",cnd);
		List<String> tagids =topos.stream().map(topo -> topo.getTagId()).collect(Collectors.toList());
		cnd = Cnd.NEW();
		cnd.and("id","in",tagids);
		cnd.and("status","=","true");
		cnd.and("delflag","=","false");
//		if(Strings.isNotBlank(orderByColumn)) {
//			cnd.orderBy(orderByColumn, isAsc);
//		}else if(Strings.equalsIgnoreCase("cnName",orderByColumn)){
//			cnd.orderBy("CONVERT(cn_name using gbk)","Asc");
//		}else {
//			cnd.orderBy("t_topo_graphs.order_num","Asc");
//		}

		if(pageSize==0){
			pageSize = 200;
		}
		Pager pager = new Pager(pageNum,pageSize);



		List<Tag> tags = tagService.dao().query(Tag.class,cnd,pager);
		List<Topo> result = Lists.newArrayList();
		for (Topo topo:topos) {
			for (Tag tag:tags) {
				if(Strings.equals(topo.getTagId(),tag.getId())){
					topo.setTag(tag);
				}

			}
			result.add(topo);
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
			,@Param("kindid") String kindid
			,@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize
			,HttpServletRequest req){

		tag = tagService.fetchLinks(tag,"devices");
		List<Device> devices = tag.devices;
		if(Lang.isEmpty(devices)){
			return Result.success("system.success",Lists.newArrayList());
		}
		Topo topo = topoService.getToPoByTagId(tag.getId());
		if(Lang.isEmpty(topo)){
			return Result.error("not have the topo in dept");
		}
		Cnd cnd = Cnd.NEW();
//		cnd.and("id","in",showdevids);
//		cnd.and("id","not in",hidedevids);
		cnd.and("status","=","true");
		cnd.and("delflag","=","false");
		cnd.and("asset_status","=","2");
		cnd.and("dept_id","=",deptId);
		List<Device> hidedev = topoBasesService.getHideDevids(topo.getBaseId());

		//List<String> hidedevids = Lists.newArrayList();

		if(Lang.isNotEmpty(hidedev)){
			List<String> hidedevids = hidedev.stream().map(device -> device.getId()).collect(Collectors.toList());
			if(Lang.isNotEmpty(hidedevids)){
				cnd.and("id","not in",hidedevids);
			}
		}


		if(Lang.isNotEmpty(devices)){
			List<String> showdevids = devices.stream().map(device -> device.getId()).collect(Collectors.toList());
			if(Lang.isNotEmpty(showdevids)){
				cnd.and("id","in",showdevids);

			}
		}

		List<String> kindids = kindService.kindAllSonIds(kindid);
		if(Lang.isNotEmpty(kindids)){
			cnd.and("kind_id","in",kindids);
		}
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


		Topo topo = topoService.getToPoByTagId(tag.getId());
		if(Lang.isEmpty(topo)){
			return  Result.error("there hasn't  the  topo  ");
		}

		List<Device> showdev = topoBasesService.getShowDevids(topo.getBaseId());

		List<String> showdevids = Lists.newArrayList();

		if(Lang.isNotEmpty(showdev)){
			showdevids = showdev.stream().map(device -> device.getId()).collect(Collectors.toList());
		}




		Cnd cnd = Cnd.NEW()
		.and("status","=","true")
		.and("delflag","=","false")
		.and("asset_status","=","2")
		.and("dept_id","=",deptId);

		List<String> kindids = Lists.newArrayList();
		if(Strings.isNotBlank(kindid)){
			kindids = kindService.kindAllSonIds(kindid);
		}
		if(Lang.isNotEmpty(kindids)){
			cnd.and("kind_id","in",kindids);
		}
		if(Lang.isNotEmpty(showdevids)){
			cnd.and("id","in",showdevids);
		}

		tag = tagService.fetchLinks(tag,"devices");

		List<Device> devices = tag.devices;

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
	public Object fullTopo(@Param("baseId") String id , HttpServletRequest req){
		Object result =  topoService.drawByAll(id);

		return Result.success("system.success",result);


	}

    @At("/dept_topo_base")
    @Ok("json")
    @Filters({@By(type=CrossOriginFilter.class, args={"*"
            , "GET, POST, PUT, DELETE, OPTIONS, PATCH"
            , "Origin, Content-Type, Accept, X-Requested-With"
            + KEYS
            , "true"})})
    public Object detpTopoBase(@Param("deptId") String deptid ,
							   HttpServletRequest req){
	    Cnd cnd = Cnd.NEW();
	    cnd.and("status","=","true");
	    cnd.and("delflag","=","false");
	    cnd.and("dept_id","=",deptid);
	    List<Base> topos = topoBasesService.query(cnd);
        return Result.success("system.success",topos);
    }

    @Filters({@By(type=CrossOriginFilter.class, args={"*"
            , "GET, POST, PUT, DELETE, OPTIONS, PATCH"
            , "Origin, Content-Type, Accept, X-Requested-With"
            + KEYS
            , "true"})})
    @At(value = "/save_topo",methods = "OPTIONS")
    @GET
    public void saveOPTIONS( ){ }
    @Filters({@By(type=CrossOriginFilter.class, args={"*"
            , "GET, POST, PUT, DELETE, OPTIONS, PATCH"
            , "Origin, Content-Type, Accept, X-Requested-With"
            + KEYS
            , "true"})})
	@At("/save_topo")
	@Ok("json")
    @POST
	@AdaptBy(type = JsonAdaptor.class)
	public Object saveTopo(@Param("data")Topo topo  , HttpServletRequest req){
		if(Lang.isEmpty(topo)){
			return Result.error("not have tag");
		}
		if(Strings.isEmpty(topo.getTagId())){
			return Result.error("not have tag id ");
		}
		List<Topo> topos =  topoService.query(Cnd.NEW().and("tag_id","=",topo.getTagId()));
		Tag tag = tagService.fetch(topo.getTagId());
		String graph = topo.getGraph();
		String hidemap=topo.getHideMap();
		if(Lang.isEmpty(topos)){
			String baseId = topo.getBaseId();

			topo = tagService.createTopo(tag,baseId,0);
			tag = topoService.initTag(tag,baseId);
			topo.setTag(tag);

			topo.setGraph(graph);
			topo.setHideMap(hidemap);
			topoService.insert(topo);
            return Result.success("system.success",topo);

        }
        Iterator<Topo> iterator = topos.iterator();
		topo = iterator.next();
        while (iterator.hasNext()){
            Topo temp =  iterator.next();
            topoService.delete(temp.getId());
        }

        topo.setGraph(graph);
        topo.setHideMap(hidemap);

		topoService.update(topo);
		deviceService.kafka(tag);
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

		return topoService.getToPoByTagId(tag.getId());
	}

}
