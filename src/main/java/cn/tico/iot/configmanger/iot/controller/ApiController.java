package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.adaptor.GraphQLAdaptor;
import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.graphql.ApiService;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import cn.tico.iot.configmanger.iot.services.DeviceService;
import cn.tico.iot.configmanger.iot.services.PersonGradeService;
import cn.tico.iot.configmanger.iot.services.PersonRulerService;
import cn.tico.iot.configmanger.iot.services.PersonService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.adaptor.QueryStringAdaptor;
import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private ApiService apiService;

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
	public Object deviceList(
			@Param("sno") String sno,
			HttpServletRequest req) {
		Segment seg = new CharSegment(this.GRAPH_DEVICE);
		seg.set("sno",sno);
		String sql =seg.toString();

		return Result.success("system.success",graphql( sql,req ) );

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

		System.out.println(Json.toJson(sql));

		System.out.println(Json.toJson(result));

		return Result.success("system.success",result);

	}
	/**
	 * 查询业务列表
	 */
	@At("/all_sno")
	@Ok("json")
	public Object allSNO(HttpServletRequest req) throws IOException, ServletException {

		String url = req.getRequestURL().toString();
		Map map = req.getParameterMap();
		url += "?";

	    url += req.getQueryString();

		System.out.println(url);

		String[] md5 =url.split("&md5");

		System.out.println(md5[0]);

		return Result.success("system.success",null);
	}

	static final String GRAPH_DEVICE="query{device(sno:\"${sno}\"){id,sno,order_time,quality,discard_time,asset_status,alert_status,i18n,price,gateway{id,cn_name,env,sno,ext_ip,git_path,desription,subgateway{id,ext_sno,sno},i18n,env,dept{id,dept_name,order_num,leader,phone,email},tags{i18n},kind{i18n},location{i18n}},env,tags{id,i18n,dept{id,dept_name}},kinds{id,i18n,level,order_num},locations{id,i18n,level,order_num},dept{id,dept_name,order_num,leader,phone,email},persons{id,i18n},driver{id,i18n,path,normals{id,i18n,operate_key,unit,order_num,grades{id,i18n,grade,order_num,rules{id,i18n,logic,normal_id,normal{id,i18n,operate_key,unit},symble,val}}}}}}";
}
