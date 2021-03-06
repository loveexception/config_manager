package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
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
        for (int i = 0 ; i < normals.size();i++) {
            Normal normal = normals.get(i);
            normal.setKey(null);
            normal.setOrderNum(i);
            normal.setDriverid(driverid);
        }
        this.dao().insert(normals);

        return normals;
    }

    public List<Normal> updateAllNormal(List<Normal> normals,int start) {
        for (int i = 0 ; i < normals.size();i++ ) {
            Normal normal = normals.get(i);
            normal.setKey(null);
            normal.setOrderNum(start + i );
        }
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));

         forup.update(normals);

         return normals;
    }

    public List<Grade> querySubs(String id) {
        List<Grade> grades = this.dao().queryByJoin(Grade.class,"^grades$", Cnd.NEW().and("normal_id","=",id));
        if(Lang.isEmpty(grades)){
            return Lists.newArrayList();
        }
        List<Grade> result = new ArrayList<Grade>();
        for (Grade grade : grades){
            Grade temp = this.dao().fetchLinks(grade,"^rulers$");
            result.add(temp);
        }

        return result;
    }
}
