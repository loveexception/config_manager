package cn.tico.iot.configmanger.iot.services;


import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.driver.Driver;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;


@IocBean(args = {"refer:dao"})
public class GradeService  extends Service<Driver> {
    public GradeService(Dao dao) {
        super(dao);
    }


}
