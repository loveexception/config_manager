package cn.tico.iot.configmanger.module.iot.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.iot.models.Location;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
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
import java.util.*;


/**
 * 区域 信息操作处理
 *
 * @author maodajun
 * @date 2019-04-11
 */
@IocBean
@At("/iot/location")
public class LocationController {
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
                    .exps("dept_id", "=", "100")
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

        return roles.contains("admin");
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
            location.setParentId("100000");
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


//    public static void getAreaList(List<Districts> list, String pid){
//        list.forEach(districts -> {
//            Area area =new Area();
//            area.setId(R.UU32().toLowerCase());
//            area.setParentId(pid);
//            area.setAdcode(districts.getAdcode());
//            area.setName(districts.getName());
//            area.setLevel(districts.getLevel());
//            if(districts.getCitycode()!=null && districts.getCitycode().size()>0){
//                area.setCitycode(districts.getCitycode().get(0));
//            }
//
//            areaList.add(area);
//            if(districts.getDistricts()!=null && districts.getDistricts().size()>0){
//                getAreaList(districts.getDistricts(),area.getId());
//            }
//        });
//    }
//
//    @At
//    @Ok("json")
//    public String  initData(){
//        //读取文件
//        String fileName = "/Users/apple/Desktop/area.txt";
//        //读取文件
//        BufferedReader br = null;
//        StringBuffer sb = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8")); //这里可以控制编码
//            sb = new StringBuffer();
//            String line = null;
//            while((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                br.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        String data = new String(sb); //StringBuffer ==> String
//        Amap amap = JSON.parseObject(data,Amap.class);
//        if(amap!=null && amap.getDistricts()!=null && amap.getDistricts().size()>0){
//            getAreaList(amap.getDistricts(),"0");
//        }
//        if(areaList!=null && areaList.size()>0){
//            areaList.forEach(area -> {
//                areaService.insert(area);
//            });
//        }
//        return "successs";
//
//    }
}
