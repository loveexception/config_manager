package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.device.Device;
import cn.tico.iot.configmanger.iot.models.device.Gateway;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonGrade;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.Date;

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



        public Person insertEntity(Person person) {


            person.setCreateBy(ShiroUtils.getSysUserId());
            person.setCreateTime(new Date());
            return this.dao().insert(person);
        }

        public int updatePerson(Person person) {
            person.setUpdateBy(ShiroUtils.getSysUserId());
            person.setUpdateTime(new Date());
            Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass(),null,"^create_by|create_time$", true));
            return forup.update(person);
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
}
