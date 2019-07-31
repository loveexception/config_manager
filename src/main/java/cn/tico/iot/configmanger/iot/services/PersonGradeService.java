package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.iot.models.driver.Grade;
import cn.tico.iot.configmanger.iot.models.driver.Ruler;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.*;

/**
 * 业务标签 服务层实现
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean(args = {"refer:dao"})
public class PersonGradeService extends Service<PersonGrade> {
    public PersonGradeService(Dao dao) {
        super(dao);
    }

    public List<PersonGrade> queryEntity(Cnd cnd) {

        List<PersonGrade> obj = this.dao().queryByJoin(this.getEntityClass(),"^rulers$",cnd);
        for (PersonGrade grade:obj) {
            List<PersonRuler> rulers = grade.getRulers();
            if(Lang.isEmpty(rulers)){
                continue;
            }

            Comparator compare = ComparableComparator.getInstance ();
            compare = ComparatorUtils. nullHighComparator(compare);
            ArrayList<Object> sortFields = new ArrayList<Object>();
            sortFields.add( new BeanComparator("orderNum" , compare)); //排序（第二排序）
            // 创建一个排序链
            ComparatorChain multiSort = new ComparatorChain(sortFields);



            Collections.sort(rulers,multiSort);
            grade.setRulers(rulers);
        }

        return obj;
    }

    public PersonGrade insertEntity(PersonGrade personGrade) {

        personGrade.setCreateBy(ShiroUtils.getSysUserId());
        personGrade.setCreateTime(new Date());
        return this.dao().insert(personGrade);
    }




    public int updateEntity(PersonGrade person) {
        person.setUpdateBy(ShiroUtils.getSysUserId());
        person.setUpdateTime(new Date());
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));
        return forup.update(person);
    }
    public PersonGrade saveEntity(PersonGrade grade) {
        if(Lang.isEmpty(grade)){
            return null;
        }
        if(Strings.isEmpty(grade.getId())){
            return insertEntity(grade);
        }
        int result = updateEntity(grade);
        if(result==1){
            return grade;
        }

        return null;
    }
    public int deleteEntity(String id) {
        PersonGrade grade = fetch(id);
        return     this.dao().deleteWith(grade,"^rulers$");
    }

    public List<PersonGrade> changeFromGrade(List<Grade> grades) {
        String json = Json.toJson(grades);
        return Json.fromJsonAsList(PersonGrade.class,json);
    }

//    List<PersonGrade> personGrades = new ArrayList<PersonGrade>();
//                for (Grade grade:grades
//                     ) {
//        PersonGrade personGrade = new PersonGrade();
//        personGrade.setGrade(grade.getGrade());
//        personGrade.setCnName(grade.getCnName());
//        personGrade.setEnName(grade.getEnName());
//        List<PersonRuler> personRulers = new ArrayList<PersonRuler>();
//        for(Ruler ruler:grade.getRulers()){
//            PersonRuler personRuler = new PersonRuler();
//            personRuler.setNormalid(ruler.getNormalid());
//            personRuler.setLogic(ruler.getLogic());
//            personRuler.setVal(ruler.getVal());
//            personRuler.setSymble(ruler.getSymble());
//            personRuler.setOrderNum(ruler.getOrderNum());
//
//            personRulers.add(personRuler);
//
//        }
//        personGrade.setRulers(personRulers);
//
//        personGrades.add(personGrade);
//
//    }
//
//			    person.setGrades(personGrades);
}
