package cn.tico.iot.configmanger.iot.services;


import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@IocBean(args = {"refer:dao"})
public class GradeService  extends Service<Grade> {
    public GradeService(Dao dao) {
        super(dao);
    }




    public Object queryGrade(Cnd cnd) {

        Object obj = this.dao().queryByJoin(this.getEntityClass(),"^rulers$",cnd);
        return obj;
    }

    public Object gradeAllSave(Grade[] grades) {
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));
        for (Grade grade:grades) {
            grade = InsertOrUpdate(grade);
            List<Ruler> rulers = InsertOrUpdateRulers(grade.getRulers());
            grade.setRulers(rulers);

            if(Strings.isNotBlank(grade.getId())){

                forup.updateLinks(grade,"^rulers$");
            }else {
                forup.insertLinks(grade,"^rulers$");
            }

        }
        return null;

    }

    private List<Ruler> InsertOrUpdateRulers(List<Ruler> rulers) {
        List<Ruler> result = new ArrayList<Ruler>();
        for (Ruler ruler:rulers) {
            if(Strings.isNotBlank(ruler.getId())){
//                grade.setCreateBy(null);
//                grade.setCreateTime(null);
//                grade.setDelFlag(null);
//                grade.setUpdateBy(ShiroUtils.getSysUserId());
//                grade.setUpdateTime(new Date());

            }else {

            }


        }

        return result;
    }

    private Grade InsertOrUpdate(Grade grade) {
        if(Strings.isNotBlank(grade.getId())){
            grade.setCreateBy(null);
            grade.setCreateTime(null);
            grade.setDelFlag(null);
            grade.setUpdateBy(ShiroUtils.getSysUserId());
            grade.setUpdateTime(new Date());

        }else{
            grade.setCreateTime(new Date());
            grade.setCreateBy(ShiroUtils.getSysUserId());
            grade.setUpdateTime(new Date());
            grade.setUpdateBy(ShiroUtils.getSysUserId());


        }
        return grade;
    }


//    Kind info = this.fetch(kind.getParentId());
//        kind.setAncestors(info.getAncestors() + "," + kind.getParentId());
//        kind.setLevel( ""+(Lang.str2number(info.getLevel()).intValue()+1));
//        kind.setUpdateBy(ShiroUtils.getSysUserId());
//        kind.setUpdateTime(new Date());
//
//    Dao forup = Daos.ext(this.dao(), FieldFilter.create(kind.getClass(), true));
//        return forup.update(kind);
}
