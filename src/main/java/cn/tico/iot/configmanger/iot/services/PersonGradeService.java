package cn.tico.iot.configmanger.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
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
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public Object queryPersonGrade(Cnd cnd) {

        List<PersonGrade> obj = this.dao().queryByJoin(this.getEntityClass(),"^rulers$",cnd);
        for (PersonGrade grade:obj) {
            List<PersonRuler> rulers = grade.getPersonRulers();


            Comparator compare = ComparableComparator.getInstance ();
            compare = ComparatorUtils. nullHighComparator(compare);
            ArrayList<Object> sortFields = new ArrayList<Object>();
            sortFields.add( new BeanComparator("orderNum" , compare)); //排序（第二排序）
            // 创建一个排序链
            ComparatorChain multiSort = new ComparatorChain(sortFields);



            Collections.sort(rulers,multiSort);
            grade.setPersonRulers(rulers);
        }

        return obj;
    }
}
