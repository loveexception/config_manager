package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.models.Kind;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
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
 * 类型service
 * @author haiming
 */
@IocBean(args = {"refer:dao"})
public class KindService extends Service<Kind> {

    public KindService(Dao dao) {
        super(dao);
    }

    /**
     * 对象转树表
     *
     * @param kinds 列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Kind> kinds) {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Kind kind : kinds) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("id", kind.getId());
            dataMap.put("pId", kind.getParentId());
            dataMap.put("name",kind.getCnName());
            dataMap.put("title",kind.getEnName());

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
        List<Kind> deptList = this.query(cnd);
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

        List<Kind> kinds = this.query(cnd);
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        trees = getTrees(kinds);
        return trees;
    }
    public Kind insertKind(Kind kind) throws Exception {
        Kind info = this.fetch(kind.getParentId());
        if(info !=  null){
            kind.setAncestors(info.getAncestors() + "," + kind.getParentId());
            kind.setLevel( ""+(Lang.str2number(info.getLevel()).intValue()+1));
        }else{
            kind.setParentId("0");
            kind.setAncestors("0");
            kind.setLevel("1");
            kind.setDelFlag("0");
        }

        User user =  ShiroUtils.getSysUser();

        kind.setCreateBy(ShiroUtils.getSysUserId());
        kind.setCreateTime(new Date());
        return this.dao().insert(kind);
    }

    public int update(Kind kind) throws Exception {


        Kind info = this.fetch(kind.getParentId());
        kind.setAncestors(info.getAncestors() + "," + kind.getParentId());
        kind.setLevel( ""+(Lang.str2number(info.getLevel()).intValue()+1));
        kind.setUpdateBy(ShiroUtils.getSysUserId());
        kind.setUpdateTime(new Date());

        Dao forup = Daos.ext(this.dao(), FieldFilter.create(kind.getClass(), true));
        return forup.update(kind);
    }

    public boolean checkKindNameUnique(String id,String parentId,String cn_name,
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
        List<Kind> list = this.query(cnd);
        if (Lang.isEmpty(list)) {
            return true;
        }
        return false;
    }


    public boolean checkDeptNameUnique(String id, String parentId, String menuName) {
        Cnd cnd =Cnd.NEW();
        if(Strings.isNotBlank(id)){
            cnd.and("id","!=",id);
        }
        if(Strings.isNotBlank(parentId)){
            cnd.and("parent_id","=",parentId);
        }
        if(Strings.isNotBlank(menuName)){
            cnd.and("cn_name", "=", menuName);
        }
        List<Kind> list = this.query(cnd);
        if (Lang.isEmpty(list)) {
            return true;
        }
        return false;
    }
}
