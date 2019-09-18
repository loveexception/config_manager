package cn.tico.iot.configmanger.module.iot.services;

import com.alibaba.druid.pool.DruidDataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;

public class MysqlTestDao {
    public static Dao NEW() {
        SimpleDataSource dataSource = new SimpleDataSource();
		//dataSource.setUrl("jdbc:mysql://172.16.16.10/nutztest");
         dataSource.setUrl("jdbc:mysql://localhost/zhihutest");
        String mysqldriver ="com.mysql.cj.jdbc.Driver";
        try {
            dataSource.setDriverClassName(mysqldriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return  new NutDao(dataSource);
    }
}
