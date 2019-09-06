package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.device.PersonGrade;
import cn.tico.iot.configmanger.module.iot.models.device.PersonRuler;
import cn.tico.iot.configmanger.module.iot.models.driver.Grade;
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
            List<PersonRuler> temps = new ArrayList<PersonRuler>();
            for (int i = 0; i < rulers.size(); i++) {
                PersonRuler temp =  dao().fetchLinks(rulers.get(i),"normal");
                temps.add(temp);
            }


            Comparator compare = ComparableComparator.getInstance ();
            compare = ComparatorUtils. nullHighComparator(compare);
            ArrayList<Object> sortFields = new ArrayList<Object>();
            sortFields.add( new BeanComparator("orderNum" , compare)); //排序（第二排序）
            // 创建一个排序链
            ComparatorChain multiSort = new ComparatorChain(sortFields);



            Collections.sort(temps,multiSort);
            grade.setRulers(temps);
        }

        return obj;
    }

    public PersonGrade insertEntity(PersonGrade personGrade) {
        List<PersonRuler> rulers = personGrade.getRulers();
        if(Lang.isEmpty(rulers)){

        }else {
            for (int i = 0; i < rulers.size(); i++) {
                PersonRuler  temp =  rulers.get(i);
                temp.setOrderNum(i);
                temp.setCreateBy(ShiroUtils.getSysUserId());
                temp.setCreateTime(new Date());
            }
        }

        personGrade.setCreateBy(ShiroUtils.getSysUserId());
        personGrade.setCreateTime(new Date());
        return this.dao().insertWith(personGrade,"^rulers$");
    }




    public PersonGrade  updateEntity(PersonGrade personGrade) {
        List<PersonRuler> rulers = personGrade.getRulers();
        if(Lang.isEmpty(rulers)){

        }else {
            for (int i = 0; i < rulers.size(); i++) {
                PersonRuler  temp =  rulers.get(i);
                temp.setOrderNum(i);
                temp.setUpdateBy(ShiroUtils.getSysUserId());
                temp.setUpdateTime(new Date());
            }
        }

        personGrade.setUpdateBy(ShiroUtils.getSysUserId());
        personGrade.setUpdateTime(new Date());
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));
        return forup.updateWith(personGrade,"^rulers$");
    }
    public PersonGrade saveEntity(PersonGrade grade) {
        if(Lang.isEmpty(grade)){
            return null;
        }
        if(Strings.isEmpty(grade.getId())){
            return insertEntity(grade);
        }
         return  updateEntity(grade);

    }
    public int deleteEntity(String id) {
        PersonGrade grade = fetch(id);
        return     this.dao().deleteWith(grade,"^rulers$");
    }

    public List<PersonGrade> changeFromGrade(List<Grade> grades) {
        String json = Json.toJson(grades);
        return Json.fromJsonAsList(PersonGrade.class,json);
    }

    public List<PersonGrade> saveEntitys(PersonGrade[] grades) {
        List<PersonGrade> result = new ArrayList<>();
        int i = 0;
        for (PersonGrade persongrade:grades) {

            persongrade.setOrderNum(i++);
            PersonGrade obj = saveEntity(persongrade);
            result.add(obj);
        }
        return result;
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
