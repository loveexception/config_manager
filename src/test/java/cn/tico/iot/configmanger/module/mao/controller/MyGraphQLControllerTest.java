package cn.tico.iot.configmanger.module.mao.controller;

import cn.tico.iot.configmanger.MainLauncher;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.mao.graphql.DeviceGrap;
import cn.tico.iot.configmanger.module.mao.graphql.DriverGrap;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.jsonwebtoken.lang.Collections;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.boot.NbApp;
import org.nutz.boot.test.junit4.NbJUnit4Runner;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.segment.CharSegment;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.nutz.integration.jedis.RedisInterceptor.jedis;


@IocBean
@RunWith(NbJUnit4Runner.class)
public class MyGraphQLControllerTest {
    @Inject
    MyGraphQLController controller ;
    @Inject
    DeviceGrap deviceGrap;

    @Inject
    Dao dao;

    @Inject
    DriverGrap driverGrap;

    private GraphQLSchema schema;


    public static NbApp createNbApp() {

        return new NbApp().setMainClass(MainLauncher.class).setPrintProcDoc(false);
    }

    public void init(){
        System.out.println("init");
    }
    @Before
    @Aop("redis")
    public void setUp() throws Exception {
        removeAllKeys();
    }

    public void removeAllKeys() {
        Set<String> keys = jedis().keys("*");
        keys.stream().forEach(key-> jedis().del(key));
    }

    @After
    @Aop("redis")
    public void tearDown() throws Exception {
        removeAllKeys();

    }

    @Test
    public void 打开项目() {
        String sql = "{deviceBySno ( sno :\"\" ) { id }}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));

    }
    @Test
    public void 打开项目失败() {
        String sql = "{deviceBySno ( sno =\"\" ) { id }}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertFalse(Lang.isEmpty(obj.get("errors")));

    }

    @Test
    public void 打开二个项目() {
        schema = new GraphQLSchemaGenerator()
                .withBasePackages("io.leangen")
                .withOperationsFromSingleton(deviceGrap) //register the service
                .withOperationsFromSingleton(driverGrap) //register the service

                .generate();
        controller.server = new GraphQL.Builder(schema).build();

        String sql = "{driver ( id :\"\" ) {  cn_name}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));

    }

    @Test
    public void 真实项目() {

        String sql = "{deviceBySno ( sno :\"2102359504DMK5001523\" ) { cn_name ,sno}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("2102359504DMK5001523", Mapl.cell(obj,"data.deviceBySno.sno"));

    }


    @Test
    public void 打开级连项目(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  " +
                "sno, cn_name ,gateway { sno } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        assertEquals("mao5", Mapl.cell(obj,"data.deviceBySno.gateway.sno"));

    }

