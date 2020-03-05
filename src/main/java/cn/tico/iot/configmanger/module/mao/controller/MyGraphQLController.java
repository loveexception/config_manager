package cn.tico.iot.configmanger.module.mao.controller;

import cn.tico.iot.configmanger.common.adaptor.GraphQLAdaptor;
import cn.tico.iot.configmanger.module.iot.controller.AdminKey;
import cn.tico.iot.configmanger.module.mao.common.BaseController;
import cn.tico.iot.configmanger.module.mao.graphql.*;
import com.google.common.collect.Lists;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CrossOriginFilter;
import org.nutz.mvc.impl.ActionInvoker;

import javax.annotation.PostConstruct;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;


import java.util.List;
import java.util.Map;


/**
 *
 *
 * @author maodajun
 * @date 2020-02-16
 */
@At(value = "/graph/")
@IocBean(create = "init")
public class MyGraphQLController  extends BaseController {
	private static final Log log = Logs.get();


	@Inject
	private DeviceGrap deviceGrap;
	@Inject
	private DriverGrap driverGrap;
	@Inject
	private LocationGrap locationGrap;
	@Inject
	private KindGrap kindGrap;
	@Inject
	private GatewayGrap gatewayGrap;
	@Inject
	private NormalGrap normalGrap;
	@Inject
	private GradeGrap gradeGrap;
	@Inject
	private RulerGrap rulerGrap;
	@Inject
	private PersonGrap personGrap;
	@Inject
	private PersonGradeGrap personGradeGrap;
	@Inject
	private PersonRulerGrap personRulerGrap;
	@Inject
            private  DeptGrap deptGrap;





	GraphQL server;
//    String KEY_PATH  ;
//    static final String REG_STAT = ".*(";
//	static final String REG_END = ").*";
    static final String SPLIT =":";


	public GraphQL init() {
		GraphQLSchema
		schema = new GraphQLSchemaGenerator()
				.withBasePackages("io.leangen")
				.withOperationsFromSingletons(
						deviceGrap
						,driverGrap
						,locationGrap
						,kindGrap
						,gatewayGrap
						,normalGrap
						,gradeGrap
						,rulerGrap
						,personGrap
						,personGradeGrap
						,personRulerGrap
                        ,deptGrap
				).generate();
		 server = new GraphQL.Builder(schema).build();
		return server;
	}

	/**
	 * 查询业务列表
	 */
	@At
	@Ok("json")
	@POST
	@AdaptBy(type = GraphQLAdaptor.class)
    public Map<String, Object> sql(@Param("") String sql) {

        ExecutionResult executionResult = server.execute(ExecutionInput.newExecutionInput()
                .query(sql)
                .build());
        Map<String,Object> result = executionResult.toSpecification();

		return result;
	}

    @At(value = "/sql",methods = "OPTIONS")
    @GET
    public void sql( ){ }

	@At
    @Ok("json")
    @GET

    public Object device(@Param("sno")String sno){
		ActionInvoker c;
        String cache = findCache(sno);
        if (Strings.isNotBlank(cache)) {
            return Json.fromJson(cache);
        }

        String sql = buildGraphQL(sno);

	    Map map =  sql(sql);
        String json = insertCache(sno,map);
	    return Json.fromJson(json);
    }


    @Aop("redis")
    public String insertCache(String sno ,Map map) {
        String key = buildRedisKey(sno);

        String json = Json.toJson(map);

        jedis().set(key,json);
        return json;
    }

    @Aop("redis")
    public String findCache(String sno) {
        String key = buildRedisKey(sno);
        return jedis().get(key);

    }
    @Aop("redis")
    public Long killCache(String sno){
        String key = buildRedisKey(sno);
        return jedis().del(key);
    }

    public String buildGraphQL(@Param("sno") String sno) {
        CharSegment seg = new CharSegment(SQL);
        seg.set("sno",sno);
        return seg.toString();
    }

    public String buildRedisKey(@Param("sno") String sno) {

        List<String> list = Lists.newArrayList("config","sno",sno);
        return Strings.join(SPLIT,list);
    }


    /**
     * 测试数据
     */
    @Aop("redis")
    public String cache( String sql) throws InterruptedException {
        Object obj = jedis().get("sleep:"+sql);
        if(Lang.isNotEmpty(obj)){
            return "weakup";
        }
        Thread.sleep(2000);
        jedis().set("sleep:"+sql,"2000","NX","EX",3);
        return "sleep";
    }

    public static   String SQL = "{\n" +
            "  deviceBySno(sno:\"${sno}\"){\n" +
			"\tid,sno,order_time,quality,discard_time\n" +
			"\t,asset_status,alert_status\n" +
			"\t,i18n,cn_name,en_name,price,env\n" +
			"\t,gateway{\n" +
			"\t  \tid,i18n,cn_name,en_name,env\n" +
			"\t  \t,sno,git_path,desription,i18n,env\n" +
			"\t\t,subgateway{id,ext_sno,sno,ext_ip}\n" +
			"\t\t,dept{id,dept_name,order_num,leader,phone,email}\n" +
			"\t\t,tags{i18n,cn_name,en_name}\n" +
			"\t\t,kind{i18n,cn_name,en_name}\t\n" +
			"\t\t,location{i18n,cn_name,en_name}\n" +
			"\t}\n" +
			"\t,tags{id,i18n,cn_name,en_name,dept{id,dept_name} }\n" +
			"\t,dept{id,dept_name,order_num,leader,phone,email}\n" +
			"\t,kinds{id,i18n,cn_name,en_name,level,order_num}\n" +
			"\t,locations{id,i18n,cn_name,en_name,level,order_num}\n" +
			"\t,driver{\n" +
			"\t\tid,i18n,cn_name,en_name\n" +
			"\t\t,normals{\n" +
			"\t\t\tid,i18n,cn_name,en_name,operate_key,unit,order_num,status\n" +
			"\t\t}\n" +
			"\t\trulers{\n" +
			"\t\t\tid,i18n,logic,val,symble,order_num\n" +
			"\t\t\t,normal{\n" +
			"\t\t\t\tid,i18n,cn_name,en_name,unit,operate_key\n" +
			"\t\t\t}\n" +
			"\t\t\t,tie{\n" +
			"\t\t\t\tid,i18n,cn_name,en_name,unit,operate_key\n" +
			"\t\t\t}\n" +
			"\t\t\t,level{\n" +
			"\t\t\t\tid,i18n,cn_name,en_name,grade,order_num\n" +
			"\t\t\t}\n" +
			"\t\t}\n" +
			"\t\t\n" +
			"\t}\n" +
			"\tpersonRulers{\n" +
			"\t\tid,i18n,logic,val,symble,order_num\n" +
			"\t\t,normal{\n" +
			"\t\t\tid,i18n,cn_name,en_name,unit,operate_key\n" +
			"\t\t}\n" +
			"\t\t,tie{\n" +
			"\t\t\tid,i18n,cn_name,en_name,unit,operate_key\n" +
			"\t\t}\n" +
			"\t\t,level{\n" +
			"\t\t\tid,i18n,cn_name,en_name,grade,order_num\n" +
			"\t\t}\n" +
			"\t\t\n" +
			"\t}\t\t\n" +
			"  }\n" +
			"}"
           ;


}
