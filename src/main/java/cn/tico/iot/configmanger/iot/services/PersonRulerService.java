package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.device.Person;
import cn.tico.iot.configmanger.iot.models.device.PersonRuler;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Date;
import java.util.List;

/**
 * 业务标签 服务层实现
 *
 * @author haiming
 * @date 2019-04-16
 */
@IocBean(args = {"refer:dao"})
public class PersonRulerService extends Service<PersonRuler> {
    public PersonRulerService(Dao dao) {
        super(dao);
    }

    public List<PersonRuler> insertEntitys(List<PersonRuler> personRulers, String gradeid) {
        for (PersonRuler ruler:personRulers) {
            ruler.setGradeid(gradeid);
            ruler.setCreateBy(ShiroUtils.getSysUserId());
            ruler.setCreateTime(new Date());
        }
        return this.dao().insert(personRulers);
    }

    public int updateEntitys(List<PersonRuler> personRulers) {

        Dao forup = Daos.ext(this.dao(), FieldFilter.create(this.getEntityClass()
                ,null
                ,"^create_by|create_time$"
                , true));
        for (PersonRuler ruler:personRulers) {

            ruler.setUpdateBy(ShiroUtils.getSysUserId());
            ruler.setUpdateTime(new Date());
        }
        return forup.update(personRulers );
    }


}
