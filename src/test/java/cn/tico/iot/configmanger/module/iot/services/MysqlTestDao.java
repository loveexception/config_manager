package cn.tico.iot.configmanger.module.iot.services;

import com.alibaba.druid.pool.DruidDataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import java.sql.SQLException;

public class MysqlTestDao {
    public static Dao NEW() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/nutztest");
        String mysqldriver ="com.mysql.cj.jdbc.Driver";
        dataSource.setDriverClassName(mysqldriver);

        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        return  new NutDao(dataSource);
    }
}
