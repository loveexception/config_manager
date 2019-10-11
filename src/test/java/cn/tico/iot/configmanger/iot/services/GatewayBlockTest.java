package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.MainLauncher;
import cn.tico.iot.configmanger.module.iot.graphql.GatewayBlock;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.boot.NbApp;
import org.nutz.boot.test.junit4.NbJUnit4Runner;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

import java.util.List;

@IocBean(create = "init")
@RunWith(NbJUnit4Runner.class)
public class GatewayBlockTest extends Assert {

    //private static final Log log = Logs.get();

    // 跟通常的@Inject完全一样.
    @Inject("refer:$ioc")
    protected Ioc ioc;

    @Inject
    protected Dao dao;
    @Inject
    GatewayBlock block ;

    @Inject
    KafkaBlock kafkaBlock;

    public static NbApp createNbApp() {

        return new NbApp().setMainClass(MainLauncher.class).setPrintProcDoc(false);
    }

    public void init(){
        System.out.println("init");
    }

    @Test
    public void exec() {
        NutMap map = NutMap.NEW();
        map.addv("sno","FD2343243")
       // .addv("lanip","172.0.0.1")
        .addv("outerip","172.123.32.22")
        .addv("contrl_device_api_port","8080")
        .addv("contrl_device_api_uri","/gw/updowndevice");


        //Daos.migration(dao,SubGateway.class, true, false, false);

        block .exec("register","", Json.toJson(map),1);
    }

    @Test
    public void haveSnoGateway() {
        SubGateway sub = new SubGateway();
        sub.setSno("SNO11");
        Gateway gateway = block.haveSnoGateway(sub);
        assertNotNull(gateway);
        assertEquals(gateway.getSno(),"SNO11");
        assertEquals(gateway.getIp(),"2.2.2.2");

    }

    //@Test
    public void AllSnoZhong() {
        Cnd cnd = Cnd.NEW();

        cnd.and("status","=","true");
        cnd.and("delflag","=","false");

        List<Device> devices =  dao.query(Device.class ,cnd);

        System.out.println(devices);
        assertEquals(5,devices.size());
        for (int i = 0; i < devices.size(); i++) {
            kafkaBlock.produce("config","sno",devices.get(i).getSno());
        }


    }

}