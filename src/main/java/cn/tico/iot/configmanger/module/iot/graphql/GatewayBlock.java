package cn.tico.iot.configmanger.module.iot.graphql;

import cn.tico.iot.configmanger.module.iot.graphql.Block;
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
import org.nutz.mapl.Mapl;

import java.util.List;
import java.util.Map;

@IocBean(create = "init", depose = "depose")

public class GatewayBlock implements Block {

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


        SubGateway subGateway = canRegister(value);
        if(Lang.isEmpty(subGateway)){
            return null;
        }

        Gateway gateway = haveSnoGateway(subGateway);

        if(Lang.isEmpty(gateway)){
            subGatewayService.insertEntity(subGateway);
            return subGateway;
        }
        if(Strings.isNotBlank(gateway.getSubid())){
            return null;
        }

        subGateway = registerSubGateWay(subGateway, gateway);

        gateway =gitBlock .createProject(gateway);

        kafkaBlock.produce("config","extsno",subGateway.getExtSno());


        return subGateway;

    }

    private SubGateway registerSubGateWay(SubGateway subGateway, Gateway gateway) {
        String extsno = subGateway.getSno()+ R.sg(8).next();
        subGateway.setExtSno(extsno);
        subGateway.setGwid(gateway.getId());


        subGatewayService.insertUpdateEntity(subGateway);

        gateway.setSubid(subGateway.getId());

        //gateway.setGitPath(conf.get("gitpath")+"/"+extsno);


        gatewayService.updateEntityRobot(gateway);

        subGateway.setGateway(gateway);
        return subGateway;
    }

    public  SubGateway canRegister(String value) {
        SubGateway subGateway = buileSubGateway(value);
        if(Lang.isEmpty(subGateway)){
            return null;
        }
        if(Lang.isEmpty(subGateway.getSno())){
            return null;
        }
        Cnd cnd = Cnd.NEW();
        cnd.and("sno","=",subGateway.getSno());
        cnd.and("status","=",true);
        cnd.and("delflag","=",false);
        List<SubGateway> registers = subGatewayService.query(cnd);
        if(Lang.isEmpty(registers)){
            return subGateway;
        }
        if(registers.size()==1){
            SubGateway sub =registers.get(0);
            sub.setSno(subGateway.getSno());
            sub.setExtip(subGateway.getExtip());
            sub.setApi(subGateway.getApi());
            sub.setPort(subGateway.getPort());
            return sub;
        }
        return null;
    }



    public Gateway haveSnoGateway(SubGateway subGateway) {
        Cnd cnd = Cnd.NEW();
        cnd.and("sno","=",subGateway.getSno());
        cnd.and("status","=",true);
        cnd.and("delflag","=",false);
        List<Gateway> gateways =gatewayService.query(cnd);
        if(Lang.isEmpty(gateways)){
            return null;
        }


        return gateways.get(0);
    }

    public SubGateway buileSubGateway(String value) {
        if(Strings.isEmpty(value)){
            return null;
        }


        SubGateway gateway = new SubGateway();

        Object obj = Json.fromJson(value);


        Object  sno = Mapl.cell(obj,"sno");

        if(Lang.isEmpty(sno)){
            return null;
        }

        Object extip = Mapl.cell(obj,"outerip");

        if(Lang.isEmpty(extip)){
            extip = "127.0.0.1";
        }

        gateway.setExtip(""+extip);
        gateway.setSno(""+sno);
        return gateway;
    }

    public void init(){

    }
    public void depose(){

    }
}