package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

@IocBean(args = {"refer:dao"})
public class NormalService extends Service<Normal> {
    public NormalService(Dao dao) {
        super(dao);
    }

    public List<Normal> insertAllNormal(List<Normal> normals) {

        return this.dao().insert(normals);
    }

    public int updateAllNormal(List<Normal> asList) {
        return this.dao().update(asList);
    }
}
