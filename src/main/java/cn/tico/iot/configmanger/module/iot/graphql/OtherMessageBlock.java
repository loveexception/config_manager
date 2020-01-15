package cn.tico.iot.configmanger.module.iot.graphql;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.bean.GitBean;
import cn.tico.iot.configmanger.module.iot.graphql.Block;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.services.DeviceService;
import cn.tico.iot.configmanger.module.iot.services.GatewayService;
import cn.tico.iot.configmanger.module.iot.services.SubGatewayService;
import cn.tico.iot.configmanger.module.wx.models.TOtherMessages;
import cn.tico.iot.configmanger.module.wx.services.TOtherMessagesService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@IocBean(create = "init", depose = "depose")

public class OtherMessageBlock implements Block {

	@Inject
	public PropertiesProxy conf;
	@Inject
	public KafkaBlock kafkaBlock;
	@Inject
	public GitBlock gitBlock;
	@Inject
	GatewayService gatewayService;
	@Inject
	SubGatewayService subGatewayService;

	@Inject
	DeviceService deviceService;

	@Inject
	TOtherMessagesService tOtherMessagesService;

	@Override
	public Object exec(String topic, String key, String value, long offset) {
		try{
			getObject(topic, key, value);
		}catch (Exception e){
			Logs.get().errorf("exception: %s",e);
		}
		return null;
	}

	public Object getObject(String topic, String key, String value) {
		System.out.println("topic" + topic + "key:" + key + "value" + value);
		TOtherMessages tOtherMessages = new TOtherMessages();
		value = value.replace("运维提醒", "更换提醒");
		Map map = Json.fromJson(Map.class, value);
		tOtherMessages.setMessage("" + map.get("message"));
		tOtherMessages.setDeptId("" + map.get("dept"));
		tOtherMessages.setSno("" + map.get("sno"));
		tOtherMessages.setCnName("" + map.get("cnname"));
		String message = ("" + map.get("message"));

		String deptId = ("" + map.get("dept"));
		String sno = ("" + map.get("sno"));
		String cnname = ("" + map.get("cnname"));
		Cnd cnd = Cnd.NEW().and("dept_id", "=", deptId).and("message", "=", message).and("sno", "=", sno).and("cn_name",
				"=", cnname);
		List<TOtherMessages> list = tOtherMessagesService.query(cnd);
		if (Lang.isEmpty(list)) {
			tOtherMessagesService.insert(tOtherMessages);
		} else {
			TOtherMessages otherMessages = list.get(0);
			otherMessages.setMessage(message);
			tOtherMessagesService.update(otherMessages);
		}
		return null;
	}

	public void init() {

	}

	public void depose() {

	}
}
