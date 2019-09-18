package cn.tico.iot.configmanger.module.sys.services;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.iot.services.MysqlTestDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;

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
}