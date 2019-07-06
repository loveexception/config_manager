package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.base.Location;
import cn.tico.iot.configmanger.iot.models.base.Tag;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.json.Json;

import java.util.ArrayList;
import java.util.Map;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static org.junit.Assert.assertNotNull;

public class GraphQLTest {
    Dao dao ;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }




    @Test
    public void getJson() {
        json();
    }
    public Device json(){
        Device dev = new Device();

        dev.setId("1001");
        dev.setCnName("前门应急");
        dev.setEnName("qianMen550");

        dev.setSno("F0X4343345");
        dev.setIp("192.168.7.55");
        dev.setCycle(30000);

        dev.setKinds(new ArrayList<Kind>());
        dev.setTags(new ArrayList<Tag>());
        dev.setDept(new Dept());
        dev.setLocations(new ArrayList<Location>());
        dev.setDriver(new Driver());

        dev.getKinds().add(new Kind());
        dev.getTags().add(new Tag());
        dev.getLocations().add(new Location());
        dev.getDriver().setCnName("maodajun");

        String json = Json.toJson(dev);
        Map model = dev.getEnv();

        System.out.println(json);
        System.out.println(model);
        return dev;

    }

    @Test
    public  void getGraphQL(){
        String schema = "type Query { " +
                " hello(what:String = \"World\"): String " +
                " }";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query",
                        builder -> builder.dataFetcher("hello",
                                env -> "Hello "+env.getArgument("what")+"!"))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        System.out.println(executionResult.<Map<String,Object>>getData());
    }

    @Test
    public  void getGraphQL22(){
        DevService devService = new DevService(); //instantiate the service (or inject by Spring or another framework)
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages("io.leangen")
                .withOperationsFromSingleton(devService) //register the service
                .generate(); //done ;)
        GraphQL graphQL = new GraphQL.Builder(schema).build();
        //keep the reference to GraphQL instance and execute queries against it.
//this operation selects a user by ID and requests name, regDate and twitterProfile fields only
        ExecutionResult result = graphQL.execute(
                "{dev(sno:\"abc\"){" +
                            "cn_name" +
                            ",driver { " +
                                "i18n"+
                                ",devdri(sno:\"ddd\")"+

                        "} " +
                            ",dept2{" +
                                "i18n" +
                                ",depts"+
                                ",deptArray"+
                                ",deptMap{sno}"+
                                ",max{sno}"+

                        "}" +
                            ",sno,env,i18n" +
                            "" +
                        "}}");

        System.out.println("----------mao-----------");
        System.out.println(Json.toJson(result));

    }
}