    @Test
    public void 打开级连多对多(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  sno, cn_name ,tags { cn_name } }}";


        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object list = Mapl.cell(obj,"data.deviceBySno.tags");
        assertTrue( Lang.isNotEmpty( list));

        assertEquals( 4, ((List)list).size() );

    }
    @Test
    @Aop("redis")
    public void 打开级连地理信息(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  sno, cn_name ,location { cn_name } }}";
        Set<String> keys= jedis().keys("*");
        Logs.get().debug(keys);

        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object location = Mapl.cell(obj,"data.deviceBySno.location.cn_name");
        assertTrue( Lang.isNotEmpty( location));

        assertEquals( "应急会议室", location);

    }
    @Test
    @Aop("redis")
    public void 打开级连多条地理信息(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  sno, cn_name ,locations { cn_name } }}";
        Set<String> keys= jedis().keys("*");
        Logs.get().debug(keys);

        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object location = Mapl.cell(obj,"data.deviceBySno.locations[0].cn_name");
        assertTrue( Lang.isNotEmpty( location));

        assertEquals( "中国", location);

    }
    @Test
    public void 打开级连类别信息(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  sno, cn_name ,kind { cn_name } }}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object location = Mapl.cell(obj,"data.deviceBySno.kind.cn_name");
        assertTrue( Lang.isNotEmpty( location));
        assertEquals( "group550", location);
    }
    @Test
    public void 打开级连驱动信息(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  sno, cn_name ,driver { cn_name } }}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object location = Mapl.cell(obj,"data.deviceBySno.driver.cn_name");
        assertTrue( Lang.isNotEmpty( location));
        assertEquals( "polycom_group550驱动", location);
    }
    @Test
    public void 打开级连驱动属性列表(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  sno, cn_name , driver { normals { cn_name}  } }}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.normals");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( 32, Collections.size((Collection) result));
        Logs.get().debug(result);
    }

    @Test
    public void 打开级连驱动下的告警列表(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  " +
                "sno,  driver { normals { grades{ cn_name } }  } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.normals[0].grades");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( 4, Collections.size((Collection) result));
        Logs.get().debug(result);
    }
    @Test
    public void 打开级连驱动下的告警规则列表(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  " +
                "sno, cn_name , driver {  grades{ subRulers{ val }  } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.grades[0].subRulers");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( 2, Collections.size((Collection) result));
        Logs.get().debug(result);
    }
    @Test
    public void 打开级连驱动下的告警规则名子列表(){
        String sql = "{deviceBySno ( sno :\"FD161330CE93D7\" ) {  " +
                "sno, cn_name , driver { normals {cn_name, grades{ subRulers{ normal{ cn_name } }  } }  } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("FD161330CE93D7", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.normals[0].grades[0].subRulers[0].normal.cn_name");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "音频接收丢包数", result);
        Logs.get().debug(result);
    }
    @Test
    public void 打开级连个性化告警列表(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  persons {  grades { cn_name } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.persons[0].grades");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "端口14已断开", Mapl.cell(((List) result).get(0),"cn_name") );


        Logs.get().debug(result);
    }

    @Test
    public void 打开级连个性化告警规则列表(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  persons {  grades { subRulers{ val } } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.persons[0].grades[0].subRulers");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "false", Mapl.cell(((List) result).get(0),"val") );


        Logs.get().debug(result);
    }
    @Test
    public void 打开级连个性化告警规则列名列表(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  persons {  grades { subRulers{ normal{ cn_name} } } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.persons[0].grades[0].subRulers[0].normal");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "端口14连接状态", Mapl.cell(result,"cn_name") );


        Logs.get().debug(result);
    }

    @Test
    public void 打开级连公司(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  dept { id,dept_name } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.dept");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "河南省电力公司", Mapl.cell(result,"dept_name") );


        Logs.get().debug(result);
    }
    @Test
    public void 打开所有的规则列列(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  driver { rulers { val , normal_id} } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.rulers");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( 26, ((List)result).size() );


        Logs.get().debug(result);
    }

    @Test
    public void 打开所有的规则列tie名(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  driver { rulers { tie { cn_name}  } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.rulers[0].tie.cn_name");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "端口16连接状态",result );


        Logs.get().debug(result);
    }
    @Test
    public void 打开个性化规则名(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  personRulers {   val  } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.personRulers[0].val");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "false",result );


        Logs.get().debug(result);
    }
    @Test
    public void 打开个性化规则tie名(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  personRulers {   tie{cn_name}  } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.personRulers[0].tie.cn_name");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "端口26连接状态",result );


        Logs.get().debug(result);
    }
    @Test
    public void 打开个性化规则grade名(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  personRulers {   level {grade}  } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.personRulers[0].level.grade");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "critical",result );


        Logs.get().debug(result);
    }
    @Test
    public void 打开所有的规则列级别名(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  driver { rulers { level { cn_name,grade}  } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.rulers[0].level.grade");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "critical",result );


        Logs.get().debug(result);
    }

    @Test
    public void 打开所有的列级别名(){
        String sql = "{deviceBySno ( sno :\"BJ-GWYTH-B6JR-Cisco-3560-1\" ) {  " +
                "sno,  driver { grades { grade  } } " +
                "}}";
        Map<String,Object> obj = controller.sql(sql);
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        Object result = Mapl.cell(obj,"data.deviceBySno.driver.grades[0].grade");
        assertTrue( Lang.isNotEmpty( result));
        assertEquals( "critical",result );


        Logs.get().debug(result);
    }





    public String bigestSQL() {
        return  "{deviceBySno ( sno :\"${sno}\" ) {  " +
                "sno,  " +
                "   person {  " +
                "       grades { " +
                "           subRulers{ " +
                "               normal{ " +
                "                   cn_name" +
                "               } " +
                "           } " +
                "       }" +
                "   } " +
                "   ,driver { " +
                "       cn_name " +
                "       grades { " +
                "           subRulers{ " +
                "               normal { " +
                "                   cn_name " +
                "               } " +
                "           }" +
                "       }" +
                "       ,normals { " +
                "           cn_name " +
                "       } " +
                "   }  " +
                "}}";


    }
}