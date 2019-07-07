package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.ArrayList;
import java.util.List;

@IocBean(args = {"refer:dao"})
public class NormalService extends Service<Normal> {
    public NormalService(Dao dao) {
        super(dao);
    }

    public List<Normal> insertAllNormal(List<Normal> normals,String driverid) {
        for (Normal normal : normals) {
            normal.setOrderNum(normal.getKey());
            normal.setDriverid(driverid);
        }
        this.dao().insert(normals);

        return normals;
    }

    public List<Normal> updateAllNormal(List<Normal> normals) {
        for (Normal normal : normals) {
            normal.setOrderNum(normal.getKey());
        }

         this.dao().update(normals);

         return normals;
    }
}
