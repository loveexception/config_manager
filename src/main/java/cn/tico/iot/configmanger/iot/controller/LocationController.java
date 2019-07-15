package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.iot.models.base.Location;
import cn.tico.iot.configmanger.iot.services.LocationService;
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
import java.util.*;


/**
 * 区域 信息操作处理
 *
 * @author maodajun
 * @date 2019-04-11
 */
@IocBean
@At("/iot/location")
public class LocationController implements AdminKey {
    private static final Log log = Logs.get();

    public static List<Location> areaList = new ArrayList<>();

    @Inject
    private LocationService locationService;
    @Inject
    private DeptService deptService;
    @Inject
    private UserService userService;

    @RequiresPermissions("iot:location:view")
    @At("")
    @Ok("th:/iot/location/location.html")
    public void index(HttpServletRequest req) {
        User user = ShiroUtils.getSysUser();
        Dept dept = deptService.fetch(user.getDeptId());

        req.setAttribute("dept",dept);


    }

    /**
     * 查询区域列表
     */
    @RequiresPermissions("iot:location:list")
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


        return locationService.query(cnd);
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
     * 新增区域
     */
    @At({"/add","/add/*"})
    @Ok("th:/iot/location/add.html")
    public void add(@Param("id") String id, HttpServletRequest req) {
        Location location = null;
        if (Strings.isNotBlank(id)) {
            location = locationService.fetch(id);
        }
        if (location ==null) {
            location =new Location();
            location.setParentId(id);
            location.setLat(0);
            location.setLng(0);

        }
        req.setAttribute("location", location);
    }

    /**
     * 新增保存区域
     */
    @RequiresPermissions("iot:location:add")
    @At
    @POST
    @Ok("json")
    @Slog(tag="区域", after="新增保存区域id=${location[0].id}")
    public Object addDo(@Param("..") Location location, HttpServletRequest req) {
        try {
            locationService.insertLocation(location);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 修改区域
     */
    @At("/edit/?")
    @Ok("th://iot/location/edit.html")
    public void edit(String id, HttpServletRequest req) {
        Location location = locationService.fetch(id);
        if (location != null) {
            Location parentData = locationService.fetch(location.getParentId());
            Dept dept = deptService.fetch(location.getDeptid());
            if (parentData != null) {
                location.setDeptName(dept.getDeptName());
                location.setParentName(parentData.getCnName());
            }
        }
        req.setAttribute("location", location);
    }

    /**
     * 修改保存区域
     */
    @RequiresPermissions("iot:location:edit")
    @At
    @POST
    @Ok("json")
    @Slog(tag="区域", after="修改保存区域")
    public Object editDo(@Param("..") Location location, HttpServletRequest req) {
        try {
            if(Lang.isNotEmpty(location)){

                locationService.update(location);
            }

            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 删除区域
     */
    @At("/remove/?")
    @Ok("json")
    @RequiresPermissions("iot:location:remove")
    @Slog(tag ="区域", after= "删除区域:${args[0]}")
    public Object remove(String id, HttpServletRequest req) {
        try {
            locationService.delete(id);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 选择菜单树
     */
    @At("/selectTree/?")
    @Ok("th:/iot/location/tree.html")
    public void selectTree(String id, HttpServletRequest req) {
        Location location = null;
        if (Strings.isNotBlank(id)) {
            location = locationService.fetch(id);
        }
        if (location ==null) {
            location =new Location();
            location.setParentId(LOCATION_ROOT);
           location.setCnName("");
        }
        req.setAttribute("location", location);
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
        List<Map<String, Object>> tree = locationService.selectTree(parentId, name);
        return tree;
    }
    @At
    @Ok("json")
    public Object  treeObject(@Param("id") String id) {
        List<Location> locations =  locationService.query();
         Location root = locationService.zip(locations);




        String json = new Gson().toJson(root);
         Object obj  = Json.fromJson(json);
        return Result.success("system.success",obj);
    }

    @At("tree_parent")
    @Ok("json")
    public Object  treeParent(@Param("id") String id) {
        Cnd cnd = Cnd.NEW();

        if(!isAdmin()){
            SqlExpressionGroup
                    group = Cnd
                    .exps("dept_id", "=", DEPT_ADMIN)
                    .or("dept_id", "=", ShiroUtils.getSysUser() .getDeptId());
            cnd.and(group);
        }

        List<Location> locations =  locationService.query(cnd);
        Location root = locationService.zip(locations);

        String json = new Gson().toJson(root);
        Object obj  = Json.fromJson(json);
        return Result.success("system.success",obj);
    }

}
