package cn.tico.iot.configmanger.module.wx.controller;

import cn.tico.iot.configmanger.common.page.TableDataInfo;
import cn.tico.iot.configmanger.common.utils.StringUtils;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Owner;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.module.wx.models.TIotOwner;
import cn.tico.iot.configmanger.module.wx.services.TIotOwnerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.TIotDevices;
import cn.tico.iot.configmanger.module.wx.services.TIotDevicesService;
import cn.tico.iot.configmanger.common.base.Result;;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static cn.tico.iot.configmanger.module.iot.controller.AdminKey.LOCATION_ROOT;

/**
 * 设备资本 信息操作处理
 * 
 * @author maodajun
 * @date 2019-11-07
 */
@IocBean
@At("/wx/tIotDevices")
public class TIotDevicesController {
	private static final Log log = Logs.get();

//	@Inject
//	private TIotDevicesService tIotDevicesService;

	@Inject
	public DeviceService deviceService;
	@Inject
	public KindService kindService;
	@Inject
	public DeptService deptService;
	@Inject
	public TIotOwnerService tIotOwnerService;
	
	@RequiresPermissions("wx:tIotDevices:view")
	@At("")
	@Ok("th:/wx/tIotDevices/tIotDevices.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询设备资本列表
	 */
	@RequiresPermissions("wx:tIotDevices:list")
	@At
	@Ok("json")
	public Object list(@Param("pageNum")int pageNum,
					   @Param("pageSize")int pageSize,
					   @Param("name") String name,
					   @Param("beginTime") Date beginTime,
					   @Param("endTime") Date endTime,
					   @Param("orderByColumn") String orderByColumn,
					   @Param("isAsc") String isAsc,
					   HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)){
			cnd.and("sno", "like", "%" + name +"%");
		}
		if(Lang.isNotEmpty(beginTime)){
			cnd.and("create_time",">=", beginTime);
		}
		if(Lang.isNotEmpty(endTime)){
			cnd.and("create_time","<=", endTime);
		}
		cnd.and("delflag", "=", "false");
		if(Strings.isNotBlank(orderByColumn)){
			cnd.orderBy(orderByColumn,isAsc);
		}else {
			cnd.orderBy("sno", "desc");
		}
		TableDataInfo info  =  deviceService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,"^dept|kind|owner|next$");
		List<Device> list = (List<Device>) info.getRows();
		List<Kind> kinds = kindService.query(Cnd.NEW().and("delflag","=","false").and("level","=","3"));
		for(Device device:list){
			Kind kind = device.getKind();
			if(Lang.isEmpty(kind)){
				continue;
			}
			String fathers = kind.getAncestors();
			if(Strings.isBlank(fathers)){
				continue;
			}
			for (Kind k :kinds){
				if(fathers.contains(k.getId())){
					 device.setKindmap(k.getCnName());
					 continue;
				}
			}
			List<Owner> lists = device.getNext();
			if(Lang.isEmpty(lists)){
				continue;
			}
			TreeSet<Owner> sets = new TreeSet<>();
			sets.addAll(lists);

			Owner last = sets.iterator().next();
			//device.setNext(Lists.newArrayList(last));
			device.setGatewayExtsno(last.getTime());

		}

		return info ;
	}

	/**
	 * 新增设备资本
	 */
	@At("/add")
	@Ok("th:/wx/tIotDevices/add.html")
	public void add( HttpServletRequest req) {

		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept =deptService.fetch(deptid);
		req.setAttribute("dept",dept);



	}

	/**
	 * 新增保存设备资本
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tIotDevices:add")
	@Slog(tag="设备资本", after="新增保存设备资本 id=${args[0].id}")
	public Object addDo(@Param("..") Device tIotDevices,@Param("next.cycle")String cycle,@Param("next.time")String time, HttpServletRequest req) {
		try {
			deviceService.insert(tIotDevices);
			Owner owner = new Owner();
			owner.setCycle(cycle);
			owner.setTime(time);
			owner.setDeviceid(tIotDevices.getId());

			deviceService.dao().insert(owner);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改设备资本
	 */
	@At("/edit/?")
	@Ok("th://wx/tIotDevices/edit.html")
	public void edit(String id, HttpServletRequest req) {
		Device tIotDevices = deviceService.fetch(id);
		deviceService.fetchLinks(tIotDevices,"^dept|next|kind$");

		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept =deptService.fetch(deptid);

		List<Owner> sets = tIotDevices.getNext();
		Owner last = new Owner();
		if(Lang.isEmpty(sets)){
			Owner owner= new Owner();
//			owner.setCycle("365");
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.YEAR,1);
//			String day =DateFormatUtils.format(cal,"yyyy-MM-dd");
//			owner.setTime(day);
			owner.setDeviceid(tIotDevices.getId());
			 last = owner;
		}else{
			last = sets.iterator().next();
		}

		tIotDevices.setNext(Lists.newArrayList(last));


		req.setAttribute("dept",dept);

		req.setAttribute("tIotDevices",tIotDevices);
	}

	/**
	 * 修改保存设备资本
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tIotDevices:edit")
	@Slog(tag="设备资本", after="修改保存设备资本")
	public Object editDo(@Param("..") Device tIotDevices,@Param("next.cycle")String cycle,@Param("next.time")String time, HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(tIotDevices)){

				deviceService.insertUpdate(tIotDevices);

				tIotDevices = deviceService.fetchLinks(tIotDevices,"next");

				List<Owner> owners = tIotDevices.getNext();
				Owner owner = new Owner();

				if(Lang.isEmpty(owners)){
					owner.setCycle(cycle);

					owner.setTime(time);
					owner.setDeviceid(tIotDevices.getId());
					deviceService.dao().insert(owner);

				}else{
					Owner temp = owners.get(0);

					temp.setCycle(cycle);
					temp.setTime(time);

					deviceService.dao().update(temp);

				}

			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除设备资本
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tIotDevices:remove")
	@Slog(tag ="设备资本", after= "删除设备资本:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			deviceService.vDelete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除设备资本
	 */
	@At("/change")
	@Ok("json")
	@Slog(tag ="设备资本", after= "删除设备资本:${array2str(args[0])}")
	public Object change(@Param("id")String ids, HttpServletRequest req) {
		try {
			Device device = deviceService.fetch(ids);
			device = deviceService.fetchLinks(device,"next");
			List<Owner> owners = device.getNext();
			Owner owner = new Owner();

			if(Lang.isEmpty(owners)){
				owner.setCycle("365");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR,1);
				String day =DateFormatUtils.format(cal,"yyyy-MM-dd");
				owner.setTime(day);
				owner.setDeviceid(ids);
				deviceService.dao().insert(owner);

			}else{
				Owner temp = owners.get(0);

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, Integer.parseInt(temp.cycle));
				String day =DateFormatUtils.format(cal,"yyyy-MM-dd");
				temp.setTime(day);
				deviceService.dao().update(owner);

			}





			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

}
