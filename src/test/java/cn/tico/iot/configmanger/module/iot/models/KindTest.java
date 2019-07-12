package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.base.Tag;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.services.MysqlTestDao;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.json.Json;

import static org.junit.Assert.*;

public class KindTest {
    Dao dao ;


    @Before
    public void setUp() throws Exception {


        dao = MysqlTestDao.NEW();
       // Daos.createTablesInPackage(dao, "cn.tico.iot.configmanger.module.iot.models", true);

        //Daos.migration(dao, "cn.tico.iot.configmanger.module.iot.models", true, false, false);

    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }


    @Test
    public void getId() {
        Kind kind = new Kind();
        dao.create(Kind.class,true);

        dao.insert(kind);
        assertNotNull(kind.getId());


    }
    @Test
    public void getModel() {
        Device dev = new Device();

        dao.create(Device.class,true);
        dao.create(Dept.class,true);
        Dept dept = new Dept();
        dev.setDept(dept);
        dao.insertWith(dev,"dept");



    }

    @Test
    public void getJson() {
        Device dev = new Device();

        dev.setId("1001");
        dev.setCnName("前门应急");
        dev.setEnName("qianMen550");

        dev.setSno("F0X4343345");
        dev.setIp("192.168.7.55");
        dev.setCycle(30000);

        String json = Json.toJson(dev);
        dev.getEnv();

        System.out.println(json);


    }

    @Test
    public  void getTag(){
        dao.create(Tag.class,true);
    }

    @Test
    public  void getKind(){
        dao.create(Normal.class,true);

    }

    @Test
    public void allTable(){

        Daos.createTablesInPackage(dao,"cn.tico.iot.configmanger.iot.models",true );
        Daos.createTablesInPackage(dao,"cn.tico.iot.configmanger.module.other.models",true );
    }

}