package cn.tico.iot.configmanger.module.sys.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.sys.models.Post;

/**
 * 岗位 服务层实现
 * 
 * @author haiming
 * @date 2019-04-10
 */
@IocBean(args = {"refer:dao"})
public class PostService extends Service<Post> {
	public PostService(Dao dao) {
		super(dao);
	}
}
