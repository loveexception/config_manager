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

    public List<Normal> insertAllNormal(List<Normal> normals) {
        List<Normal> result = new ArrayList<Normal>();
        for (Normal normal :
                normals) {
            Normal temp =  this.dao().insert(normal);
            result.add(temp);
        }

        return result;
    }

    public List<Normal> updateAllNormal(List<Normal> normals) {
        List<Normal> result = new ArrayList<Normal>();
        for (Normal normal : normals) {
           int  i = this.dao().update(normal);
           if(i==1){
               result.add(normal);
           }
        }

         return result;
    }
}
