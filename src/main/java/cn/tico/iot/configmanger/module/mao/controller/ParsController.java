package cn.tico.iot.configmanger.module.mao.controller;

import cn.tico.iot.configmanger.common.page.TableDataInfo;
import cn.tico.iot.configmanger.common.utils.StringUtils;
import cn.tico.iot.configmanger.module.iot.controller.AdminKey;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import cn.tico.iot.configmanger.module.wx.models.OtherParts;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.mao.services.ParsService;
import cn.tico.iot.configmanger.common.base.Result;
import org.codehaus.groovy.util.StringUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
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
import java.util.stream.Collectors;

/**
 * 备品备件 信息操作处理
 * 
 * @author maodajun
 * @date 2019-12-14
 */
@IocBean
@At("/mao/pars")
public class ParsController implements AdminKey {
	private static final Log log = Logs.get();

	@Inject
	private UserService userService;

	@Inject
	public ParsService parsService;
	@Inject
	public KindService kindService;
	@Inject
	public DeptService deptService;
	@Inject
	public LocationService locationService;

	@RequiresPermissions("mao:pars:view")
	@At("")
	@Ok("th:/mao/pars/pars.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 用户权限
	 *
	 * @return
	 */
	private boolean isAdmin() {

		User user = ShiroUtils.getSysUser();

		Set roles = userService.getRoleCodeList(user);

		return roles.contains(ROLE_ADMIN);
	}

	/**
	 * 查询设备资本列表
	 */
	public Object lists() {

		return new Object();
	}

	// @RequiresPermissions("wx:mao:pars:list")
	@At
	@Ok("json")
	public Object list(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("enName") String enName,
			@Param("cnName") String cnName, @Param("assetStatus") String assetStatus,
			@Param("orderByColumn") String orderByColumn, @Param("isAsc") String isAsc, HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (Strings.isNotBlank(enName)) {
			cnd.and("en_name", "like", "%" + enName + "%");
		}
		if (Strings.isNotBlank(cnName)) {
			cnd.and("cn_name", "like", "%" + cnName + "%");
		}

		if (!isAdmin()) {

			cnd.and("dept_id", "=", ShiroUtils.getSysUser().getDeptId());
		}
		cnd.and("delflag", "=", "false");
		if (Strings.isNotBlank(orderByColumn)) {
			cnd.orderBy(orderByColumn, isAsc);
		} else {
			cnd.orderBy("update_time", "desc");
		}
		TableDataInfo info = parsService.tableList(pageNum, pageSize, cnd, orderByColumn, isAsc,
				"^dept|kind|location$");
		List list = info.getRows();
		List<Kind> kinds = kindService.query(Cnd.NEW().and("delflag", "=", "false"));
		// List<NutMap> listmap = kindService.appendKindList(list,kinds);
		// List<Kind> kinds = kindService.query(Cnd.NEW().and("delflag", "=",
		// "false").and("level", "in", Lists.newArrayList("3","1")));
		myKindNameFind(list, kinds);
		List<Location> locations = locationService.query(cnd.NEW().and("delflag","=","false"));
		myLocationsParentName(list,locations);
		// info.setRows(listmap);

		return info;
	}

	/**
	 * 新增设备资本
	 */
	@At("/add")
	@Ok("th:/mao/pars/add.html")
	public void add(HttpServletRequest req) {
		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept = deptService.fetch(deptid);
		req.setAttribute("dept", dept);
	}

	/**
	 * 新增保存设备资本
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("mao:pars:add")
	@Slog(tag = "设备资本", after = "新增保存设备资本 id=${args[0].id}")
	public Object addDo(@Param("..") OtherParts otherParts, @Param("next.cycle") String cycle,
			@Param("next.time") String time, HttpServletRequest req) {

		if (Lang.isNotEmpty(otherParts)) {
			otherParts.setCreateTime(new Date());
			otherParts.setUpdateTime(new Date());
			otherParts.setCreateBy(ShiroUtils.getSysUserId());
			otherParts.setUpdateBy(ShiroUtils.getSysUserId());
			otherParts.setStatus("true");
			otherParts.setDelFlag("false");
			parsService.insert(otherParts);
			return Result.success("system.success", otherParts);

		}
		return Result.error("system.error");

	}

	/**
	 * 修改备品备件
	 */
	@At("/edit/?")
	@Ok("th://mao/pars/edit.html")
	public void edit(String id, HttpServletRequest req) {

		OtherParts tIotDevices = parsService.fetch(id);
		parsService.fetchLinks(tIotDevices, "^dept|location|kind$");

		User user = ShiroUtils.getSysUser();
		String deptid = user.getDeptId();
		Dept dept = deptService.fetch(deptid);

		req.setAttribute("dept", dept);

		req.setAttribute("pars", tIotDevices);
	}

	/**
	 * 修改保存设备资本
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("mao:pars:edit")
	public Object editDo(@Param("..") OtherParts tIotDevices, @Param("next.cycle") String cycle,
			@Param("next.time") String time, HttpServletRequest req) {

		// tIotDevices = parsService.fetch(tIotDevices.getId());
		if (Lang.isNotEmpty(tIotDevices)) {
			tIotDevices.setUpdateBy(ShiroUtils.getSysUserId());
			tIotDevices.setUpdateTime(new Date());
			Dao forup = Daos.ext(parsService.dao(), FieldFilter.create(tIotDevices.getClass(), true));
			forup.update(tIotDevices);

			return Result.success("system.success", tIotDevices);

		} else {
			return Result.error("system.error");

		}

	}

	/**
	 * 删除设备资本
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("mao:pars:remove")
	@Slog(tag = "设备资本", after = "删除设备资本:${array2str(args[0])}")
	public Object remove(@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			parsService.vDelete(ids);

			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	// myKindNameFind
	public void myKindNameFind(List<OtherParts> list, List<Kind> kinds) {
		for (OtherParts device : list) {
			Kind kind = device.getKind();
			device.setKindParents(new HashMap<String, Kind>());
			if (Lang.isEmpty(kind)) {
				continue;
			}
			String fathers = kind.getAncestors();
			if (Strings.isBlank(fathers)) {
				continue;
			}
			for (Kind k : kinds) {
				if (Strings.equals(k.getLevel(), "3") && fathers.contains(k.getId())) {
					device.getKindParents().put("3", k);
				}
				if (Strings.equals(k.getLevel(), "2") && fathers.contains(k.getId())) {
					device.getKindParents().put("2", k);
				}
				if (Strings.equals(k.getLevel(), "1") && fathers.contains(k.getId())) {
					device.getKindParents().put("1", k);
				}
			}
			// kinds.stream().map(kind1 -> kind1.getId()).collect(Collectors.toList())

		}
	}

	public void myLocationsParentName(List<OtherParts> otherParts, List<Location> locations) {
		Logs.get().debugf("otherparts:%s,location:%s",otherParts.size(),locations.size());

		if(Lang.isEmpty(otherParts)){
			return;
		}
		if(Lang.isEmpty(locations)){
			return ;
		}

		otherParts.stream().forEach(part -> {
			Location partLocal = part.getLocation();
			if(Lang.isEmpty(partLocal)){
				return ;
			}
			String father = partLocal.getAncestors();

			String cnName = partLocal.getCnName();
			if(Lang.isEmpty(partLocal)){
				return;
			}
				List<String> names = locations.stream()
						.filter(location -> StringUtils.contains(father,location.getId()))
						.sorted(Comparator.comparing(location -> location.getLevel()))
						.map(location -> location.getCnName())
						.collect(Collectors.toList());
				names.add(cnName);

				partLocal.setCnName(Strings.join("-",names));
				part.setLocation(partLocal);


		});

	}

}
