package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.Location;
import cn.tico.iot.configmanger.module.iot.models.Tag;
import cn.tico.iot.configmanger.module.sys.models.Dict;
import cn.tico.iot.configmanger.module.sys.models.User;
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
public class TagService extends Service<Tag> {
	public TagService(Dao dao) {
		super(dao);
	}

	public Tag insertTag(Tag tag) {
		User user =  ShiroUtils.getSysUser();
		tag.setDeptid(user.getDeptId());

		tag.setCreateBy(ShiroUtils.getSysUserId());
		tag.setCreateTime(new Date());
		return this.dao().insert(tag);
	}

	public int updateTag(Tag tag) {
		tag.setDeptid(null);
		tag.setUpdateBy(ShiroUtils.getSysUserId());
		tag.setUpdateTime(new Date());
		Dao forup = Daos.ext(this.dao(), FieldFilter.create(Location.class, true));
		return forup.update(tag);


	}

}
