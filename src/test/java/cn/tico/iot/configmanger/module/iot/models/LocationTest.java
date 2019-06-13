package cn.tico.iot.configmanger.module.iot.models;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class LocationTest {
    Dao dao ;


    @Before
    public void setUp() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/nutztest");
         String mysqldriver ="com.mysql.cj.jdbc.Driver";
        dataSource.setDriverClassName(mysqldriver);
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        dao = new NutDao(dataSource);
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