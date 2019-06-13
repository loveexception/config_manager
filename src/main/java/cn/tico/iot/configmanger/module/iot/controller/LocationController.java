package cn.tico.iot.configmanger.module.sys.controllers;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.bean.Amap;
import cn.tico.iot.configmanger.common.bean.Districts;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.sys.models.Area;
import cn.tico.iot.configmanger.module.sys.services.AreaService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * 区域 信息操作处理
 *
 * @author maodajun
 * @date 2019-04-11
 */
@IocBean
@At("/sys/location")
public class LocationController {
    private static final Log log = Logs.get();

    public static List<Area> areaList = new ArrayList<>();

    @Inject
    private AreaService areaService;

    @RequiresPermissions("sys:location:view")
    @At("")
    @Ok("th:/sys/location/location.html")
    public void index(HttpServletRequest req) {

    }

    /**
     * 查询区域列表
     */
   // @RequiresPermissions("sys:location:list")
    @At
    @Ok("json")
    public Object list(@Param("name") String name, HttpServletRequest req) {
        Cnd cnd = Cnd.NEW();
//        if (!Strings.isBlank(name)) {
//            cnd.and("name", "like", "%" + name +"%");
//        }
        cnd.asc("adcode");
        return areaService.query(cnd);
    }

    /**
     * 新增区域
     */
    @At({"/add","/add/*"})
    @Ok("th:/sys/area/add.html")
    public void add(@Param("id") String id, HttpServletRequest req) {
        Area area = null;
        if (Strings.isNotBlank(id)) {
            area = areaService.fetch(id);
        }
        if (area ==null) {
            area =new Area();
            area.setParentId("0");
            area.setName("无");
        }
        req.setAttribute("area", area);
    }

    /**
     * 新增保存区域
     */
    //@RequiresPermissions("sys:location:add")
    @At
    @POST
    @Ok("json")
    @Slog(tag="区域", after="新增保存区域id=${args[0].id}")
    public Object addDo(@Param("..") Area area, HttpServletRequest req) {
        try {
            areaService.insert(area);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 修改区域
     */
    @At("/edit/?")
    @Ok("th://sys/location/edit.html")
    public void edit(String id, HttpServletRequest req) {
        Area area = areaService.fetch(id);
        if (area != null) {
            Area parentData = areaService.fetch(area.getParentId());
            if (parentData != null) {
                area.setParentName(parentData.getName());
            }
        }
        req.setAttribute("area", area);
    }

    /**
     * 修改保存区域
     */
    //@RequiresPermissions("sys:location:edit")
    @At
    @POST
    @Ok("json")
    @Slog(tag="区域", after="修改保存区域")
    public Object editDo(@Param("..") Area area, HttpServletRequest req) {
        try {
            if(Lang.isNotEmpty(area)){
                area.setUpdateBy(ShiroUtils.getSysUserId());
                area.setUpdateTime(new Date());
                areaService.update(area);
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
    @RequiresPermissions("sys:location:remove")
    @Slog(tag ="区域", after= "删除区域:${args[0]}")
    public Object remove(String id, HttpServletRequest req) {
        try {
            areaService.delete(id);
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    /**
     * 选择菜单树
     */
    @At("/selectTree/?")
    @Ok("th:/sys/area/tree.html")
    public void selectTree(String id, HttpServletRequest req) {
        Area area = null;
        if (Strings.isNotBlank(id)) {
            area = areaService.fetch(id);
        }
        if (area ==null) {
            area =new Area();
            area.setParentId("0");
            area.setName("无");
        }
        req.setAttribute("area", area);
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
        List<Map<String, Object>> tree = areaService.selectTree(parentId, name);
        return tree;
    }

    public static void getAreaList(List<Districts> list, String pid){
        list.forEach(districts -> {
            Area area =new Area();
            area.setId(R.UU32().toLowerCase());
            area.setParentId(pid);
            area.setAdcode(districts.getAdcode());
            area.setName(districts.getName());
            area.setLevel(districts.getLevel());
            if(districts.getCitycode()!=null && districts.getCitycode().size()>0){
                area.setCitycode(districts.getCitycode().get(0));
            }

            areaList.add(area);
            if(districts.getDistricts()!=null && districts.getDistricts().size()>0){
                getAreaList(districts.getDistricts(),area.getId());
            }
        });
    }

    @At
    @Ok("json")
    public String  initData(){
        //读取文件
        String fileName = "/Users/apple/Desktop/area.txt";
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String data = new String(sb); //StringBuffer ==> String
        Amap amap = JSON.parseObject(data,Amap.class);
        if(amap!=null && amap.getDistricts()!=null && amap.getDistricts().size()>0){
            getAreaList(amap.getDistricts(),"0");
        }
//        if(areaList!=null && areaList.size()>0){
//            areaList.forEach(area -> {
//                areaService.insert(area);
//            });
//        }
        return "successs";

    }
}
