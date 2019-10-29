package cn.tico.iot.configmanger.module.sys.services;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.iot.services.MysqlTestDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

import static org.junit.Assert.*;
@Slf4j
public class ConfigServiceTest {
    Dao dao ;
    @Inject
    private ConfigService configService;
    @Before
    public void setUp() throws Exception {
        dao = MysqlTestDao.NEW2();
        dao.create(ConfigService.class,true);
        configService = new ConfigService(dao);

    }

    @Test
    public void getValue() {
        String appUploadPath = configService.getValue("AppUploadPath");
        log.info(appUploadPath);
    }

    @Test
    public void getJson(){

        NutMap map = Json.fromJson(NutMap.class, "{data:{pager:{pagerSize:20},users:[{id:123,name:'wendal'}, {id:345}]}}");
        System.out.println(map.eval("data.pager.pagerSize"));
        System.out.println(map.eval("data.users[0].name"));
        System.out.println(map.eval("data.users[1].name"));

    }


}