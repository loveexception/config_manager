package cn.tico.iot.configmanger.module.iot.services;


import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
import cn.tico.iot.configmanger.module.iot.models.driver.Ruler;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.*;


@IocBean(args = {"refer:dao"})
public class GradeService  extends Service<Grade> {
    public GradeService(Dao dao) {
        super(dao);
    }




    public Object queryEntity(Cnd cnd) {

        List<Grade> obj = this.dao().queryByJoin(this.getEntityClass(),"^rulers$",cnd);
        for (Grade grade:obj) {
            List<Ruler> rulers = grade.getRulers();
            List<Ruler> mylist = new ArrayList<>();
            for (int i = 0; i < rulers.size(); i++) {
                Ruler temp = rulers.get(i);
                temp = this.dao().fetchLinks(temp,"^normal$");
                mylist.add(temp);
            }
            Collections.sort(mylist);
            grade.setRulers(mylist);
        }

        return obj;
    }

    public List<Grade> saveEntitys(Grade[] grades) {
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(), true));
        List<Grade> result = new ArrayList<Grade>();
        for (int i =0 ; i < grades.length; i++) {
            Grade grade = grades[i];
            grade = saveEntity(grade,i);
            List<Ruler> rulers = saveSubs(grade.getRulers());
            grade.setRulers(rulers);
            result.add(grade);

        }
        return forup.updateWith(result,"^rulers$");


    }

    private List<Ruler> saveSubs(List<Ruler> rulers) {
        List<Ruler> result = new ArrayList<Ruler>();
        if(Lang.isEmpty(rulers)){
            return Lists.newArrayList();
        }
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

    private Grade saveEntity(Grade grade, int index) {
            grade.setOrderNum(index);
            grade.setCreateTime(new Date());
            grade.setCreateBy(ShiroUtils.getSysUserId());
            grade.setUpdateTime(new Date());
            grade.setUpdateBy(ShiroUtils.getSysUserId());
        return grade;
    }
    public int deleteEntityAndSub(String id) {
        Grade grade = new Grade();
        grade.setId(id);

        return this.dao().deleteWith(grade,"^rules$");
    }
}
