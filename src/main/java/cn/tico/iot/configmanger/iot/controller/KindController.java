package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.base.Kind;
import cn.tico.iot.configmanger.iot.models.base.Location;
import cn.tico.iot.configmanger.iot.services.KindService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 区域 信息操作处理
 *
 * @author maodajun
 * @date 2019-04-11
 */
@IocBean
@At("/iot/kind")
public class KindController  implements AdminKey {
    private static final Log log = Logs.get();

    public static List<Kind> areaList = new ArrayList<>();

    @Inject
    private KindService kindService;
    @Inject
    private DeptService deptService;
    @Inject
    private UserService userService;

    @RequiresPermissions("iot:kind:view")
    @At("")
    @Ok("th:/iot/kind/list.html")
    public void index(HttpServletRequest req) {
        User user = ShiroUtils.getSysUser();
        Dept dept = deptService.fetch(user.getDeptId());

        req.setAttribute("dept",dept);


    }

    /**
     * 查询区域列表
     */
    @RequiresPermissions("iot:kind:list")
    @At
    @Ok("json")
    public Object list(@Param("cnName") String name, @Param("status") String status ,HttpServletRequest req) {
        Cnd cnd = Cnd.NEW();
        if (!Strings.isBlank(name)) {
            SqlExpressionGroup
                    group = Cnd
                    .exps("cn_name", "like", "%" + name + "%")
                    .or("en_name", "like", "%" + name + "%");
            cnd.and(group);
        }
        if(!Strings.isBlank(status)){
            cnd.and("status","=",status);
        }

        if(!isAdmin()){
            SqlExpressionGroup
                    group = Cnd
                    .exps("dept_id", "=", DEPT_ADMIN)
                    .or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
            cnd.and(group);
        }


        return kindService.query(cnd);
    }

    /**
     * 用户权限
     * @return
     */
    private boolean isAdmin() {

        User user = ShiroUtils.getSysUser();

        Set roles = userService.getRoleCodeList(user);

        return roles.contains(ROLE_ADMIN);
    }

    /**
     * 新增
     */
    @At({"/add","/add/*"})
    @Ok("th:/iot/kind/add.html")
    public void add(@Param("id") String id, HttpServletRequest req) {
        Kind kind = null;
        if (Strings.isNotBlank(id)) {
            kind = kindService.fetch(id);
        }
        if (kind ==null) {
            kind =new Kind();
            kind.setParentId(id);


        }
        req.setAttribute("kind", kind);
    }

    /**
     * 新增保存
     */
    @RequiresPermissions("iot:kind:add")
    @At
    @POST
    @Ok("json")
    @Slog(tag="区域", after="新增保存区域id=${kind[0].id}")
    public Object addDo(@Param("..") Kind kind, HttpServletRequest req) {
        try {
            kindService.insertKind(kind);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 修改
     */
    @At("/edit/?")
    @Ok("th://iot/kind/edit.html")
    public void edit(String id, HttpServletRequest req) {
        Kind kind = kindService.fetch(id);
        if (kind != null) {
            Kind parentData = kindService.fetch(kind.getParentId());
            kind.setParentName(parentData.getCnName());

        }
        req.setAttribute("kind", kind);
    }

    /**
     * 修改保存类型
     */
    @RequiresPermissions("iot:kind:edit")
    @At
    @POST
    @Ok("json")
    @Slog(tag="区域", after="修改保存区域")
    public Object editDo(@Param("..") Kind kind, HttpServletRequest req) {
        try {
            if(Lang.isNotEmpty(kind)){

                kindService.update(kind);
            }

            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 删除类型
     */
    @At("/remove/?")
    @Ok("json")
    @RequiresPermissions("iot:kind:remove")
    @Slog(tag ="区域", after= "删除区域:${args[0]}")
    public Object remove(String id, HttpServletRequest req) {
        try {
            kindService.delete(id);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 选择菜单树
     */
    @At("/selectTree/?")
    @Ok("th:/iot/kind/tree.html")
    public void selectTree(String id, HttpServletRequest req) {
        Kind kind = null;
        if (Strings.isNotBlank(id)) {
            kind = kindService.fetch(id);
        }
        if (kind ==null) {
            kind =new Kind();
            kind.setParentId(KIND_ROOT);
            kind.setCnName("");
        }
        req.setAttribute("kind", kind);
    }

    /**
     * 获取树数据
     *
     * @param parentId
     * @param name
     * @return
     */
    @At
    @Ok("json")
    public List<Map<String, Object>> treeData(@Param("parentId") String parentId,
                                              @Param("name") String name) {
        List<Map<String, Object>> tree = kindService.selectTree(parentId, name);
        return tree;
    }



    @At
    @Ok("json")
    public Object  treeObject(@Param("level") String level) {
        Cnd cnd = Cnd.NEW();
        if(Strings.isNotBlank(level)){
            cnd.and("level","<=",level);
        }
        List<Kind> locations =  kindService.query(cnd);
        Kind root = kindService.zip(locations);




        String json = new Gson().toJson(root);
        Object obj  = Json.fromJson(json);
        return Result.success("system.success",obj);
    }


    /**
     * 选择菜单树
     */
    @At("/select_parent")
    @Ok("json")
    public Object selectParent(@Param("id") String id, HttpServletRequest req) {

        List<Kind> obj = kindService.selectParents(id);

       return Result.success("system.success",obj);
    }

//    @At
//    @Ok("json")
//    public List<Map<String, Object>> treeData(@Param("parentId") String parentId,
//                                              @Param("deptName") String deptName) {
//        List<Map<String, Object>> tree = deptService.selectTree(parentId, deptName);
//        return tree;
//    }
//    @At
//    @Ok("json")
//    public List<Map<String, Object>> treeDataOrg(@Param("parentId") String parentId,
//                                                 @Param("deptName") String deptName) {
//        User user = ShiroUtils.getSysUser();
//
//        user =userService.fetchLinks(user,"dept|image");
//        Set roles = userService.getRoleCodeList(user);
//
//        if(roles.contains("admin")){
//            List<Map<String, Object>> tree = deptService.selectFathers("100", null);
//            return tree;
//        }else{
//            List<Map<String, Object>> tree = deptService.selectFathers("100" , user.getDept().getDeptName());
//            return tree;
//        }
//
//
//    }

}
