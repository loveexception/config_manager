package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.mao.redis.KindManager;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.List;


@IocBean
public class KindGrap {


    @Inject
    private KindManager kindManager;

    @Inject
    private Dao dao;

    @GraphQLQuery
    public Kind kind(@GraphQLContext Device device) {

        return kind(device.getKindid());
    }
    @GraphQLQuery
    public List<Kind> kinds(@GraphQLContext Device device) {
        List<Kind> locations =  kindManager.allFamilyWithMe(device.getKindid());
        if(Lang.isNotEmpty(locations)){
            return locations;
        }
        return dao.query(Kind.class, Cnd.NEW().and("ancestors","like","%"+device.getKindid()+"%"));
    }
    @GraphQLQuery
    public Kind kind(@GraphQLArgument(name="id") String id) {

        if(Strings.isBlank(id)){
            return null;
        }
        Kind kind =  kindManager.get(id);
        if(Lang.isNotEmpty(kind)){
            return kind;
        }
        return dao.fetch(Kind.class,id);
    }


}