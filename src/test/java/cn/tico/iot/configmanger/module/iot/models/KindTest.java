package cn.tico.iot.configmanger.module.iot.models;

import cn.tico.iot.configmanger.module.sys.models.Dept;
import com.alibaba.druid.pool.DruidDataSource;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.util.Daos;
import org.nutz.json.Json;

import java.util.Map;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static org.junit.Assert.*;

public class KindTest {
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
        dao.create(Kind.class,true);

    }

    @Test
    public void allTable(){
        Daos.createTablesInPackage(dao,"cn.tico.iot.configmanger.module.iot.models",true );
    }

}