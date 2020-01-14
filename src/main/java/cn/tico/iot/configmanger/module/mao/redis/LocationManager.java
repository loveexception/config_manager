package cn.tico.iot.configmanger.module.mao.redis;

import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;

@IocBean
public class LocationManager {
    @Inject
    PropertiesProxy conf;
    @Inject
    LocationService locationService;

     String KEY_PATH  ;
    static final String REG_STAT = ".*(";
    static final String REG_END = ").*";
    static final String SPLIT =":";


    @Aop("redis")
    public void init(){
        Logs.get().debugf("location redis path : %s ",KEY_PATH);

        KEY_PATH = conf.get("redis.pre.key.location");

        Set<String> keys= jedis().keys(KEY_PATH+"*");
        Logs.get().debugf("keys",keys);
        keys.stream().forEach(key->jedis().del(key));

        List <Location> all = locationService.findAllLocations();
        List<Location> copy = Lists.newArrayList(all);
        all = all.stream()
                .map(location -> findAncestors(location,copy))
                .map(location -> fatherName(location))
                .collect(Collectors.toList());
        all = children(all);

        all.forEach(location -> jedis().set(KEY_PATH+location.getId(),Json.toJson(location)));

    }

    /**
     * 补完所有的祖节点
     * @param me
     * @param all
     * @return
     */
    public  Location findAncestors(Location me ,List<Location> all) {
        List<Location> list = getAncestorsLocationsByMe(me,all);
        Map<String,Location> parents = list
               .stream()
               .collect(Collectors.toMap(
                       location-> location.getLevel(),
                       location->location)
               );
//               all
//               .stream()
//               .filter(location -> me.getAncestors().indexOf(location.getId())>=0 )
//               .collect(Collectors.toMap(location -> location.getLevel(),location->location));

       me.setParents(parents);
       return me;
    }

    /**
     * 补完所有的子节点
     * @param all
     * @return
     */
    public List<Location> children(List<Location> all){
        Map<String,Location > map =  all.stream().collect(Collectors.toMap(location->location.getId(),location->location));
        List<Location> result =  all.stream().map(location ->{
            String id = location.getParentId();
            if(Strings.isBlank(id)||Strings.equals("0",id)){
                return location;
            }
            Location father = map.get(id);
            if(Lang.isEmpty(father.getChildren())){
                father.setChildren(Lists.newArrayList());
            }
            father.getChildren().add(location);
        return  location;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * 生成树的根数据
     * @param id
     * @param all
     * @return
     */
    public Location zip(String id ,List<Location> all){
        List<Location> root = children (all);
        return root.stream()
                .filter(location -> Strings.equals(id,location.getId()))
                .findFirst()
                .get();

    }

    /**
     * 生成单独数据
     * @param id
     * @return
     */
    @Aop("redis")
    public Location get(String id){
        String json = jedis().get(KEY_PATH+id);
        if(Strings.isBlank(json)){
            return null;
        }

        return Json.fromJson(Location.class,json);
    }

    /**
     * 查寻所有已知的KEY
     * @return
     */
    @Aop("redis")
    public Set<String> keys(){
        Set<String> set =  jedis().keys(KEY_PATH+"*");
        if(Lang.isEmpty(set)){
            return Sets.newHashSet();
        }
        return set.stream().map(key-> Strings.replaceBy(key, NutMap.NEW().addv(KEY_PATH,""))).collect(Collectors.toSet());
    }

    public Location byName(String longSplitName,List<Location> all){
        String[] array =Strings.splitIgnoreBlank(longSplitName,SPLIT);
        List<String> names = Lang.array2list(array);
        String last = Lists.newLinkedList(names).getLast();


          List<Location> ones = all.stream()
                  .filter(one-> isMatchByKey(last, one.getCnName()))
                  .filter(one ->{

                      String langName = Strings.sBlank(one.getParentName(),"")+SPLIT+one.getCnName();
                      boolean b =  names.stream().allMatch(name -> isMatchByKey(name, langName));
                        return b;
                        }).collect(Collectors.toList());
          if(Lang.isEmpty(ones)){
              return null;
          }
          if(ones.size()!=1){
              return null;

          }
          return ones.get(0);



        //return ones.get(0);

    }

    public static boolean isMatchByKey(String last, String cnName) {
        return Strings.isMatch(Pattern.compile(REG_STAT + last + REG_END), cnName);
    }

    /**
     * 所有的地理位制
     * @return
     */
    public List<Location> all() {
        Set<String> keys = keys();
        return keys.stream().map(key-> get(key)).collect(Collectors.toList());
    }

    /**
     * 靠父目录数据补完
     * fatherName
     * 字段
     * @param location
     */
    public Location fatherName(Location location) {
        Map<String,Object> map = location.getParents();
        if(Lang.isEmpty(map)){
            return location;
        }
        List<String> list  = map.values()
                        .stream()
                        .map(value->Lang.obj2nutmap(value).getString("cnName"))
                        .collect(Collectors.toList());
        String fatherName = Strings.join(SPLIT,list);
        location.setParentName(fatherName);
        return location;
    }


    /**
     * 跟据 LOCATION 得到所有的 Location 父数据
     * @param locationId
     * @return
     */
    public List<Location> getAllByLocation(String locationId) {
        Location me = get(locationId);
        List<Location> result = getAncestorsLocationsByMe(me,all());
        result.add(me);
        return result ;
    }

    public List<Location> getAncestorsLocationsByMe(Location me,List<Location> all) {
        return all
                    .stream()
                    .filter(location ->isMatchByKey(
                            location.getId()
                            ,me.getAncestors()))

                    .collect(Collectors.toList());
    }
}
