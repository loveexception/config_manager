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
public class MyGraphQLControllerSpeedTest {
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
    public void 打开列表测试速度(){
        Stopwatch sw = Stopwatch.begin();

        String sql = controller.SQL;
        CharSegment seg = new CharSegment(sql);
        seg.set("sno","BJ-GWYTH-B6JR-Cisco-3560-1");


        Map<String,Object> obj = controller.sql(seg.toString());
        Logs.get().debug(obj);
        assertNotNull(obj);
        assertTrue(Lang.isEmpty(obj.get("errors")));
        assertEquals("BJ-GWYTH-B6JR-Cisco-3560-1", Mapl.cell(obj,"data.deviceBySno.sno"));
        String json = Json.toJson(obj);


        sw.stop();
        System.out.println(sw.getDuration());

    }

     @Test
    public void 测试速度() throws InterruptedException {
        Stopwatch sw = Stopwatch.begin();


       Object
               obj = controller.cache((String) "11");


        sw.stop();
        System.out.println(sw.getDuration());
        assertTrue(sw.getDuration() - 1000 > 0);


        sw = Stopwatch.begin();
        obj = controller.cache((String) "11");

        sw.stop();
        System.out.println(sw.getDuration());
        assertTrue(sw.getDuration() - 1000 < 0);


        sw = Stopwatch.begin();
        obj = controller.cache((String) "22");

        sw.stop();
        System.out.println(sw.getDuration());
        assertTrue(sw.getDuration() - 1000 > 0);


        sw = Stopwatch.begin();
        obj = controller.cache((String) "11");

        sw.stop();
        System.out.println(sw.getDuration());
        assertTrue(sw.getDuration() - 1000 < 0);


    }



    @Test
    public void 打开device缓存速度(){



        Object obj =null;
        List<Device> devices = dao.query(Device.class, Cnd.NEW());
        //先缓存完再发TOPIC
        devices.stream().forEach(device -> controller.device(device.getSno()));
        Stopwatch sw = Stopwatch.begin();

        for (Device device : devices) {
             obj = controller.device(device.getSno());
        }
        sw.stop();
        System.out.println(sw.getDuration());
        assertTrue(sw.getDuration() - 1000 < 0);


    }
    @Test
    public void 打开device测试速度(){
        Stopwatch sw = Stopwatch.begin();


        Object obj =null;
        List<Device> devices = dao.query(Device.class, Cnd.NEW());
       // devices.stream().forEach(device -> controller.device(device.getSno()));

        //先缓存完再发TOPIC

        devices.stream().forEach(device -> controller.device(device.getSno()));

        sw.stop();
        System.out.println(sw.getDuration());
        assertTrue(sw.getDuration() > 30000 );


    }
    @Test
    public void 删除缓存(){
        Object obj =null;

        controller.SQL = "{deviceBySno ( sno :\"${sno}\" ) {  " +
                " sno }}";

        //先缓存完再发TOPIC
        Stopwatch sw = Stopwatch.begin();


        obj = controller.device("BJ-GWYTH-B6JR-Cisco-3560-1");

        sw.stop();
        System.out.println(sw.getDuration());
        System.out.println(obj);
        assertTrue(sw.getDuration() - 10 > 0);

        sw = Stopwatch.begin();
        obj = controller.device("BJ-GWYTH-B6JR-Cisco-3560-1");

        sw.stop();
        System.out.println(sw.getDuration());
        System.out.println(obj);
        assertTrue(sw.getDuration() - 10 < 0);

        controller.killCache("BJ-GWYTH-B6JR-Cisco-3560-1");

        sw = Stopwatch.begin();
        obj = controller.device("BJ-GWYTH-B6JR-Cisco-3560-1");

        sw.stop();
        System.out.println(sw.getDuration());
        System.out.println(obj);
        assertTrue(sw.getDuration() - 10 > 0);

    }


}