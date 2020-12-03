package cn.tico.iot.configmanger.module.mao.controller;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.mao.models.Upgrades;
import cn.tico.iot.configmanger.module.mao.services.UpgradesService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.DeptService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ForwardView;
import org.nutz.plugins.slog.annotation.Slog;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 告警升级 信息操作处理
 * 
 * @author maodajun
 * @date 2020-01-03
 */
@IocBean
@At("/cui/")
public class CuiMeetingController {
	private static final Log log = Logs.get();


	@Inject
	PropertiesProxy conf;


	@At("/meeting")
	@Ok("re")
	public Object list(HttpServletRequest req) {
		String url ="/html/meeting/list/index.html";
		url = getRedirectUrl(url);
		return url;
	}
	@At("/meeting_data")
	@Ok("re")
	public Object meetingdata(HttpServletRequest req) {

		String url ="/html/meetingdata/list/index.html";
		url = getRedirectUrl(url);
		return url;
	}

	@At("/meeting_level")
	@Ok("re")
	public Object meetingLevel(HttpServletRequest req) {

		String url ="/html/meetinglevel/list/index.html";
		url = getRedirectUrl(url);
		return url;

	}
	@At("/diagram")
	@Ok("re")
	public Object diagram(HttpServletRequest req) {

		String url ="/html/diagram/list/index.html";
		url = getRedirectUrl(url);
		return url;

	}

	private String getRedirectUrl(String path) {
		String url ="redirect:${path}?dept_id=${deptId}&ip=${api}";
		User obj = ShiroUtils.getSysUser();
		if(Lang.isEmpty(obj)){
			return "redirect:/login";
		}
		String deptId = obj.getDeptId();
		String api = conf.get("cui_meeting_interface");
		if(Strings.isBlank(deptId)){
			return "redirect:/login";
		}
		if(Strings.isBlank(api)){
			return "redirect:/login";
		}
		Segment sg = new CharSegment(url);
		sg.set("deptId",deptId);
		sg.set("api",api);
		sg.set("path",path);
		return sg.toString();
	}

}
