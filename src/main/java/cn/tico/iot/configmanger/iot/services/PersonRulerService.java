package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * 业务标签 服务层实现
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean(args = {"refer:dao"})
public class PersonRulerService extends Service<PersonRuler> {
    public PersonRulerService(Dao dao) {
        super(dao);
    }

    public List insertAllRuler(List<PersonRuler> asList, String driverid) {
        for (PersonRuler ruler:asList) {
            ruler.setGradeid(driverid);
        }
        return this.dao().insert(asList);
    }

    public int updateAllRuler(List<PersonRuler> asList) {
        return this.dao().update(asList );
    }
}
