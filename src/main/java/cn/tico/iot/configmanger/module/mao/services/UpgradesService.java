package cn.tico.iot.configmanger.module.mao.services;

import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import cn.tico.iot.configmanger.module.mao.models.Pushs;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.mao.models.Upgrades;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;

import java.util.List;

/**
 * 告警升级 服务层实现
 * 
 * @author maodajun
 * @date 2020-01-03
 */
@IocBean(args = {"refer:dao"})
public class UpgradesService extends Service<Upgrades> {
	@Inject
	KafkaBlock kafkaBlock;



	public UpgradesService(Dao dao) {
		super(dao);
	}

	public Dept findDeptByAllUpgrades(String deptid){
		Dept dept = dao().fetch(Dept.class,deptid);
		if(Lang.isEmpty(dept)){
			return null;
		}
		Cnd cnd = Cnd.NEW().and("dept_id","=",dept.getId());

		List<Pushs> pushs = dao().query(Pushs.class,cnd);
		List<Upgrades> upgradesList = dao().query(Upgrades.class,cnd);
		dept.setPushs(pushs);
		dept.setUpgrades(upgradesList);

		return dept;
	}
	public void kafkaDept(Dept dept){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(dept);

		json = Json.toJson(Json.fromJson(json), JsonFormat.full().setActived("^(id|dept_name|upgrades|pushs|grade|cycle|count_down|level|dept_id|type)$")); // 不输出location,其他key正常输出


		kafkaBlock.produce(KafkaBlock.TOPIC_DEPT,dept.getDeptName(),json);

	}
}
