package cn.tico.iot.configmanger.module.iot.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.services.GatewayService;
import cn.tico.iot.configmanger.module.iot.services.SubGatewayService;
import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.log.Logs;

import java.util.List;

@IocBean(create = "init", depose = "depose")

public class SubGatewayBlock implements Block {

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

    @Override
    public Object exec(String topic, String key, String value, long offset) {
        
        SubGateway temp = Json.fromJson(SubGateway.class , value);
        String sno = temp.getSno();
        /**
         * 1。	gateway已绑 退出
         */
        if(isSnoBindingAtLessOneGateWay(sno)){
            Logs.get().infof("gateway %s is all ready binding  " ,sno );
            return null;
        }

        

        /**
         * 2。	sub 已绑  删一个
         */

        if(isSnoBindingOneSubGatesy(sno)){
            Logs.get().infof("subgateway %s is binding and del ",sno );
            return removeOneSubGateway(sno);
        }


        /**
         * 3。	无SUB 建SUB
         *  6。	SUB 选一
         */
        SubGateway subGateway = chooseOneSubGatewayUnbind(sno);
        Logs.get().debugf("subgateway is  %s ",subGateway);
        if(Lang.isEmpty(subGateway)){
            Logs.get().infof("build a one subgateway %s ",sno);
            subGateway = buildNewSubGateway(value);
        }

        /**
         *  4。	无GATEWAY 退出
         *   5。	GATEAY 选一
         */
        Gateway gateway = chooseOneGatwayUnbind(sno);
        Logs.get().debugf("find gateway to bind %s",gateway);
        if(Lang.isEmpty(gateway)){
            Logs.get().infof("gateway is not find %s ",sno);
            return subGateway;
        }


        /**
         * 7。 	绑定	生成EXTSNO
         */
        String extSno = bindingGateWayAndSubGateWay(gateway,subGateway,sno);
        Logs.get().infof("binding on gateway %sy \n to subgateway %s  \n name extsno : %s ",gateway,subGateway,extSno);
        if(Strings.isBlank(extSno)){
            Logs.get().errorf(" sno is %s , but gateway %s and subgateway %s  not  fit to bind" ,sno ,gateway, subGateway );
            return null;
        }
        gatewayService.update(gateway);
        subGatewayService.update(subGateway);
        /**
         * 	发送KAFKA（还末建立 GIT ）
         */
        kafkaBlock.produce("config","extsno",extSno);
        Logs.get().infof("send tipc : config  ;keys :extsno ; value : ",extSno);


        return extSno;
    }

    /**
     * 生成随机ExtSNO
     * 绑定Gateway
     * 绑定 SUB Gateway
     * 保存 到数据库
     * @param gateway
     * @param subGateway
     * @return
     */
    public  String bindingGateWayAndSubGateWay(Gateway gateway, SubGateway subGateway,String sno ) {
        if(Lang.isEmpty(gateway)){
            return null;
        }
        if(Lang.isEmpty(subGateway)){
            return null;
        }
        if( Strings.isBlank(gateway.getId())){
            return null;
        }
        if(Strings.isBlank(subGateway.getId())){
            return null;
        }

        if(!Strings.equals(sno,gateway.getSno())){
            return null;
        }
        if(!Strings.equals(sno,subGateway.getSno())){
            return null;
        }
        String extsno = sno + R.sg(8);
        subGateway.setExtSno(extsno);
        subGateway.setGwid(gateway.getId());
        gateway.setSubid(subGateway.getId());

        return extsno;
    }

    /**
     * 多条 或 一 条 只返回一条
     * 需要绑定的 那个  gate way
     * @param sno
     * @return
     */
    public Gateway chooseOneGatwayUnbind(String sno) {
        Cnd cnd = Cnd.NEW()
                .and("status","=","true")
                .and("delflage","=","false")
                .and("sno","=",sno)
                .and ("subgateway_id","is ",null );
        List<Gateway> list = gatewayService.query(cnd);
        if(Lang.isEmpty(list)){
            return null;
        }
        Gateway gateway =list.iterator().next();
        Logs.get().debugf("choose one unbinding gateway %s",gateway);
        return gateway;
    }

    /**
     * 如果没有可以用来绑定的
     * sub gate way
     * 那么就新建一个新的SUB
     * @param json
     * @return
     */
    public  SubGateway buildNewSubGateway(String json) {
        SubGateway
        subGateway = Json.fromJson(SubGateway.class,json);
        return subGatewayService.insert(subGateway);
    }

    /**
     * 多条 或 一 条 只返回一条
     * 需要绑定的 那个 sub gate way
     * @param sno
     * @return
     */
    public SubGateway chooseOneSubGatewayUnbind(String sno) {
        Cnd cnd = Cnd.NEW()
                .and("status","=","true")
                .and("delflage","=","false")
                .and("sno","=",sno)
                .and ("subgateway_id","is ",null );
        List<SubGateway> list = subGatewayService.query(cnd);
        if(Lang.isEmpty(list)){
            return null;
        }
        SubGateway sub =list.iterator().next();
        Logs.get().debugf("choose one unbinding subgateway %s",sub);

        return sub;
    }

    /**
     * sub 已绑的删除一个
     * @param sno
     * @return
     */
    public  Object removeOneSubGateway(String sno) {
        Cnd cnd = Cnd.NEW()
                .and("status","=","true")
                .and("delflage","=","false")
                .and("sno","=",sno)
                .and ("subgateway_id","is not",null );
                ;

        List<SubGateway> list = subGatewayService.query(cnd);
        if(Lang.isEmpty(list)){
            return null;
        }

        SubGateway sub =list.iterator().next();
        Logs.get().debugf("del on binding subgateway %s",sub);
        subGatewayService.deletex(sub.getId());

        return sub;
    }

    /**
     * sub 是否有已绑定的
     * @param sno
     * @return
     */
    public boolean isSnoBindingOneSubGatesy(String sno) {
        Cnd cnd = Cnd.NEW()
                .and("status","=","true")
                .and("delflage","=","false")
                .and("sno","=",sno)
                .and ("subgateway_id","is not",null );
        ;
        return subGatewayService.count(cnd)>0;
    }

    /**
     * gateway已绑
     * @param sno
     * @return
     */
    public boolean isSnoBindingAtLessOneGateWay(String sno) {
        Cnd cnd  = Cnd.NEW()
                .and("status","=","true")
                .and ("delflage","=","false")
                .and ("sno","=",sno)
                .and ("subgateway_id","is not",null );
        int  count = gatewayService.count(cnd);
        return count >=1 ;
    }


}
