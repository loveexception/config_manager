package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.wx.models.OtherEmp;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.wx.models.OtherWorkflow;
import org.nutz.lang.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 运维 服务层实现
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@IocBean(args = {"refer:dao"})
public class OtherWorkflowService extends Service<OtherWorkflow> {
	public OtherWorkflowService(Dao dao) {
		super(dao);
	}

    public OtherWorkflow insertEntity(OtherWorkflow otherWorkflow) {

		dao().insert(otherWorkflow);
		saveWorkEmps(otherWorkflow);
		return otherWorkflow;
    }



	public void updateEntity(OtherWorkflow otherWorkflow) {
		dao().updateIgnoreNull(otherWorkflow);
		saveWorkEmps(otherWorkflow);
	}
	public void saveWorkEmps(OtherWorkflow otherWorkflow) {
		List<String> ids = new ArrayList<>();
		if (otherWorkflow != null && Strings.isNotBlank(otherWorkflow.getEmpIds())) {
			if (Strings.isNotBlank(otherWorkflow.getEmpIds())) {
				ids = Arrays.asList(otherWorkflow.getEmpIds().split(","));
			}
			//清除已有关系
			this.fetchLinks(otherWorkflow, "emps");
			dao().clearLinks(otherWorkflow, "emps");
		}
		if (ids != null && ids.size() > 0) {
			Criteria cri = Cnd.cri();
			cri.where().andInStrList("id", ids);
			List<OtherEmp> empList = dao().query(OtherEmp.class, cri);
			otherWorkflow.setEmps(empList);
		}
		//更新关系
		dao().insertRelation(otherWorkflow, "emps");
	}
}
