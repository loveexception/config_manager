package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import com.google.common.collect.Lists;
import org.nutz.castor.Castors;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.TIotOwner;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 资产管理 服务层实现
 * 
 * @author maodajun
 * @date 2019-11-06
 */
@IocBean(args = {"refer:dao"})
public class TIotOwnerService extends Service<TIotOwner> {
	public TIotOwnerService(Dao dao) {
		super(dao);
	}

    public List<Map> queryCountTimeOutDeviceIds(String deptid, String time) {
		if(Strings.isEmpty(time)){
			time = Times.ts2S(new Date().getTime(),"yyyy-MM-dd");

		}
		String sql = "select  dev.sno as sno ,dev.id as id  " +
				",from_unixtime(max(unix_timestamp(own.time))) as time " +
				" from t_iot_devices  dev " +
				" left join t_iot_owner own  on dev.id = own.device_id \n" +
				" where own.time is not null \n" ;
		String group = 		"group by dev.sno ,dev.id\n" +
				"HAVING max(UNIX_TIMESTAMP(own.time)) < UNIX_TIMESTAMP(@mytime)";
		if(Strings.isBlank(deptid)){
			sql += group;
		}else{
			sql  += "and dev.dept_id = @mydept \n";
			sql += group;
		}
		Sql base = Sqls.create(sql);
		base
				.setParam("mydept",deptid)
				.setParam("mytime",time);
		base.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Map> list = new LinkedList<Map>();
				while (rs.next()){
					NutMap map = NutMap.NEW();
					map.addv("sno",rs.getString("sno"))
							.addv("id",rs.getString("id"))
							.addv("time",rs.getString("time"));
					list.add(map);
				}
				return list;
			}
		});



		dao().execute(base);
		return base.getList(Map.class);
    }
}
