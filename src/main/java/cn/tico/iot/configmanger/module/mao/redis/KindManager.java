package cn.tico.iot.configmanger.module.mao.redis;

import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.mao.common.BaseManager;
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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;

@IocBean(create = "init")
public class KindManager extends BaseManager {
    @Inject
    PropertiesProxy conf;
    @Inject
    KindService kindService;

     String KEY_PATH  ;
    static final String REG_STAT = ".*(";
    static final String REG_END = ").*";
    static final String SPLIT =":";


    @Aop("redis")
    public void init(){
        Logs.get().debugf("location redis path : %s ",KEY_PATH);

        KEY_PATH = conf.get("redis.pre.key.kind");

        Set<String> keys= jedis().keys(KEY_PATH+"*");
        Logs.get().debugf("keys",keys);
        keys.stream().forEach(key->jedis().del(key));

        List <Kind> all = kindService.findAllKinds();
        List<Kind> copy = Lists.newArrayList(all);
        all = all.stream()
                .map(kind -> findAncestors(kind,copy))
                .map(kind -> fatherName(kind))
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
    public  Kind findAncestors(Kind me ,List<Kind> all) {
        List<Kind> list = getAncestorsLocationsByMe(me,all);
        Map<String,Kind> parents = list
               .stream()
               .collect(Collectors.toMap(
                       kind-> kind.getLevel(),
                       kind->kind)
               );

       me.setParents(parents);
       return me;
    }

    /**
     * 补完所有的子节点
     * @param all
     * @return
     */
    public List<Kind> children(List<Kind> all){
        Map<String,Kind > map =  all.stream().collect(Collectors.toMap(kind->kind.getId(),kind->kind));
        List<Kind> result =  all.stream().map(kind ->{
            String id = kind.getParentId();
            if(Strings.isBlank(id)||Strings.equals("0",id)){
                return kind;
            }
            Kind father = map.get(id);
            if(Lang.isEmpty(father.getChildren())){
                father.setChildren(Lists.newArrayList());
            }
            father.getChildren().add(kind);
        return  kind;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * 生成树的根数据
     * @param id
     * @param all
     * @return
     */
    public Kind zip(String id ,List<Kind> all){
        List<Kind> root = children (all);
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
    public Kind get(String id){
        String json = jedis().get(KEY_PATH+id);
        if(Strings.isBlank(json)){
            return null;
        }

        return Json.fromJson(Kind.class,json);
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

    public Kind byName(String longSplitName,List<Kind> all){
        String[] array =Strings.splitIgnoreBlank(longSplitName,SPLIT);
        List<String> names = Lang.array2list(array);
        String last = Lists.newLinkedList(names).getLast();


          List<Kind> ones = all.stream()
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
    public List<Kind> all() {
        Set<String> keys = keys();
        return keys.stream().map(key-> get(key)).collect(Collectors.toList());
    }

    /**
     * 靠父目录数据补完
     * fatherName
     * 字段
     * @param kind
     */
    public Kind fatherName(Kind kind) {

        Map<String,Object> map = kind.getParents();
        if(Lang.isEmpty(map)){
            return kind;
        }

        List<String> list  = map.values()
                        .stream()
                        .map(value->Lang.obj2nutmap(value).getString("cnName"))
                        .collect(Collectors.toList());
        String fatherName = Strings.join(SPLIT,list);

        kind.setParentName(fatherName);

        return kind;
    }


    /**
     * 跟据 LOCATION 得到所有的 Location 父数据
     * @param kindId
     * @return
     */
    public List<Kind> allFamilyWithMe(String kindId) {
        Kind me = get(kindId);
        List<Kind> result = getAncestorsLocationsByMe(me,all());
        result.add(me);
        return result ;
    }

    public List<Kind> getAncestorsLocationsByMe(Kind me,List<Kind> all) {
        return all
                    .stream()
                    .filter(kind ->isMatchByKey(
                            kind.getId()
                            ,me.getAncestors()))

                    .collect(Collectors.toList());
    }
}
