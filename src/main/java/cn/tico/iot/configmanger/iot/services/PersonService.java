package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonGrade;
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

/**
 * 业务标签 服务层实现
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean(args = {"refer:dao"})
public class PersonService extends Service<Person> {
    public PersonService(Dao dao) {
        super(dao);
    }

    public Person insertEntity(Person person,String deep) {



        person.setCreateBy(ShiroUtils.getSysUserId());
        person.setCreateTime(new Date());
        person =  insert(person);
        if(Lang.isEmpty(person.getGrades())){
            return person;
        }
        for(PersonGrade personGrade:person.getGrades()){
            personGrade.setPersonid(person.getId());
            this.dao().insertWith(personGrade,deep);
        }

        return person;
    }

    public int updateEntity(Person person) {
        person.setUpdateBy(ShiroUtils.getSysUserId());
        person.setUpdateTime(new Date());
        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));
        return forup.update(person);
    }
    public Person saveEntity(Person person) {
        if(Lang.isEmpty(person)){
            return null;
        }
        if(Strings.isEmpty(person.getId())){
            return insertEntity(person,"");
        }
        int result = updateEntity(person);
        if(result==1){
            return person;
        }

        return null;
    }
    public int deleteEntity(String id) {
        Person person = fetch(id);
        if(person==null){
            return 0;
        }
        if(Lang.isEmpty(person.getGrades())){
            return delete(id);
        }
        for (PersonGrade grade: person.getGrades()) {
            this.dao().deleteWith(grade,"^rulers$");
        }

        return delete(id);
    }

    public Person queryEntityDeep(Person person) {
        Cnd cnd = Cnd.NEW();
        cnd.and("deviceid","=",person.getDeviceid())
                .and("normalid","=",person.getNormalid());
        List<Person> persons = this.query(cnd);
        if(Lang.isEmpty(person)){
            return null;
        }
        if(persons.iterator().hasNext()){
            person = persons.iterator().next();
        }else {
            return null;
        }
        person = this.dao().fetchLinks(person,"^grades|normal|device$");
        if(Lang.isEmpty(person.getGrades())){
           // delete(person.getId());
            return null;
        }
        List<PersonGrade> grades = person.getGrades();
        List<PersonGrade> result = new ArrayList<>();
        for(PersonGrade grade : grades){
            PersonGrade personGrade = this.dao().fetchLinks(grade,"^rules$");
            result.add(personGrade);
        }
        person.setGrades(result);
        return person;



    }
}
