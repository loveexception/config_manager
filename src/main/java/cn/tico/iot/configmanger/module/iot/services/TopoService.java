package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.Topo.Topo;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

@IocBean(args = {"refer:dao"})
public class TopoService extends Service<Topo> {

    public TopoService(Dao dao) {
        super(dao);
    }

    @Inject
    TagService tagService;

    public Object drawByTag(String tag) {

        return Json.fromJson(testtemp);
    }
    static  final  String testtemp = "{    " +
            "   \"code\": 0,\n" +
            "    \"msg\": \"操作成功\",\n" +
            "    \"data\": {\n" +
            "\t\t\"nodes\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"54\",\n" +
            "\t\t\t\t\"sno\": \"FD154430C863D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.40\",\n" +
            "\t\t\t\t\"label\": \"B座二层1-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 829.9109176466993,\n" +
            "\t\t\t\t\"y\": 144.98882861316332,\n" +
            "\t\t\t\t\"index\": 63\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"52\",\n" +
            "\t\t\t\t\"sno\": \"FD161631300CD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.20\",\n" +
            "\t\t\t\t\"label\": \"639会议终端主用行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 828.9109176466993,\n" +
            "\t\t\t\t\"y\": 180,\n" +
            "\t\t\t\t\"index\": 64\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"53\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313009D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.21\",\n" +
            "\t\t\t\t\"label\": \"639会议终端备用行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 821.924887068086,\n" +
            "\t\t\t\t\"y\": 213.1855406267627,\n" +
            "\t\t\t\t\"index\": 65\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"8\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313048D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.31\",\n" +
            "\t\t\t\t\"label\": \"721会议终端主用行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 822.424887068086,\n" +
            "\t\t\t\t\"y\": 246.48882861316332,\n" +
            "\t\t\t\t\"index\": 66\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"6\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK5001523\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"switch\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.255.3\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-3\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 591.7471857114074,\n" +
            "\t\t\t\t\"y\": 418.96638426895214,\n" +
            "\t\t\t\t\"index\": 67\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"91\",\n" +
            "\t\t\t\t\"sno\": \"FD17033152F6D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.33\",\n" +
            "\t\t\t\t\"label\": \"会城门-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 693.1022387456808,\n" +
            "\t\t\t\t\"y\": 475.58012361522657,\n" +
            "\t\t\t\t\"index\": 68\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"42\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313001D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.226\",\n" +
            "\t\t\t\t\"label\": \"华商能源行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 689.2471857114074,\n" +
            "\t\t\t\t\"y\": 500.08012361522657,\n" +
            "\t\t\t\t\"index\": 69\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"43\",\n" +
            "\t\t\t\t\"sno\": \"FD161631305ED7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.5.18\",\n" +
            "\t\t\t\t\"label\": \"华商电灯行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 687.4677821667515,\n" +
            "\t\t\t\t\"y\": 520.7138702444377,\n" +
            "\t\t\t\t\"index\": 70\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"44\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CF1CD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.242\",\n" +
            "\t\t\t\t\"label\": \"吉北咨询行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 686.9677821667515,\n" +
            "\t\t\t\t\"y\": 550.703053599552,\n" +
            "\t\t\t\t\"index\": 71\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"45\",\n" +
            "\t\t\t\t\"sno\": \"FD161631300DD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.130\",\n" +
            "\t\t\t\t\"label\": \"京电设计行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 686.4677821667515,\n" +
            "\t\t\t\t\"y\": 577.2748527816004,\n" +
            "\t\t\t\t\"index\": 72\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"49\",\n" +
            "\t\t\t\t\"sno\": \"FD1648314584D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.17\",\n" +
            "\t\t\t\t\"label\": \"公司巡察办行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 685.9677821667515,\n" +
            "\t\t\t\t\"y\": 601.7748527816004,\n" +
            "\t\t\t\t\"index\": 73\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"48\",\n" +
            "\t\t\t\t\"sno\": \"82163143B910CN\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.98\",\n" +
            "\t\t\t\t\"label\": \"中电联汽车行政-7000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 678.6022387456808,\n" +
            "\t\t\t\t\"y\": 629.8763885397445,\n" +
            "\t\t\t\t\"index\": 74\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"34\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313016D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.194\",\n" +
            "\t\t\t\t\"label\": \"客服中心行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 668.4109176466993,\n" +
            "\t\t\t\t\"y\": 662.1755244857567,\n" +
            "\t\t\t\t\"index\": 75\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"83\",\n" +
            "\t\t\t\t\"sno\": \"FD190331C08DD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.5.114\",\n" +
            "\t\t\t\t\"label\": \"大兴机场东-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 519.1159133771671,\n" +
            "\t\t\t\t\"y\": 477.20305359955194,\n" +
            "\t\t\t\t\"index\": 76\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"35\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFE7D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.210\",\n" +
            "\t\t\t\t\"label\": \"电动车公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 508.0419951669095,\n" +
            "\t\t\t\t\"y\": 502.20305359955194,\n" +
            "\t\t\t\t\"index\": 77\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"80\",\n" +
            "\t\t\t\t\"sno\": \"FD191031C8D8D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.5.81\",\n" +
            "\t\t\t\t\"label\": \"昌平19006分指挥部-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 507.5419951669095,\n" +
            "\t\t\t\t\"y\": 528.2748527816003,\n" +
            "\t\t\t\t\"index\": 78\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"64\",\n" +
            "\t\t\t\t\"sno\": \"FD161330CE93D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.193\",\n" +
            "\t\t\t\t\"label\": \"客服中心应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 507.0419951669095,\n" +
            "\t\t\t\t\"y\": 559.4663842689521,\n" +
            "\t\t\t\t\"index\": 79\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"71\",\n" +
            "\t\t\t\t\"sno\": \"FD1812319644D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.161\",\n" +
            "\t\t\t\t\"label\": \"华商远大应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 501.1162081670675,\n" +
            "\t\t\t\t\"y\": 588.1755244857567,\n" +
            "\t\t\t\t\"index\": 80\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"40\",\n" +
            "\t\t\t\t\"sno\": \"FD150330B592D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.114\",\n" +
            "\t\t\t\t\"label\": \"华商远大行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 494.2471857114074,\n" +
            "\t\t\t\t\"y\": 619.8608400412821,\n" +
            "\t\t\t\t\"index\": 81\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"41\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313057D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.5.34\",\n" +
            "\t\t\t\t\"label\": \"华商三优行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 500.6162081670675,\n" +
            "\t\t\t\t\"y\": 644.8608400412821,\n" +
            "\t\t\t\t\"index\": 82\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"82\",\n" +
            "\t\t\t\t\"sno\": \"FD1628313232D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.18\",\n" +
            "\t\t\t\t\"label\": \"芦城应急抢修中心-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 508.0419951669095,\n" +
            "\t\t\t\t\"y\": 662.6755244857567,\n" +
            "\t\t\t\t\"index\": 83\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"69\",\n" +
            "\t\t\t\t\"sno\": \"FD16283131B1D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.241\",\n" +
            "\t\t\t\t\"label\": \"检修公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1059.142479594469,\n" +
            "\t\t\t\t\"y\": 483.4663842689522,\n" +
            "\t\t\t\t\"index\": 84\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"72\",\n" +
            "\t\t\t\t\"sno\": \"82151242D55FCG\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.145\",\n" +
            "\t\t\t\t\"label\": \"海淀公司应急-hdx8000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1065.15914421547,\n" +
            "\t\t\t\t\"y\": 508.4663842689522,\n" +
            "\t\t\t\t\"index\": 85\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"20\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313028D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.162\",\n" +
            "\t\t\t\t\"label\": \"房山公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1066.6806074081094,\n" +
            "\t\t\t\t\"y\": 527.6803786258138,\n" +
            "\t\t\t\t\"index\": 86\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"12\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFB0D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.82\",\n" +
            "\t\t\t\t\"label\": \"朝阳公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1064.65914421547,\n" +
            "\t\t\t\t\"y\": 552.1803786258138,\n" +
            "\t\t\t\t\"index\": 87\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"11\",\n" +
            "\t\t\t\t\"sno\": \"82150542D281CG\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.66\",\n" +
            "\t\t\t\t\"label\": \"城区公司行政-7000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1067.1806074081094,\n" +
            "\t\t\t\t\"y\": 568.9663842689522,\n" +
            "\t\t\t\t\"index\": 88\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"30\",\n" +
            "\t\t\t\t\"sno\": \"8212370F59DDCN\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.2\",\n" +
            "\t\t\t\t\"label\": \"检修公司行政-hdx7000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1058.642479594469,\n" +
            "\t\t\t\t\"y\": 585.6630962825516,\n" +
            "\t\t\t\t\"index\": 89\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"22\",\n" +
            "\t\t\t\t\"sno\": \"8210320B2CB5B9\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.178\",\n" +
            "\t\t\t\t\"label\": \"平谷公司行政8000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1064.65914421547,\n" +
            "\t\t\t\t\"y\": 601.6803786258138,\n" +
            "\t\t\t\t\"index\": 90\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"24\",\n" +
            "\t\t\t\t\"sno\": \"FD1722315AD1D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.2\",\n" +
            "\t\t\t\t\"label\": \"密云公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1059.642479594469,\n" +
            "\t\t\t\t\"y\": 626.1803786258138,\n" +
            "\t\t\t\t\"index\": 91\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"23\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313084D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.34\",\n" +
            "\t\t\t\t\"label\": \"怀柔公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1049.6375658705986,\n" +
            "\t\t\t\t\"y\": 643.4663842689522,\n" +
            "\t\t\t\t\"index\": 92\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"26\",\n" +
            "\t\t\t\t\"sno\": \"FD161631302BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.97\",\n" +
            "\t\t\t\t\"label\": \"延庆公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1045.0547369874014,\n" +
            "\t\t\t\t\"y\": 659.6630962825516,\n" +
            "\t\t\t\t\"index\": 93\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"31\",\n" +
            "\t\t\t\t\"sno\": \"FD183531B12CD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.50\",\n" +
            "\t\t\t\t\"label\": \"信通公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1050.1375658705986,\n" +
            "\t\t\t\t\"y\": 684.1630962825516,\n" +
            "\t\t\t\t\"index\": 94\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"13\",\n" +
            "\t\t\t\t\"sno\": \"FD161330CDB2D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.146\",\n" +
            "\t\t\t\t\"label\": \"海淀公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1064.15914421547,\n" +
            "\t\t\t\t\"y\": 699.943939924741,\n" +
            "\t\t\t\t\"index\": 95\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"14\",\n" +
            "\t\t\t\t\"sno\": \"82151943A1B0CN\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.18\",\n" +
            "\t\t\t\t\"label\": \"丰台公司行政-7000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1057.642479594469,\n" +
            "\t\t\t\t\"y\": 731.2907964000262,\n" +
            "\t\t\t\t\"index\": 96\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"61\",\n" +
            "\t\t\t\t\"sno\": \"FD185131BD75D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.97\",\n" +
            "\t\t\t\t\"label\": \"朝阳公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 868.6235964492118,\n" +
            "\t\t\t\t\"y\": 492.96638426895214,\n" +
            "\t\t\t\t\"index\": 97\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"79\",\n" +
            "\t\t\t\t\"sno\": \"FD184631B8AAD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.68\",\n" +
            "\t\t\t\t\"label\": \"城区公司五层-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 874.6412278852893,\n" +
            "\t\t\t\t\"y\": 517.4663842689522,\n" +
            "\t\t\t\t\"index\": 98\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"75\",\n" +
            "\t\t\t\t\"sno\": \"FD1628313230D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.1\",\n" +
            "\t\t\t\t\"label\": \"密云公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 869.1235964492118,\n" +
            "\t\t\t\t\"y\": 541.9663842689522,\n" +
            "\t\t\t\t\"index\": 99\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"59\",\n" +
            "\t\t\t\t\"sno\": \"FD154430C945D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.33\",\n" +
            "\t\t\t\t\"label\": \"昌平公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 874.1412278852893,\n" +
            "\t\t\t\t\"y\": 561.1630962825516,\n" +
            "\t\t\t\t\"index\": 100\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"15\",\n" +
            "\t\t\t\t\"sno\": \"FD161631307DD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.130\",\n" +
            "\t\t\t\t\"label\": \"石景山公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 874.1412278852893,\n" +
            "\t\t\t\t\"y\": 585.6630962825516,\n" +
            "\t\t\t\t\"index\": 101\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"74\",\n" +
            "\t\t\t\t\"sno\": \"FD18033193C3D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.49\",\n" +
            "\t\t\t\t\"label\": \"门头沟新会场行政（应急）-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 876.1412278852893,\n" +
            "\t\t\t\t\"y\": 610.1630962825516,\n" +
            "\t\t\t\t\"index\": 102\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"19\",\n" +
            "\t\t\t\t\"sno\": \"FD161330CD8AD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.51\",\n" +
            "\t\t\t\t\"label\": \"门头沟公司双流(行政)-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 877.6412278852893,\n" +
            "\t\t\t\t\"y\": 626.6803786258138,\n" +
            "\t\t\t\t\"index\": 103\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"37\",\n" +
            "\t\t\t\t\"sno\": \"FD154430C4CBD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.226\",\n" +
            "\t\t\t\t\"label\": \"电缆分公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 877.6412278852893,\n" +
            "\t\t\t\t\"y\": 642.9663842689522,\n" +
            "\t\t\t\t\"index\": 104\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"78\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313007D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.113\",\n" +
            "\t\t\t\t\"label\": \"通州公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 878.1412278852893,\n" +
            "\t\t\t\t\"y\": 660.1630962825516,\n" +
            "\t\t\t\t\"index\": 105\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"18\",\n" +
            "\t\t\t\t\"sno\": \"FD1818319F36D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.34\",\n" +
            "\t\t\t\t\"label\": \"昌平公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 873.6412278852893,\n" +
            "\t\t\t\t\"y\": 681.7907964000262,\n" +
            "\t\t\t\t\"index\": 106\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"73\",\n" +
            "\t\t\t\t\"sno\": \"FD162831325DD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.33\",\n" +
            "\t\t\t\t\"label\": \"怀柔公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 874.6412278852893,\n" +
            "\t\t\t\t\"y\": 706.2907964000262,\n" +
            "\t\t\t\t\"index\": 107\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"17\",\n" +
            "\t\t\t\t\"sno\": \"FD1635314358D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.82\",\n" +
            "\t\t\t\t\"label\": \"通州公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 874.1412278852893,\n" +
            "\t\t\t\t\"y\": 724.943939924741,\n" +
            "\t\t\t\t\"index\": 108\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"76\",\n" +
            "\t\t\t\t\"sno\": \"FD16283131A3D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.177\",\n" +
            "\t\t\t\t\"label\": \"平谷公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 878.6412278852893,\n" +
            "\t\t\t\t\"y\": 740.2208971760274,\n" +
            "\t\t\t\t\"index\": 109\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"65\",\n" +
            "\t\t\t\t\"sno\": \"FD162831331FD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.161\",\n" +
            "\t\t\t\t\"label\": \"房山公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1050.6375658705986,\n" +
            "\t\t\t\t\"y\": 749.443939924741,\n" +
            "\t\t\t\t\"index\": 110\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"66\",\n" +
            "\t\t\t\t\"sno\": \"FD16283131A9D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.17\",\n" +
            "\t\t\t\t\"label\": \"丰台公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 876.1412278852893,\n" +
            "\t\t\t\t\"y\": 749.943939924741,\n" +
            "\t\t\t\t\"index\": 111\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"4\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK5001449\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"switch\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.255.1\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-1\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 963.6235964492118,\n" +
            "\t\t\t\t\"y\": 418.46638426895214,\n" +
            "\t\t\t\t\"index\": 112\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"51\",\n" +
            "\t\t\t\t\"sno\": \"FD183531ABF3D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.10\",\n" +
            "\t\t\t\t\"label\": \"应急指挥中心1-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1011.424887068086,\n" +
            "\t\t\t\t\"y\": 145.48882861316332,\n" +
            "\t\t\t\t\"index\": 113\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"55\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313029D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.41\",\n" +
            "\t\t\t\t\"label\": \"B座二层2-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1016.15914421547,\n" +
            "\t\t\t\t\"y\": 170.48882861316332,\n" +
            "\t\t\t\t\"index\": 114\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"56\",\n" +
            "\t\t\t\t\"sno\": \"FD183531AFBDD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.2\",\n" +
            "\t\t\t\t\"label\": \"19006公司总指挥部1-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1236.0131483698972,\n" +
            "\t\t\t\t\"y\": 188.6855406267627,\n" +
            "\t\t\t\t\"index\": 115\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"57\",\n" +
            "\t\t\t\t\"sno\": \"FD185131BDF7D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.206\",\n" +
            "\t\t\t\t\"label\": \"19006公司总指挥部2-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1234.5131483698972,\n" +
            "\t\t\t\t\"y\": 219.98882861316332,\n" +
            "\t\t\t\t\"index\": 116\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"58\",\n" +
            "\t\t\t\t\"sno\": \"FD182031A1E8D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.11\",\n" +
            "\t\t\t\t\"label\": \"应急指挥中心2-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1234.0131483698972,\n" +
            "\t\t\t\t\"y\": 246.98882861316332,\n" +
            "\t\t\t\t\"index\": 117\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"9\",\n" +
            "\t\t\t\t\"sno\": \"FD152430C15BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.30\",\n" +
            "\t\t\t\t\"label\": \"721会议终端备用行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1234.5131483698972,\n" +
            "\t\t\t\t\"y\": 271.9888286131633,\n" +
            "\t\t\t\t\"index\": 118\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"3\",\n" +
            "\t\t\t\t\"sno\": \"2102350DLSDMJB001614\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"switch\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.255.253\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Core-2\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1331.5131483698972,\n" +
            "\t\t\t\t\"y\": 102.7815222414572,\n" +
            "\t\t\t\t\"index\": 119\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"7\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK7002171\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"switch\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.255.4\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-4\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1566.8310101561756,\n" +
            "\t\t\t\t\"y\": 186.48882861316332,\n" +
            "\t\t\t\t\"index\": 120\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"21\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFD7D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.50\",\n" +
            "\t\t\t\t\"label\": \"大兴公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1647.677468862605,\n" +
            "\t\t\t\t\"y\": 389.04681567350997,\n" +
            "\t\t\t\t\"index\": 121\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"25\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313003D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.66\",\n" +
            "\t\t\t\t\"label\": \"顺义公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1645.1230227653384,\n" +
            "\t\t\t\t\"y\": 261.4888286131633,\n" +
            "\t\t\t\t\"index\": 122\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"32\",\n" +
            "\t\t\t\t\"sno\": \"FD161631300BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.130\",\n" +
            "\t\t\t\t\"label\": \"培训中心行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1647.677468862605,\n" +
            "\t\t\t\t\"y\": 293.3356850884486,\n" +
            "\t\t\t\t\"index\": 123\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"33\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFA5D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.5.178\",\n" +
            "\t\t\t\t\"label\": \"物资公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1644.6230227653384,\n" +
            "\t\t\t\t\"y\": 318.8356850884486,\n" +
            "\t\t\t\t\"index\": 124\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"39\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CF3BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.162\",\n" +
            "\t\t\t\t\"label\": \"华商伟业行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1647.177468862605,\n" +
            "\t\t\t\t\"y\": 347.95030445872214,\n" +
            "\t\t\t\t\"index\": 125\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"50\",\n" +
            "\t\t\t\t\"sno\": \"FD150330B592D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.114\",\n" +
            "\t\t\t\t\"label\": \"重点工程生产办公室行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1648.177468862605,\n" +
            "\t\t\t\t\"y\": 365.04681567350997,\n" +
            "\t\t\t\t\"index\": 126\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"62\",\n" +
            "\t\t\t\t\"sno\": \"FD1628313219D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.65\",\n" +
            "\t\t\t\t\"label\": \"大兴公司八层应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1466.060460739652,\n" +
            "\t\t\t\t\"y\": 335.9888286131634,\n" +
            "\t\t\t\t\"index\": 127\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"77\",\n" +
            "\t\t\t\t\"sno\": \"FD1722315A9BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.81\",\n" +
            "\t\t\t\t\"label\": \"顺义公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1472.0464913182652,\n" +
            "\t\t\t\t\"y\": 280.367166732951,\n" +
            "\t\t\t\t\"index\": 128\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"81\",\n" +
            "\t\t\t\t\"sno\": \"82134341259BCN\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.242\",\n" +
            "\t\t\t\t\"label\": \"国家会议中心现场指挥部-hdx7000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1469.3310101561756,\n" +
            "\t\t\t\t\"y\": 310.9888286131634,\n" +
            "\t\t\t\t\"index\": 129\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"84\",\n" +
            "\t\t\t\t\"sno\": \"FD1748318D83D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.242\",\n" +
            "\t\t\t\t\"label\": \"照明中心广场指挥部-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1465.560460739652,\n" +
            "\t\t\t\t\"y\": 368.3356850884486,\n" +
            "\t\t\t\t\"index\": 130\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"85\",\n" +
            "\t\t\t\t\"sno\": \"FD182031A63DD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.81\",\n" +
            "\t\t\t\t\"label\": \"广场区现场指挥部-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1474.0464913182652,\n" +
            "\t\t\t\t\"y\": 251.6553828420977,\n" +
            "\t\t\t\t\"index\": 131\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"16\",\n" +
            "\t\t\t\t\"sno\": \"FD161330CE90D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.146\",\n" +
            "\t\t\t\t\"label\": \"亦庄公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1237.2196820948586,\n" +
            "\t\t\t\t\"y\": 528.7659636453737,\n" +
            "\t\t\t\t\"index\": 145\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"27\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFECD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.66\",\n" +
            "\t\t\t\t\"label\": \"经研院行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1512.9212316968903,\n" +
            "\t\t\t\t\"y\": 663.1604194177039,\n" +
            "\t\t\t\t\"index\": 146\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"28\",\n" +
            "\t\t\t\t\"sno\": \"FD1722315AD1D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.2\",\n" +
            "\t\t\t\t\"label\": \"电科院行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1511.9212316968903,\n" +
            "\t\t\t\t\"y\": 545.5026329759737,\n" +
            "\t\t\t\t\"index\": 147\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"29\",\n" +
            "\t\t\t\t\"sno\": \"821334411353CN\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.34\",\n" +
            "\t\t\t\t\"label\": \"工程公司行政-hdx8000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1511.9212316968903,\n" +
            "\t\t\t\t\"y\": 644.5026329759737,\n" +
            "\t\t\t\t\"index\": 148\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"36\",\n" +
            "\t\t\t\t\"sno\": \"821338409C1FCG\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.98\",\n" +
            "\t\t\t\t\"label\": \"承发包公司行政-hdx8000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1505.3533776447025,\n" +
            "\t\t\t\t\"y\": 719.7907964000262,\n" +
            "\t\t\t\t\"index\": 149\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"38\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CF56D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.18\",\n" +
            "\t\t\t\t\"label\": \"照明中心行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1504.8533776447025,\n" +
            "\t\t\t\t\"y\": 694.0026329759737,\n" +
            "\t\t\t\t\"index\": 150\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"46\",\n" +
            "\t\t\t\t\"sno\": \"8211170DE64FCN\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.242\",\n" +
            "\t\t\t\t\"label\": \"银杰公司行政-7000\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1227.6875572208237,\n" +
            "\t\t\t\t\"y\": 631.4751038621785,\n" +
            "\t\t\t\t\"index\": 151\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"47\",\n" +
            "\t\t\t\t\"sno\": \"FD162831325AD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.50\",\n" +
            "\t\t\t\t\"label\": \"谷新公司行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1511.4212316968903,\n" +
            "\t\t\t\t\"y\": 621.5744321580221,\n" +
            "\t\t\t\t\"index\": 152\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"60\",\n" +
            "\t\t\t\t\"sno\": \"FD183531B079D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.161\",\n" +
            "\t\t\t\t\"label\": \"电缆分公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1214.6875572208237,\n" +
            "\t\t\t\t\"y\": 720.2907964000262,\n" +
            "\t\t\t\t\"index\": 153\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"63\",\n" +
            "\t\t\t\t\"sno\": \"FD161330CDABD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.145\",\n" +
            "\t\t\t\t\"label\": \"亦庄公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1515.3533776447025,\n" +
            "\t\t\t\t\"y\": 570.5026329759737,\n" +
            "\t\t\t\t\"index\": 154\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"67\",\n" +
            "\t\t\t\t\"sno\": \"FD154430C49FD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.1\",\n" +
            "\t\t\t\t\"label\": \"电科院应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1516.3533776447025,\n" +
            "\t\t\t\t\"y\": 596.5744321580221,\n" +
            "\t\t\t\t\"index\": 155\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"68\",\n" +
            "\t\t\t\t\"sno\": \"FD16283132CFD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.17\",\n" +
            "\t\t\t\t\"label\": \"照明中心应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1512.9212316968903,\n" +
            "\t\t\t\t\"y\": 520.3797029916483,\n" +
            "\t\t\t\t\"index\": 156\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"70\",\n" +
            "\t\t\t\t\"sno\": \"FD182031A12ED7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.33\",\n" +
            "\t\t\t\t\"label\": \"工程公司应急-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1244.8417146528254,\n" +
            "\t\t\t\t\"y\": 491.01344962085926,\n" +
            "\t\t\t\t\"index\": 157\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"86\",\n" +
            "\t\t\t\t\"sno\": \"FD1628313200D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.178\",\n" +
            "\t\t\t\t\"label\": \"大雁楼备-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1223.3738395268604,\n" +
            "\t\t\t\t\"y\": 687.6604194177039,\n" +
            "\t\t\t\t\"index\": 158\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"87\",\n" +
            "\t\t\t\t\"sno\": \"FD16353141B6D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.146\",\n" +
            "\t\t\t\t\"label\": \"大雁楼主-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1358.8533776447025,\n" +
            "\t\t\t\t\"y\": 718.5026329759737,\n" +
            "\t\t\t\t\"index\": 159\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"88\",\n" +
            "\t\t\t\t\"sno\": \"FD1635314344D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.209\",\n" +
            "\t\t\t\t\"label\": \"国际会都现场指挥部-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1235.7196820948586,\n" +
            "\t\t\t\t\"y\": 595.0026329759737,\n" +
            "\t\t\t\t\"index\": 160\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"89\",\n" +
            "\t\t\t\t\"sno\": \"FD162831371ED7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.130\",\n" +
            "\t\t\t\t\"label\": \"通州大会议室-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1515.8533776447025,\n" +
            "\t\t\t\t\"y\": 490.51344962085926,\n" +
            "\t\t\t\t\"index\": 161\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"90\",\n" +
            "\t\t\t\t\"sno\": \"FD180331942FD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.193\",\n" +
            "\t\t\t\t\"label\": \"世园会现场指挥部备-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1236.7196820948586,\n" +
            "\t\t\t\t\"y\": 561.1604194177039,\n" +
            "\t\t\t\t\"index\": 162\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"92\",\n" +
            "\t\t\t\t\"sno\": \"FD1818319F6BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.130\",\n" +
            "\t\t\t\t\"label\": \"冬奥会西单办公室-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1223.3738395268604,\n" +
            "\t\t\t\t\"y\": 659.6604194177039,\n" +
            "\t\t\t\t\"index\": 163\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"5\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK5001438\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"switch\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.255.2\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-2\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1372.8533776447025,\n" +
            "\t\t\t\t\"y\": 419.46638426895214,\n" +
            "\t\t\t\t\"index\": 184\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"2\",\n" +
            "\t\t\t\t\"sno\": \"2102350DLSDMJB001611\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"switch\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.255.252\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Core-1\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1073.7284124916687,\n" +
            "\t\t\t\t\"y\": 53.281522241457196,\n" +
            "\t\t\t\t\"index\": 185\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"1\",\n" +
            "\t\t\t\t\"sno\": \"CR2161207027\",\n" +
            "\t\t\t\t\"icon\": \"MCU.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"MCU\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.0.100\",\n" +
            "\t\t\t\t\"label\": \"宝利通MCU备\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1074.2284124916687,\n" +
            "\t\t\t\t\"y\": -24.052627619612906,\n" +
            "\t\t\t\t\"index\": 186\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"0\",\n" +
            "\t\t\t\t\"sno\": \"CR2160816028\",\n" +
            "\t\t\t\t\"icon\": \"MCU.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"MCU\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.0.105\",\n" +
            "\t\t\t\t\"label\": \"宝利通MCU主\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1261.3533776447025,\n" +
            "\t\t\t\t\"y\": 12.448841437846113,\n" +
            "\t\t\t\t\"index\": 187\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"93\",\n" +
            "\t\t\t\t\"sno\": \"T0L832T4006ZBTT0L832T4001BBT\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"192.168.1.200\",\n" +
            "\t\t\t\t\"label\": \"小鸟拼接处理器\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1664.3310101561756,\n" +
            "\t\t\t\t\"y\": 561.6630962825516,\n" +
            "\t\t\t\t\"index\": 188\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"edges\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"0\",\n" +
            "\t\t\t\t\"target\": \"2\",\n" +
            "\t\t\t\t\"description\": \"宝利通MCU主:管理LAN ,QM-Video-Core-1:G0/0/8\",\n" +
            "\t\t\t\t\"id\": \"edge-0\",\n" +
            "\t\t\t\t\"index\": 0\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"0\",\n" +
            "\t\t\t\t\"description\": \"宝利通MCU主:信令LAN ,QM-Video-Core-1:G0/0/10\",\n" +
            "\t\t\t\t\"id\": \"edge-0-1\",\n" +
            "\t\t\t\t\"index\": 1\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"1\",\n" +
            "\t\t\t\t\"target\": \"2\",\n" +
            "\t\t\t\t\"description\": \"宝利通MCU备:管理LAN,QM-Video-Core-1:G0/0/12\",\n" +
            "\t\t\t\t\"id\": \"edge-1\",\n" +
            "\t\t\t\t\"index\": 2\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"3\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/27,QM-Video-Core-2:G0/0/27\",\n" +
            "\t\t\t\t\"id\": \"edge-2\",\n" +
            "\t\t\t\t\"index\": 3\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"2\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/28,QM-Video-Core-2:G0/0/28\",\n" +
            "\t\t\t\t\"id\": \"edge-3\",\n" +
            "\t\t\t\t\"index\": 4\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"8\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/7,721会议终端主用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-12\",\n" +
            "\t\t\t\t\"index\": 5\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"9\",\n" +
            "\t\t\t\t\"description\": \"core-switch-2:G0/0/7,721会议终端备用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-13\",\n" +
            "\t\t\t\t\"index\": 6\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"51\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/1,应急指挥中心1:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-54\",\n" +
            "\t\t\t\t\"index\": 7\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"52\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/3,639会议终端主用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-55\",\n" +
            "\t\t\t\t\"index\": 8\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"53\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/5,639会议终端备用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-56\",\n" +
            "\t\t\t\t\"index\": 9\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"54\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/9,B座二层1:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-57\",\n" +
            "\t\t\t\t\"index\": 10\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"55\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/11,B座二层2:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-58\",\n" +
            "\t\t\t\t\"index\": 11\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"56\",\n" +
            "\t\t\t\t\"description\": \"core-switch-2:G0/0/1,19006公司总指挥部1:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-59\",\n" +
            "\t\t\t\t\"index\": 12\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"57\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/3,19006公司总指挥部2:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-59-1\",\n" +
            "\t\t\t\t\"index\": 13\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"58\",\n" +
            "\t\t\t\t\"description\": \"core-switch-1:G0/0/5,应急指挥中心2:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-60\",\n" +
            "\t\t\t\t\"index\": 14\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"4\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/21,QM-Video-Access-1:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-4\",\n" +
            "\t\t\t\t\"index\": 15\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"4\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/21,QM-Video-Access-1:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-5\",\n" +
            "\t\t\t\t\"index\": 16\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"11\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/41,城区公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-14\",\n" +
            "\t\t\t\t\"index\": 17\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"12\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/11,朝阳公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-15\",\n" +
            "\t\t\t\t\"index\": 18\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"13\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/19,海淀公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-16\",\n" +
            "\t\t\t\t\"index\": 19\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"14\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/3,丰台公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-17\",\n" +
            "\t\t\t\t\"index\": 20\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"15\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/17,石景山公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-18\",\n" +
            "\t\t\t\t\"index\": 21\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"17\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/43,通州公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-20\",\n" +
            "\t\t\t\t\"index\": 22\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"18\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/37,昌平公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-21\",\n" +
            "\t\t\t\t\"index\": 23\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"19\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/7,门头沟公司双流(行政):LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-22\",\n" +
            "\t\t\t\t\"index\": 24\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"20\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/21,房山公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-23\",\n" +
            "\t\t\t\t\"index\": 25\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"22\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/23,平谷公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-25\",\n" +
            "\t\t\t\t\"index\": 26\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"23\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/5,怀柔公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-26\",\n" +
            "\t\t\t\t\"index\": 27\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"24\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/1,密云公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-27\",\n" +
            "\t\t\t\t\"index\": 28\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"26\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/45,延庆公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-29\",\n" +
            "\t\t\t\t\"index\": 29\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"30\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/33,检修公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-33\",\n" +
            "\t\t\t\t\"index\": 30\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"31\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/39,信通公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-34\",\n" +
            "\t\t\t\t\"index\": 31\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"37\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/29,电缆分公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-40\",\n" +
            "\t\t\t\t\"index\": 32\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"59\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/37,昌平公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-61\",\n" +
            "\t\t\t\t\"index\": 33\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"61\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/13,朝阳公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-63\",\n" +
            "\t\t\t\t\"index\": 34\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"65\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/21,房山公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-67\",\n" +
            "\t\t\t\t\"index\": 35\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"66\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/3,丰台公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-68\",\n" +
            "\t\t\t\t\"index\": 36\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"69\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/31,检修公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-71\",\n" +
            "\t\t\t\t\"index\": 37\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"72\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/19,海淀公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-74\",\n" +
            "\t\t\t\t\"index\": 38\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"73\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/5,怀柔公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-75\",\n" +
            "\t\t\t\t\"index\": 39\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"74\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/7,门头沟新会场行政（应急）:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-76\",\n" +
            "\t\t\t\t\"index\": 40\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"75\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/1,密云公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-77\",\n" +
            "\t\t\t\t\"index\": 41\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"76\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/23,平谷公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-78\",\n" +
            "\t\t\t\t\"index\": 42\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"78\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/47,通州公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-80\",\n" +
            "\t\t\t\t\"index\": 43\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"79\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/41,城区公司五层:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-81\",\n" +
            "\t\t\t\t\"index\": 44\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"6\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/23,QM-Video-Access-3:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-8\",\n" +
            "\t\t\t\t\"index\": 45\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"6\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/23,QM-Video-Access-3:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-9\",\n" +
            "\t\t\t\t\"index\": 46\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"34\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/25,客服中心行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-37\",\n" +
            "\t\t\t\t\"index\": 47\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"35\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/27,电动车公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-38\",\n" +
            "\t\t\t\t\"index\": 48\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"40\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/21,华商远大行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-43\",\n" +
            "\t\t\t\t\"index\": 49\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"41\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/37,华商三优行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-44\",\n" +
            "\t\t\t\t\"index\": 50\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"42\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/29,华商能源行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-45\",\n" +
            "\t\t\t\t\"index\": 51\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"43\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/35,华商电灯行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-46\",\n" +
            "\t\t\t\t\"index\": 52\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"44\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/31,吉北咨询行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-47\",\n" +
            "\t\t\t\t\"index\": 53\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"45\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/17,京电设计行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-48\",\n" +
            "\t\t\t\t\"index\": 54\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"48\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/13,中电联汽车行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-51\",\n" +
            "\t\t\t\t\"index\": 55\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"49\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/33,公司巡察办行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-52\",\n" +
            "\t\t\t\t\"index\": 56\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"64\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/25,客服中心应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-65\",\n" +
            "\t\t\t\t\"index\": 57\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"71\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/21,华商远大应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-73\",\n" +
            "\t\t\t\t\"index\": 58\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"80\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/43,昌平19006分指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-82\",\n" +
            "\t\t\t\t\"index\": 59\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"82\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/3,芦城应急抢修中心:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-84\",\n" +
            "\t\t\t\t\"index\": 60\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"83\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/47,大兴机场东:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-85\",\n" +
            "\t\t\t\t\"index\": 61\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"91\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/5,会城门:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-93\",\n" +
            "\t\t\t\t\"index\": 62\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"7\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/24,QM-Video-Access-4:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-10\",\n" +
            "\t\t\t\t\"index\": 132\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"7\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/24,QM-Video-Access-4:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-11\",\n" +
            "\t\t\t\t\"index\": 133\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"21\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/23,大兴公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-24\",\n" +
            "\t\t\t\t\"index\": 134\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"25\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/25,顺义公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-28\",\n" +
            "\t\t\t\t\"index\": 135\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"32\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/33,培训中心行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-35\",\n" +
            "\t\t\t\t\"index\": 136\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"33\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/7,物资公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-36\",\n" +
            "\t\t\t\t\"index\": 137\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"39\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/37,华商伟业行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-42\",\n" +
            "\t\t\t\t\"index\": 138\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"39\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/19,重点工程生产办公室行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-53\",\n" +
            "\t\t\t\t\"index\": 139\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"62\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/23,大兴公司八层应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-63-1\",\n" +
            "\t\t\t\t\"index\": 140\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"77\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/25,顺义公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-79\",\n" +
            "\t\t\t\t\"index\": 141\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"81\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/17,国家会议中心现场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-83\",\n" +
            "\t\t\t\t\"index\": 142\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"84\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/47,照明中心广场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-86\",\n" +
            "\t\t\t\t\"index\": 143\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"85\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/27,广场区现场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-87\",\n" +
            "\t\t\t\t\"index\": 144\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"5\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/22,QM-Video-Access-2:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-6\",\n" +
            "\t\t\t\t\"index\": 164\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"5\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/22,QM-Video-Access-2:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-7\",\n" +
            "\t\t\t\t\"index\": 165\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"16\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/3,亦庄公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-19\",\n" +
            "\t\t\t\t\"index\": 166\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"28\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/17,电科院行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-31\",\n" +
            "\t\t\t\t\"index\": 167\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"29\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/21,工程公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-32\",\n" +
            "\t\t\t\t\"index\": 168\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"36\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/29,承发包公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-39\",\n" +
            "\t\t\t\t\"index\": 169\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"38\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/19,照明中心行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-41\",\n" +
            "\t\t\t\t\"index\": 170\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"46\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/47,银杰公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-49\",\n" +
            "\t\t\t\t\"index\": 171\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"47\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/23,谷新公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-50\",\n" +
            "\t\t\t\t\"index\": 172\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"60\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/5,电缆分公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-62\",\n" +
            "\t\t\t\t\"index\": 173\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"63\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/3,亦庄公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-64\",\n" +
            "\t\t\t\t\"index\": 174\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"67\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/17,电科院应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-69\",\n" +
            "\t\t\t\t\"index\": 175\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"68\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/19,照明中心应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-70\",\n" +
            "\t\t\t\t\"index\": 176\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"70\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/21,工程公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-72\",\n" +
            "\t\t\t\t\"index\": 177\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"86\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/39,大雁楼备:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-88\",\n" +
            "\t\t\t\t\"index\": 178\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"87\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/35,大雁楼主:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-89\",\n" +
            "\t\t\t\t\"index\": 179\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"88\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/11,国际会都现场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-90\",\n" +
            "\t\t\t\t\"index\": 180\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"89\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/1,通州大会议室:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-91\",\n" +
            "\t\t\t\t\"index\": 181\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"90\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/9,世园会现场指挥部备:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-92\",\n" +
            "\t\t\t\t\"index\": 182\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"92\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/33,冬奥会西单办公室:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-94\",\n" +
            "\t\t\t\t\"index\": 183\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "    }\n" +
            "}";

    public Object drawByAll(String deptid) {
        return Json.fromJson(testtemp);
    }
}
