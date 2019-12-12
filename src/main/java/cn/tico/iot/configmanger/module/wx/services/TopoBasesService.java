package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.Topo.Base;
import cn.tico.iot.configmanger.module.iot.models.base.Tag;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.services.TagService;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 拓扑图存储 服务层实现
 * 
 * @author maodajun
 * @date 2019-12-05
 */
@IocBean(args = {"refer:dao"})
public class TopoBasesService extends Service<Base> {
	@Inject
	TagService tagService;



	public TopoBasesService(Dao dao) {
		super(dao);
	}

	public List<Device> getShowDevids(String baseId) {


			Base base = fetch(baseId);


			if(Lang.isNotEmpty(base)){
				base = fetchLinks(base,"^kind|location|hide|show|dept$");

			}

			Tag show = base.getShow();
			if(Lang.isNotEmpty(show)){
				show = tagService.fetchLinks(show,"devices");
			}
			List<Device> showdev=null;
			if(Lang.isNotEmpty(show)){
				showdev = show.getDevices();
			}




			return showdev;


	}
}
