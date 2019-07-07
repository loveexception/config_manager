package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.driver.Normal;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.ArrayList;
import java.util.List;

@IocBean(args = {"refer:dao"})
public class NormalService extends Service<Normal> {
    public NormalService(Dao dao) {
        super(dao);
    }

    public List<Normal> insertAllNormal(List<Normal> normals,String driverid) {
        for (Normal normal : normals) {
            String order =normal.getKey();
            normal.setKey(null);
            normal.setOrderNum(Lang.str2number(order).intValue());
            normal.setDriverid(driverid);
        }
        this.dao().insert(normals);

        return normals;
    }

    public List<Normal> updateAllNormal(List<Normal> normals) {
        for (Normal normal : normals) {
            String order = normal.getKey();
            normal.setKey(null);
            normal.setOrderNum(Lang.str2number(order).intValue());
        }
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));

         forup.update(normals);

         return normals;
    }
}
