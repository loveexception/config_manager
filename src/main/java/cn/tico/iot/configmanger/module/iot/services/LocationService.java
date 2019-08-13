package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.sys.models.User;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.util.*;

/**
 * 地址service
 * @author haiming
 */
@IocBean(args = {"refer:dao"})
public class LocationService extends Service<Location> {

    public LocationService(Dao dao) {
        super(dao);
    }

    /**
     * 对象转树表
     *
     * @param locations 列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Location> locations) {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Location location : locations) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("id", location.getId());
                dataMap.put("pId", location.getParentId());
                dataMap.put("name",location.getCnName());
                dataMap.put("title",location.getEnName());

                dataMap.put("checked", false);

                trees.add(dataMap);
        }
        return trees;
    }

    /**
     * 查询数据树
     * @param parentId
     * @param name
     * @return
     */
    public List<Map<String, Object>> selectTree(String parentId, String name) {
        Cnd cnd = Cnd.NEW();
        if (Strings.isNotBlank(name)) {
            SqlExpressionGroup group = Cnd
                    .exps("cn_name", "like", "%" + name + "%")
                    .or("en_name", "like", "%" + name + "%");
           cnd.and(group);
        }
        if (Strings.isNotBlank(parentId)) {
            cnd.and("parent_id", "=", parentId);
        }
        cnd.and("status", "=", "true");//
        List<Location> deptList = this.query(cnd);
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        trees = getTrees(deptList);
        return trees;
    }
    /**
     * 查询某节点前的所有节点
     * @param parentId
     * @param name
     * @return
     */
    public List<Map<String, Object>> selectFathers(String parentId, String name) {
        Cnd cnd = Cnd.NEW();

        if (Strings.isNotBlank(name)) {
            SqlExpressionGroup group = Cnd
                    .exps("cn_name", "like", "%" + name + "%")
                    .or("en_name", "like", "%" + name + "%");
            cnd.and(group);
        }
        if (Strings.isNotBlank(parentId)) {
            SqlExpressionGroup group = Cnd
                    .exps("id", "=", parentId)
                    .or("parent_id", "=", parentId);
            cnd.and(group);
        }
        cnd.and("status", "=", "true");//

        List<Location> locations = this.query(cnd);
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        trees = getTrees(locations);
        return trees;
    }
    public Location insertLocation(Location location) throws Exception {
        Location info = this.fetch(location.getParentId());
        location.setAncestors(info.getAncestors() + "," + location.getParentId());
        location.setLevel( ""+(Lang.str2number(info.getLevel()).intValue()+1));
        User user =  ShiroUtils.getSysUser();
        location.setDeptid(user.getDeptId());

        location.setCreateBy(ShiroUtils.getSysUserId());
        location.setCreateTime(new Date());
        return this.dao().insert(location);
    }

    public int update(Location location) throws Exception {


        // Location info = this.fetch(location.getParentId());
        location.setAncestors(null);
        location.setLevel(null);
        location.setDeptid(null);
        location.setParentId(null);
        location.setUpdateBy(ShiroUtils.getSysUserId());
        location.setUpdateTime(new Date());

         Dao forup = Daos.ext(this.dao(), FieldFilter.create(location.getClass(), true));
         return forup.update(location);
    }

    public boolean checkLocationNameUnique(String id,String parentId,String cn_name,
                                           String en_name) {
        Cnd cnd =Cnd.NEW();
        if(Strings.isNotBlank(id)){
            cnd.and("id","!=",id);
        }
        if(Strings.isNotBlank(parentId)){
            cnd.and("parent_id","=",parentId);
        }
        if(Strings.isNotBlank(cn_name)){
            cnd.and("cn_name", "=", cn_name);
        }
        List<Location> list = this.query(cnd);
        if (Lang.isEmpty(list)) {
            return true;
        }
        return false;
    }


    public Location zip(List<Location> locations) {
        Map<String,Location> map = new LinkedHashMap<>();
        Location root = null;
        for(Location location : locations){
            map.put(location.getId(),location);
        }
        for (Location location : locations){
           Location father =  map.get(location.getParentId());
           if(father==null){
               root = location;
           }else {
               List<Location> list = father.getChildren();
               if(list==null){
                   list = new ArrayList<Location>();
               }
               list.add(location);
               father.setChildren(list);

           }
           map.put(location.getParentId(),father);
        }


        return root;
    }
    public List<Location> selectParents(String id,int level) {
        Location location = fetch(id);
        if(Lang.isEmpty(location)){
            return Lists.newArrayList();
        }
        String fathers = location.getAncestors();
        List<Location> result = new ArrayList();
        for(String parent : fathers.split(",")){
            Location temp = fetch(parent);
            if(temp!=null&&Lang.str2number(temp.getLevel()).intValue()>level) {
                result.add(temp);
            }
        }
        return result;
    }
}
