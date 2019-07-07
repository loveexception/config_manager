package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.device.Gateway;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean(args = {"refer:dao"})
public class RulerService extends Service<Ruler> {


    public RulerService(Dao dao) {
        super(dao);
    }


}
