package cn.tico.iot.configmanger.module.wx.controller;

import cn.tico.iot.configmanger.common.page.TableDataInfo;
import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.models.driver.Normal;
import cn.tico.iot.configmanger.module.iot.services.DriverService;
import cn.tico.iot.configmanger.module.iot.services.KindService;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.tico.iot.configmanger.module.wx.models.TUiMetrics;
import cn.tico.iot.configmanger.module.wx.services.TUiMetricsService;
import cn.tico.iot.configmanger.common.base.Result;;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.plugins.slog.annotation.Slog;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 一键寻检数据 信息操作处理
 * 
 * @author maodajun
 * @date 2019-08-21
 */
@IocBean
@At("/wx/tUiMetrics")
public class TUiMetricsController {
	private static final Log log = Logs.get();

	@Inject
	private TUiMetricsService tUiMetricsService;

	@Inject
	private DriverService driverService;

	@Inject
	private KindService kindService;

	@RequiresPermissions("wx:tUiMetrics:view")
	@At("")
	@Ok("th:/wx/tUiMetrics/tUiMetrics.html")
	public void index(HttpServletRequest req) {

	}

	/**
	 * 查询一键寻检数据列表
	 */
	@RequiresPermissions("wx:tUiMetrics:list")
	@At
	@Ok("json")
	public Object list(@Param("pageNum")int pageNum,
					   @Param("pageSize")int pageSize,
					   @Param("name") String name,
					   @Param("kindTypeId") String kindTypeId,
					   @Param("beginTime") Date beginTime,
					   @Param("endTime") Date endTime,
					   @Param("orderByColumn") String orderByColumn,
					   @Param("isAsc") String isAsc,
					   HttpServletRequest req) {
		Cnd cnd = Cnd.NEW();
		if (!Strings.isBlank(name)){
			cnd.and("cn_name", "like", "%" + name +"%");
		}
		if (!Strings.isBlank(kindTypeId)){
			cnd.and("kind_type_id", "like", "%" + kindTypeId +"%");
		}
		cnd.orderBy("order_num","asc");
		Pager pager =new Pager(pageNum,pageSize);
		List<TUiMetrics> list =tUiMetricsService.dao().queryByJoin (TUiMetrics.class,"",cnd,pager);

		pager.setRecordCount(tUiMetricsService.dao().count(TUiMetrics.class,cnd));
		TableDataInfo info =new TableDataInfo(list,pager.getRecordCount() );
		info.setPageCount(pager.getPageCount());
		return info;
		 //return tUiMetricsService.tableList(pageNum,pageSize,cnd,orderByColumn,isAsc,null);
	}

	/**
	 * 新增一键寻检数据
	 */
	@At("/add")
	@Ok("th:/wx/tUiMetrics/add.html")
	public void add( HttpServletRequest req) {

	}

	/**
	 * 新增保存一键寻检数据
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tUiMetrics:add")
	@Slog(tag="一键寻检数据", after="新增保存一键寻检数据 id=${args[0].id}")
	public Object addDo(@Param("..") TUiMetrics tUiMetrics,HttpServletRequest req) {
		try {
			tUiMetricsService.insert(tUiMetrics);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 修改一键寻检数据
	 */
	@At("/edit/?")
	@Ok("th://wx/tUiMetrics/edit.html")
	public void edit(String id, HttpServletRequest req) {
		TUiMetrics tUiMetrics = tUiMetricsService.fetch(id);
		req.setAttribute("tUiMetrics",tUiMetrics);
	}

	/**
	 * 修改保存一键寻检数据
	 */
	@At
	@POST
	@Ok("json")
	@RequiresPermissions("wx:tUiMetrics:edit")
	@Slog(tag="一键寻检数据", after="修改保存一键寻检数据")
	public Object editDo(@Param("..") TUiMetrics tUiMetrics,HttpServletRequest req) {
		try {
			if(Lang.isNotEmpty(tUiMetrics)){
				tUiMetrics.setUpdateBy(ShiroUtils.getSysUserId());
				tUiMetrics.setUpdateTime(new Date());
				tUiMetricsService.update(tUiMetrics);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 删除一键寻检数据
	 */
	@At("/remove")
	@Ok("json")
	@RequiresPermissions("wx:tUiMetrics:remove")
	@Slog(tag ="一键寻检数据", after= "删除一键寻检数据:${array2str(args[0])}")
	public Object remove(@Param("ids")String[] ids, HttpServletRequest req) {
		try {
			tUiMetricsService.delete(ids);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 变更 一键寻检数据
	 */
	@At("/change")
	@Ok("json")
	public Object change(@Param("id")String id,@Param("name")String name, HttpServletRequest req) {
		try {
			 TUiMetrics tUiMetrics=tUiMetricsService.fetch(id);
			Mirror<TUiMetrics> mirror = Mirror.me(TUiMetrics.class);

			Object is  = mirror.getValue(tUiMetrics, name);

			 if(Lang.isEmpty(is)){
			 	return Result.success("system.error");
			 }
			 if(Strings.equalsIgnoreCase(is.toString(),"true")){
				 mirror.setValue(tUiMetrics, mirror.getField(name), "false");
			 }
			if(Strings.equalsIgnoreCase(is.toString(),"false")){
				mirror.setValue(tUiMetrics, mirror.getField(name), "true");
			}
			tUiMetricsService.update(tUiMetrics);
			return Result.success("system.success",tUiMetrics);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}

	/**
	 * 导入 一键寻检数据
	 */
	@At("/export")
	@Ok("json")
	public Object export(@Param("kindid")String id, HttpServletRequest req) {
		try {
			if(Lang.isEmpty(id)){
				return Result.error(101,"system.error");
			}
			Cnd cnd = Cnd.NEW();

			List<Kind> kinds = kindService.query(Cnd.where("parent_id","=",id));
			Kind kind = kindService.fetch(id);
			kinds.add(kind);
			List<String> inkinds = Lists.newArrayList();
			for (int i = 0; i < kinds.size(); i++) {
				inkinds.add(kinds.get(i).getId());
			}
			cnd.and("kindid","in",inkinds);
			cnd.and("delflag","=","false");
			List<Driver> drivers = driverService.query(cnd);
			if(Lang.isEmpty(drivers)){
				return Result.error(201,"system.error");
			}
			Driver driver = driverService.fetchLinks(drivers.get(0),"normals");
			List<Normal> normals = driver.getNormals();
			List<TUiMetrics> tUiMetrics = Lists.newArrayList();
			for (int i = 0; i < normals.size(); i++) {
				TUiMetrics temp = new TUiMetrics();
				Normal normal =normals.get(i);
				temp.setKindTypeId(id);
				temp.setOrderNum(new Long(i));
				temp.setCnName(normal.getCnName());
				temp.setEnName(normal.getOperateKey());
				temp.setViewMetrics("false");
				temp.setViewTable("false");
				temp.setViewGraph("false");
				temp.setStatus("true");
				temp.setDelFlag("false");


				tUiMetricsService.insert(temp);
				tUiMetrics.add(temp);

			}
			return Result.success("system.success",tUiMetrics);
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
}
