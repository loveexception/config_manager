package cn.tico.iot.configmanger.module.sys.services;

import cn.tico.iot.configmanger.common.base.Service;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.sys.models.Area;
import org.nutz.lang.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域 服务层实现
 * 
 * @author haiming
 * @date 2019-04-11
 */
@IocBean(args = {"refer:dao"})
public class AreaService extends Service<Area> {
	public AreaService(Dao dao) {
		super(dao);
	}

	/**
     * 对象转 区域 树
     *
     * @param list 区域列表
     * @return
     */
	public List<Map<String, Object>> getTrees(List<Area> list) {
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		for (Area data : list) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", data.getId());
			dataMap.put("pId", data.getParentId());
			dataMap.put("name", data.getName());
			dataMap.put("title", data.getName());
			dataMap.put("checked", false);
			trees.add(dataMap);
		}
		return trees;
	}

	/**
	 * 查询数据树
	 * @param parentId
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> selectTree(String parentId, String name) {
		Cnd cnd = Cnd.NEW();
		if (Strings.isNotBlank(name)) {
			//cnd.and("name", "like", "%" + name + "%");
		}
		if (Strings.isNotBlank(parentId)) {
			cnd.and("parent_id", "=", parentId);
		}
//		cnd.and("status", "=", false).and("del_flag", "=", false);
		List<Area> list = this.query(cnd);
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		trees = getTrees(list);
		return trees;
	}

}
