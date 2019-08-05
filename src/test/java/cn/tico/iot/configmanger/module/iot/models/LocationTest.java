package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.MysqlTestDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;

public class LocationTest {
    Dao dao ;


    @Before
    public void setUp() throws Exception {
        dao = MysqlTestDao.NEW();

        dao.create(Location.class,true);

    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    @Test
    public void creatDBOne(){
        Location location = new Location();
        //location.setId("1001");
        location.setParentId(null);
        dao.insert(location);
        System.out.println(location.getId());
        Assert.assertTrue(location.getId()!=null);

    }



}