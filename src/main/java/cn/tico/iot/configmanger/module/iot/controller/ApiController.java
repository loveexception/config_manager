package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.adaptor.GraphQLAdaptor;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.page.TableDataInfo;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.graphql.ApiService;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.TopoService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.Dict;
import cn.tico.iot.configmanger.module.sys.services.DictService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.kafka.common.cache.LRUCache;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CrossOriginFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务 信息操作处理
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean
@At("/api/")
public class ApiController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private UserService userService;

	@Inject
	private DeviceService deviceService;

	@Inject
	private ApiService apiService;
	@Inject
	private KafkaBlock kafkaBlock;

	@Inject
	public 	DictService dictService;

	@Inject
	public TopoService topuService;

	@Inject
	private Dao dao;


	public static LRUCache<String ,Object> LRU = new LRUCache<>(50);

	static GraphQLSchema schema = null;

	public GraphQLSchema init(){
		if(schema!=null){
			return schema;
		}
		schema =new GraphQLSchemaGenerator()
				.withBasePackages("io.leangen")
				.withOperationsFromSingleton(apiService) //register the service
				.generate();
		return schema;
	}
	/**
	 * 查询业务列表
	 */
	@At("/device")
	@Ok("json")
	public Object device(
			@Param("sno") String sno,
			HttpServletRequest req
	) {
		Object json = LRU.get(sno);
		if(Lang.isNotEmpty(json)){
			return json;
		}
		Segment seg = new CharSegment(this.GRAPH_DEVICE);
		seg.set("sno",sno);
		String sql =seg.toString();
		Object obj  =  graphql( sql,req ) ;
		LRU.put(sno, obj);
		return obj;

	}
	/**
	 * 查询业务列表
	 */
	@At("/devices")
	@Ok("json")
	public Object deviceList(
			@Param("sno") String[] sno,
			HttpServletRequest req) {


		return Result.success("system.success",sno);

	}
	/**
	 * 查询业务列表
	 */
	@At("/gateway")
	@Ok("json")
	public Object gateway(
			@Param("extsno") String extsno,
			HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		cnd.and("ext_sno","=",extsno);
		List<SubGateway> list = this.dao.queryByJoin(SubGateway.class , "^gateway$",cnd);
		return  Result.success("system.success",list);
	}

	@At("/gatewayGraphql")
	@Ok("json")
	public Object gatewayGraphql(
			@Param("..") String sql,
			HttpServletRequest req) {
		init();

		GraphQL graphQL = new GraphQL.Builder(schema).build();

		ExecutionResult result = graphQL.execute(sql);

		return result;
	}
	/**
	 * 查询业务列表
	 */
	@At("/graphql")
	@Ok("json")
	@POST
	@AdaptBy(type = GraphQLAdaptor.class)
	public Object graphql(@Param("..")String sql ,
			HttpServletRequest req) {
		init();

		GraphQL graphQL = new GraphQL.Builder(schema).build();

        ExecutionResult result = graphQL.execute(sql);

		return result;

	}



	/**
	 * 查询业务列表
	 */
	@At("/all_sno")
	@Ok("json")
	public Object allSNO(
			@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize

			,HttpServletRequest req)  {
		Cnd cnd = Cnd.NEW();
		Pager pager = new Pager(pageNum,pageSize);

		List<Device> devices = deviceService.query(cnd,pager);

		return Result.success("system.success",devices);
	}

	@At("/kafka")
	@Ok("json")
	public Object kafka(@Param("sno") String sno,@Param("extsno") String extsno, HttpServletRequest req) {
		if(Strings.isNotBlank(sno)){
			kafkaBlock.produce("config","sno",sno);
			return  Result.success("system.success",sno );
		}
		kafkaBlock.produce("config","extsno",extsno);
		return  Result.success("system.success",extsno );

	}
	@At("/dict")
	@Ok("json")
    @Filters(@By(type=CrossOriginFilter.class))

    public Object api(@Param("type")String type,HttpServletRequest req){
		List<Dict> list =  dictService.query(Cnd.where("type","=",type));

		return Result.success("system.success", list);
	}

	@Filters(@By(type=CrossOriginFilter.class))
	@At("/init_snos")
	@Ok("json")
	public Object initSnos(
			@Param("pageNum")int pageNum
			, @Param("pageSize")int pageSize

			, @Param("name") String name
			, @Param("orderByColumn") String orderByColumn
			, @Param("isAsc") String isAsc


			, @Param("deptid") String deptid
			, @Param("locationid") String locationid,
									   HttpServletRequest req) {

		Cnd cnd = Cnd.NEW();


		cnd.and("delflag", "=", "false");
		cnd.and("status", "=", "true");
		cnd.and("asset_status", "=", "2");

		if(Strings.isNotBlank(deptid)){
			cnd.and("dept_id","=",deptid);

		}
		if(Strings.isNotBlank(locationid)){
			cnd.and("location.location_id","=",locationid);
		}


		TableDataInfo devices = deviceService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,"location");

		List<String> obj = devices.getRows().stream().map(device -> ((Device)device).getSno()).collect(Collectors.toList());
		devices.setRows(obj);

		return Result.success("system.success", devices);
	}
	@At("/topu")
	@Ok("json")
	@Filters({@By(type=CrossOriginFilter.class, args={"*", "GET, POST, PUT, DELETE, OPTIONS, PATCH", "Origin, Content-Type, Accept, X-Requested-With", "true"})})
    public Object topu(@Param("tag")String tag ,HttpServletRequest req){
		return topuService.drawByTag(tag);
	}

	static final String GRAPH_DEVICE=
			"query{device(sno:\"${sno}\")  {id,sno,order_time,quality,discard_time,asset_status,alert_status,i18n,cn_name,en_name,price,gateway{id,i18n,cn_name,en_name,env,sno,git_path,desription,subgateway{id,ext_sno,sno,ext_ip},i18n,env,dept{id,dept_name,order_num,leader,phone,email},tags{i18n,cn_name,en_name},kind{i18n,cn_name,en_name},location{i18n,cn_name,en_name}},env,tags{id,i18n,cn_name,en_name,dept{id,dept_name}},kinds{id,i18n,cn_name,en_name,level,order_num},locations{id,i18n,cn_name,en_name,level,order_num},dept{id,dept_name,order_num,leader,phone,email},driver{id,i18n,cn_name,en_name,normals{id,i18n,cn_name,en_name,operate_key,unit,order_num,status,person(sno:\"${sno}\"){id,i18n,cn_name,en_name,status,grades{id,i18n,cn_name,en_name,grade,order_num,rulers{id,i18n,logic,val,symble,order_num,normal{id,i18n,cn_name,en_name,unit,operate_key}}}},grades{id,i18n,cn_name,en_name,grade,order_num,rulers{id,i18n,logic,val,symble,order_num,normal{id,i18n,cn_name,en_name,unit,operate_key}}}}}}}";
		//"query{device(sno:\"${sno}\")  {id,sno,order_time,quality,discard_time,asset_status,alert_status,i18n,cn_name,en_name,price,gateway{id,i18n,cn_name,en_name,env,sno,git_path,desription,subgateway{id,ext_sno,sno,ext_ip},i18n,env,dept{id,dept_name,order_num,leader,phone,email},tags{i18n,cn_name,en_name},kind{i18n,cn_name,en_name},location{i18n,cn_name,en_name}},env,tags{id,i18n,cn_name,en_name,dept{id,dept_name}},kinds{id,i18n,cn_name,en_name,level,order_num},locations{id,i18n,cn_name,en_name,level,order_num},dept{id,dept_name,order_num,leader,phone,email},driver{id,i18n,cn_name,en_name,normals{id,i18n,cn_name,en_name,operate_key,unit,order_num,status,person(sno:\"${sno}\"){id,i18n,cn_name,en_name,status,grades{id,i18n,cn_name,en_name,grade,order_num,rulers{id,i18n,logic,val,symble,order_num,normal{id,i18n,cn_name,en_name,unit,operate_key}}}},grades{id,i18n,cn_name,en_name,grade,order_num,rulers{id,i18n,logic,val,symble,order_num,normal{id,i18n,cn_name,en_name,unit,operate_key}}}}}}}";
	}
