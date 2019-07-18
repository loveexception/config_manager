package cn.tico.iot.configmanger.iot.controller;

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
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.adaptor.QueryStringAdaptor;
import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
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



	/**
	 * 查询业务列表
	 */
	@At("/device")
	@Ok("json")
	public Object deviceList(
			@Param("sno") String sno,
			HttpServletRequest req) {

		return Result.success("system.success",sno);

	}
	/**
	 * 查询业务列表
	 */
	@At("/graphql")
	@Ok("json")
	@POST
	@AdaptBy(type = VoidAdaptor.class)
	public Object graphql(

			HttpServletRequest req) {
		String sql = "";
		try {
			InputStream is = req.getInputStream();

			byte[] bb = Streams.readBytesAndClose(is);
			sql = new String (bb);
		} catch (IOException e) {
			e.printStackTrace();
		}
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages("io.leangen")
                .withOperationsFromSingleton(apiService) //register the service
                .generate(); //done ;)
        GraphQL graphQL = new GraphQL.Builder(schema).build();
        ExecutionResult result = graphQL.execute(sql);
        System.out.println(Json.toJson(result));

		return Result.success("system.success",result);

	}

}
