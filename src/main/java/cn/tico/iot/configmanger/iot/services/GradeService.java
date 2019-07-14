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

import java.util.*;


@IocBean(args = {"refer:dao"})
public class GradeService  extends Service<Grade> {
    public GradeService(Dao dao) {
        super(dao);
    }




    public Object queryGrade(Cnd cnd) {

        List<Grade> obj = this.dao().queryByJoin(this.getEntityClass(),"^rulers$",cnd);
        for (Grade grade:obj) {
            List<Ruler> rulers = grade.getRulers();
            Collections.sort(rulers, Comparator.comparing(Ruler::getOrderNum));
            grade.setRulers(rulers);
        }

        return obj;
    }

    public Object gradeAllSave(Grade[] grades) {
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));
        for (int i =0 ; i < grades.length; i++) {

            Grade grade = grades[i];

            grade = InsertOrUpdate(grade);
            List<Ruler> rulers = InsertOrUpdateRulers(grade.getRulers());
            grade.setRulers(rulers);



                forup.updateLinks(grade,"^rulers$");


        }
        return grades;

    }

    private List<Ruler> InsertOrUpdateRulers(List<Ruler> rulers) {
        List<Ruler> result = new ArrayList<Ruler>();
        for (int i =0 ; i < rulers.size() ; i ++ ) {
                Ruler ruler = rulers.get(i);
                ruler.setOrderNum(i);
                ruler.setCreateTime(new Date());
                ruler.setCreateBy(ShiroUtils.getSysUserId());
                ruler.setUpdateTime(new Date());
                ruler.setUpdateBy(ShiroUtils.getSysUserId());

            result.add(ruler);


        }

        return result;
    }

    private Grade InsertOrUpdate(Grade grade) {

            grade.setCreateTime(new Date());
            grade.setCreateBy(ShiroUtils.getSysUserId());
            grade.setUpdateTime(new Date());
            grade.setUpdateBy(ShiroUtils.getSysUserId());


        return grade;
    }



}
