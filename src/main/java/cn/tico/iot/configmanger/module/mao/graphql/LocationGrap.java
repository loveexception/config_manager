package cn.tico.iot.configmanger.module.mao.graphql;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.mao.redis.LocationManager;
import com.google.common.collect.Lists;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Logs;

import java.util.List;


@IocBean
public class LocationGrap  {


 @Inject
    LocationManager locationManager;


    @Inject
    private Dao dao;

    @GraphQLQuery
    public Location location(@GraphQLContext Device device) {

        return location(device.getLocationid());
    }

    @GraphQLQuery
    public List<Location> locations(@GraphQLContext Device device) {
        List<Location> locations =  locationManager.allFamilyWithMe(device.getLocationid());
        Logs.get().debug(locations);
        if(Lang.isNotEmpty(locations)){
            return locations;
        }
        Location location = dao.fetch(Location.class,device.getLocationid());
        String ancestors =location.getAncestors();
        String[] array = Strings.splitIgnoreBlank(ancestors);
        List<String> family = Lists.newArrayList(array);
        family.add(device.getLocationid());
        return dao.query(Location.class,Cnd.where("id","in",family));


    }
    @GraphQLQuery
    public Location location(@GraphQLArgument(name="id") String id) {
        if(Strings.isBlank(id)){
            return null;
        }
        Location location =  locationManager.get(id);
        if(Lang.isNotEmpty(location)){
            return location;
        }
        return dao.fetch(Location.class,id);
    }


}