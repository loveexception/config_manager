package cn.tico.iot.configmanger.iot.services;


import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;


@IocBean(args = {"refer:dao"})
public class GradeService  extends Service<Grade> {
    public GradeService(Dao dao) {
        super(dao);
    }




    public Object queryGrade(Cnd cnd) {
        Object obj = this.dao().queryByJoin(this.getEntityClass(),"^rulers$",cnd);
        return obj;
    }
}
