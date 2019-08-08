package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.adaptor.GraphQLAdaptor;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.module.iot.graphql.ApiService;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	private Dao dao;

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
			HttpServletRequest req) {
		Segment seg = new CharSegment(this.GRAPH_DEVICE);
		seg.set("sno",sno);
		String sql =seg.toString();
		return graphql( sql,req ) ;

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

	static final String GRAPH_DEVICE="query{device(sno:\"${sno}\")  {id,sno,order_time,quality,discard_time,asset_status,alert_status,i18n,cn_name,en_name,price,gateway{id,i18n,cn_name,en_name,env,sno,git_path,desription,subgateway{id,ext_sno,sno,ext_ip},i18n,env,dept{id,dept_name,order_num,leader,phone,email},tags{i18n,cn_name,en_name},kind{i18n,cn_name,en_name},location{i18n,cn_name,en_name}},env,tags{id,i18n,cn_name,en_name,dept{id,dept_name}},kinds{id,i18n,cn_name,en_name,level,order_num},locations{id,i18n,cn_name,en_name,level,order_num},dept{id,dept_name,order_num,leader,phone,email},driver{id,i18n,cn_name,en_name,normals{id,i18n,cn_name,en_name,operate_key,unit,order_num,status,person(sno:\"${sno}\"){id,i18n,cn_name,en_name,status,grades{id,i18n,cn_name,en_name,grade,order_num,rulers{id,i18n,logic,val,symble,order_num,normal{id,i18n,cn_name,en_nameunit}}}},grades{id,i18n,cn_name,en_name,grade,order_num,rulers{id,i18n,logic,val,symble,order_num,normal{id,i18n,cn_name,en_nameunit}}}}}}}";
	//"query{device(sno:\"${sno}\") {id,sno,order_time,quality,discard_time,asset_status,alert_status,i18n,cn_name,en_name,price,gateway{id,i18n,cn_name,en_name,env,sno,git_path,desription,subgateway{id,ext_sno,sno,ext_ip},i18n,env,dept{id,dept_name,order_num,leader,phone,email},tags{i18n,cn_name,en_name},kind{i18n,cn_name,en_name},location{i18n,cn_name,en_name}},env,tags{id,i18n,cn_name,en_name,dept{id,dept_name}},kinds{id,i18n,cn_name,en_name,level,order_num},locations{id,i18n,cn_name,en_name,level,order_num},dept{id,dept_name,order_num,leader,phone,email},persons{id,i18n,cn_name,en_name},driver{id,i18n,cn_name,en_name,path,normals{id,i18n,cn_name,en_name,operate_key,unit,order_num,grades{id,i18n,cn_name,en_name,grade,order_num,rules{id，i18n,cn_name,en_name,logic,normal_id,normal{id,i18n,cn_name,en_name,operate_key,unit},symble,val}}}}}}";
            //"query{device(sno:\"${sno}\"){id,sno,order_time,quality,discard_time,asset_status,alert_status,i18n,price,gateway{id,cn_name,env,sno,git_path,desription,subgateway{id,ext_sno,sno,ext_ip},i18n,env,dept{id,dept_name,order_num,leader,phone,email},tags{i18n},kind{i18n},location{i18n}},env,tags{id,i18n,dept{id,dept_name}},kinds{id,i18n,level,order_num},locations{id,i18n,level,order_num},dept{id,dept_name,order_num,leader,phone,email},persons{id,i18n},driver{id,i18n,path,normals{id,i18n,operate_key,unit,order_num,grades{id,i18n,grade,order_num,rules{id,i18n,logic,normal_id,normal{id,i18n,operate_key,unit},symble,val}}}}}}";
}
