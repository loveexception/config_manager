package cn.tico.iot.configmanger.module.sys.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.sys.models.Config;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.Arrays;

/**
 * 系统参数 服务层实现
 *
 * @author haiming
 * @date 2019-04-17
 */
@IocBean(args = {"refer:dao"})
public class ConfigService extends Service<Config> {
    public ConfigService() {
    }

    public ConfigService(Dao dao) {
        super(dao);
    }

    @Override
    public void delete(String[] ids) {
        if (Lang.isNotEmpty(ids)) {
            Arrays.stream(ids).forEach(id -> {
                this.dao().delete(this.getEntityClass(), id);
            });
        }
    }

    public String getValue(String paramKey) {
        Criteria cri = Cnd.cri();
        cri.where().and("configKey", "=", paramKey);
        Config config = dao().fetch(Config.class, cri);
        return config.getConfigValue();
    }
}
