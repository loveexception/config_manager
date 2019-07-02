package cn.tico.iot.configmanger.module.iot.services;

import com.alibaba.druid.pool.DruidDataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

public class MysqlTestDao {
    public static Dao NEW() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1/nutztest");
        String mysqldriver ="com.mysql.cj.jdbc.Driver";
        dataSource.setDriverClassName(mysqldriver);
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        return  new NutDao(dataSource);
    }
}
