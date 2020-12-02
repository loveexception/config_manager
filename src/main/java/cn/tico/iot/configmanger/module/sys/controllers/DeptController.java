package cn.tico.iot.configmanger.module.sys.controllers;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.Role;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
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
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 部门控制类
 *
 * @author haiming
 */
@IocBean
@At("/sys/dept")
public class DeptController {
    private static final Log log = Logs.get();

    @Inject
    DeptService deptService;

    @Inject
    UserService userService;

    @At("")
    @Ok("th:/sys/dept/dept.html")
    @RequiresPermissions("sys:dept:view")
    public void index(HttpServletRequest req) {

    }

//    @At
//    @Ok("json")
//    public Object listBack(@Param("deptName") String deptName, HttpServletRequest req) {
//        Cnd cnd = Cnd.NEW();
//        if (Strings.isNotBlank(deptName)) {
//            cnd.and("dept_name", "like", "%" + deptName + "%");
//        }
//        cnd.and("del_flag", "=", false);
//        return deptService.query(cnd);
//    }
    @At
    @Ok("json")
    public Object list(@Param("deptName") String deptName
            ,@Param("status")String status
            , HttpServletRequest req) {

        Cnd cnd = Cnd.NEW();
        if (Strings.isNotBlank(deptName)) {
            cnd.and("dept_name", "like", "%" + deptName + "%");
        }
        if(Strings.isNotBlank(status)){
            cnd.and("status","=",status);
        }
        User user = ShiroUtils.getSysUser();

        user =userService.fetchLinks(user,"dept|image");
        Set roles = userService.getRoleCodeList(user);
        cnd.and("del_flag", "=", false);

        if(roles.contains("admin")){
             List list = deptService.query(cnd);
             return list;
        }else{
            cnd.and("ancestors","like","%"+user.getDeptId()+"%");
            List list = deptService.query(cnd);
            return list;
        }



    }


    @At("/add/?")
    @Ok("th:/sys/dept/add.html")
    public void add(@Param("id") String id, HttpServletRequest req) {
        Dept data = null;
        if (Strings.isNotBlank(id)) {
            data = deptService.fetch(id);
        }
        if (data == null) {
            data = new Dept();
            data.setId("0");
            data.setDeptName("无");
        }
        req.setAttribute("dept", data);
    }

    @At
    @POST
    @Ok("json")
    @RequiresPermissions("sys:dept:add")
    @Slog(tag = "部门管理", after = " 新增部门id=${args[0].id}")
    public Object addDo(@Param("..") Dept data, @Param("parentId") String parentId, HttpServletRequest req) {
        try {
            deptService.insertDept(data);
            return Result.success("system.success", data);
        } catch (Exception e) {
            if (Lang.isNotEmpty(e) && Strings.isNotBlank(e.getMessage())) {
                return Result.error(e.getMessage());
            }
            return Result.error("system.error");
        }
    }

    @At("/edit/?")
    @Ok("th:/sys/dept/edit.html")
    public void edit(String id, HttpServletRequest req) {
        Dept data = deptService.fetch(id);
        if (data != null) {
            Dept parentData = deptService.fetch(data.getParentId());
            if (parentData != null) {
                data.setParentName(parentData.getDeptName());
            }

            req.setAttribute("dept", data);
        }
    }

    @At
    @POST
    @Ok("json")
    @RequiresPermissions("sys:dept:edit")
    @Slog(tag = "部门管理", after = "修改部门")
    public Object editDo(@Param("..") Dept data, HttpServletRequest req) {
        try {
            deptService.update(data);
            return Result.success("system.success");
        } catch (Exception e) {
            if (Lang.isNotEmpty(e) && Strings.isNotBlank(e.getMessage())) {
                return Result.error(e.getMessage());
            }
            return Result.error("system.error");
        }
    }

    @At("/remove/?")
    @Ok("json")
    @RequiresPermissions("sys:dept:remove")
    @Slog(tag = "删除单位", after = "删除部门:${args[0]}")
    public Object remove(String id, HttpServletRequest req) {
        try {
            deptService.vDelete(id);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }


    /**
     * 选择菜单树
     */
    @At("/selectTree/?")
    @Ok("th:/sys/dept/tree.html")
    public void selectTree(String id, HttpServletRequest req) {
        req.setAttribute("dept", deptService.fetch(id));
    }

    @At
    @Ok("json")
    public List<Map<String, Object>> treeData(@Param("parentId") String parentId,
                                              @Param("deptName") String deptName) {
        List<Map<String, Object>> tree = deptService.selectTree(parentId, deptName);
        return tree;
    }
    @At
    @Ok("json")
    public List<Map<String, Object>> treeDataOrg(@Param("parentId") String parentId,
                                              @Param("deptName") String deptName) {
        User user = ShiroUtils.getSysUser();

        user =userService.fetchLinks(user,"dept|image");
        Set roles = userService.getRoleCodeList(user);

        if(roles.contains("admin")){
            List<Map<String, Object>> tree = deptService.selectFathers("100", null);
            return tree;
        }else{
            List<Map<String, Object>> tree = deptService.selectFathers(user.getDeptId() , user.getDept().getDeptName());
            return tree;
        }


    }

    @At("/tree_list")
    @Ok("json")
    public Object  treeList(@Param("parentId") String parentId,
                                                 @Param("deptName") String deptName) {
        User user = ShiroUtils.getSysUser();

        user =userService.fetchLinks(user,"dept|image");
        Set roles = userService.getRoleCodeList(user);

        if(roles.contains("admin")){
            List<Map<String, Object>> tree = deptService.selectFathers("100", null);
            return Result.success("system.success",tree);
        }else{
            List<Map<String, Object>> tree = deptService.selectFathers("100" , user.getDept().getDeptName());
            return  Result.success("system.success",tree);
        }


    }

    @At
    @POST
    @Ok("json")
    public boolean checkDeptNameUnique(@Param("id") String id,
                                       @Param("parentId") String parentId,
                                       @Param("name") String menuName) {
        return deptService.checkDeptNameUnique(id, parentId, menuName);
    }

}
