package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Result;
import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.Topo.Topo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

import java.util.List;

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
            "\t\t\t\t\"id\": \"0\",\n" +
            "\t\t\t\t\"sno\": \"CR2160816028\",\n" +
            "\t\t\t\t\"icon\": \"MCU.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"MCU\",\n" +
            "\t\t\t\t\"lock\": true,\n" +
            "\t\t\t\t\"ip\": \"196.168.0.105\",\n" +
            "\t\t\t\t\"label\": \"宝利通MCU主\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 831.4739428932332,\n" +
            "\t\t\t\t\"y\": 64.30727373449064,\n" +
            "\t\t\t\t\"index\": 0\n" +
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
            "\t\t\t\t\"x\": 1103.6076384430771,\n" +
            "\t\t\t\t\"y\": 63.807273734490636,\n" +
            "\t\t\t\t\"index\": 1\n" +
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
            "\t\t\t\t\"x\": 832.4739428932332,\n" +
            "\t\t\t\t\"y\": 205.74452108271453,\n" +
            "\t\t\t\t\"index\": 2\n" +
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
            "\t\t\t\t\"x\": 1113.175492495265,\n" +
            "\t\t\t\t\"y\": 206.24452108271453,\n" +
            "\t\t\t\t\"index\": 3\n" +
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
            "\t\t\t\t\"x\": 649.8039177344588,\n" +
            "\t\t\t\t\"y\": 358.1187524742829,\n" +
            "\t\t\t\t\"index\": 4\n" +
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
            "\t\t\t\t\"x\": 1041.6076384430771,\n" +
            "\t\t\t\t\"y\": 359.2414960207452,\n" +
            "\t\t\t\t\"index\": 5\n" +
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
            "\t\t\t\t\"x\": 302.9650274222234,\n" +
            "\t\t\t\t\"y\": 359.5772018863256,\n" +
            "\t\t\t\t\"index\": 6\n" +
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
            "\t\t\t\t\"x\": 1439.3015929108506,\n" +
            "\t\t\t\t\"y\": 359.7414960207452,\n" +
            "\t\t\t\t\"index\": 7\n" +
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
            "\t\t\t\t\"x\": 599.2872457068486,\n" +
            "\t\t\t\t\"y\": 255.24452108271453,\n" +
            "\t\t\t\t\"index\": 8\n" +
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
            "\t\t\t\t\"x\": 593.142728778902,\n" +
            "\t\t\t\t\"y\": 272.5772018863256,\n" +
            "\t\t\t\t\"index\": 9\n" +
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
            "\t\t\t\t\"x\": 753.3609286933563,\n" +
            "\t\t\t\t\"y\": 508.618752474283,\n" +
            "\t\t\t\t\"index\": 10\n" +
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
            "\t\t\t\t\"x\": 750.8394655007169,\n" +
            "\t\t\t\t\"y\": 491.83274683114456,\n" +
            "\t\t\t\t\"index\": 11\n" +
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
            "\t\t\t\t\"x\": 750.3394655007169,\n" +
            "\t\t\t\t\"y\": 639.5963081300717,\n" +
            "\t\t\t\t\"index\": 12\n" +
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
            "\t\t\t\t\"x\": 743.8228008797159,\n" +
            "\t\t\t\t\"y\": 670.943164605357,\n" +
            "\t\t\t\t\"index\": 13\n" +
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
            "\t\t\t\t\"x\": 560.3215491705362,\n" +
            "\t\t\t\t\"y\": 525.3154644878823,\n" +
            "\t\t\t\t\"index\": 14\n" +
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
            "\t\t\t\t\"x\": 928.4739428932332,\n" +
            "\t\t\t\t\"y\": 484.5655186239388,\n" +
            "\t\t\t\t\"index\": 15\n" +
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
            "\t\t\t\t\"x\": 560.3215491705362,\n" +
            "\t\t\t\t\"y\": 664.5963081300717,\n" +
            "\t\t\t\t\"index\": 16\n" +
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
            "\t\t\t\t\"x\": 559.8215491705362,\n" +
            "\t\t\t\t\"y\": 621.443164605357,\n" +
            "\t\t\t\t\"index\": 17\n" +
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
            "\t\t\t\t\"x\": 563.8215491705362,\n" +
            "\t\t\t\t\"y\": 566.3327468311445,\n" +
            "\t\t\t\t\"index\": 18\n" +
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
            "\t\t\t\t\"x\": 752.8609286933563,\n" +
            "\t\t\t\t\"y\": 467.33274683114456,\n" +
            "\t\t\t\t\"index\": 19\n" +
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
            "\t\t\t\t\"x\": 1519.14805161728,\n" +
            "\t\t\t\t\"y\": 543.2985121746842,\n" +
            "\t\t\t\t\"index\": 20\n" +
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
            "\t\t\t\t\"x\": 750.8394655007169,\n" +
            "\t\t\t\t\"y\": 541.3327468311445,\n" +
            "\t\t\t\t\"index\": 21\n" +
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
            "\t\t\t\t\"x\": 735.8178871558455,\n" +
            "\t\t\t\t\"y\": 583.1187524742829,\n" +
            "\t\t\t\t\"index\": 22\n" +
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
            "\t\t\t\t\"x\": 745.8228008797159,\n" +
            "\t\t\t\t\"y\": 565.8327468311445,\n" +
            "\t\t\t\t\"index\": 23\n" +
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
            "\t\t\t\t\"x\": 1517.5936055200134,\n" +
            "\t\t\t\t\"y\": 434.74149602074533,\n" +
            "\t\t\t\t\"index\": 24\n" +
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
            "\t\t\t\t\"x\": 731.2350582726483,\n" +
            "\t\t\t\t\"y\": 599.3154644878823,\n" +
            "\t\t\t\t\"index\": 25\n" +
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
            "\t\t\t\t\"x\": 924.2674091682718,\n" +
            "\t\t\t\t\"y\": 638.5547019790531,\n" +
            "\t\t\t\t\"index\": 26\n" +
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
            "\t\t\t\t\"x\": 1159.675492495265,\n" +
            "\t\t\t\t\"y\": 516.0547019790531,\n" +
            "\t\t\t\t\"index\": 27\n" +
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
            "\t\t\t\t\"x\": 1156.5246306305771,\n" +
            "\t\t\t\t\"y\": 607.2414960207452,\n" +
            "\t\t\t\t\"index\": 28\n" +
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
            "\t\t\t\t\"x\": 744.8228008797159,\n" +
            "\t\t\t\t\"y\": 525.3154644878823,\n" +
            "\t\t\t\t\"index\": 29\n" +
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
            "\t\t\t\t\"x\": 736.3178871558455,\n" +
            "\t\t\t\t\"y\": 623.8154644878823,\n" +
            "\t\t\t\t\"index\": 30\n" +
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
            "\t\t\t\t\"x\": 1520.14805161728,\n" +
            "\t\t\t\t\"y\": 466.5883524960305,\n" +
            "\t\t\t\t\"index\": 31\n" +
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
            "\t\t\t\t\"x\": 1517.0936055200134,\n" +
            "\t\t\t\t\"y\": 492.0883524960305,\n" +
            "\t\t\t\t\"index\": 32\n" +
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
            "\t\t\t\t\"x\": 379.6287593575153,\n" +
            "\t\t\t\t\"y\": 602.7863421031301,\n" +
            "\t\t\t\t\"index\": 33\n" +
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
            "\t\t\t\t\"x\": 219.2598368777255,\n" +
            "\t\t\t\t\"y\": 442.8138712169254,\n" +
            "\t\t\t\t\"index\": 34\n" +
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
            "\t\t\t\t\"x\": 1153.1076384430771,\n" +
            "\t\t\t\t\"y\": 664.219051676534,\n" +
            "\t\t\t\t\"index\": 35\n" +
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
            "\t\t\t\t\"x\": 563.8215491705362,\n" +
            "\t\t\t\t\"y\": 582.6187524742829,\n" +
            "\t\t\t\t\"index\": 36\n" +
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
            "\t\t\t\t\"x\": 1152.6076384430771,\n" +
            "\t\t\t\t\"y\": 631.7414960207452,\n" +
            "\t\t\t\t\"index\": 37\n" +
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
            "\t\t\t\t\"x\": 1519.64805161728,\n" +
            "\t\t\t\t\"y\": 521.2029718663039,\n" +
            "\t\t\t\t\"index\": 38\n" +
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
            "\t\t\t\t\"x\": 205.4650274222234,\n" +
            "\t\t\t\t\"y\": 560.4716576586555,\n" +
            "\t\t\t\t\"index\": 39\n" +
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
            "\t\t\t\t\"x\": 211.8340498778835,\n" +
            "\t\t\t\t\"y\": 585.4716576586555,\n" +
            "\t\t\t\t\"index\": 40\n" +
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
            "\t\t\t\t\"x\": 400.4650274222234,\n" +
            "\t\t\t\t\"y\": 440.69094123260004,\n" +
            "\t\t\t\t\"index\": 41\n" +
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
            "\t\t\t\t\"x\": 398.6856238775675,\n" +
            "\t\t\t\t\"y\": 461.3246878618111,\n" +
            "\t\t\t\t\"index\": 42\n" +
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
            "\t\t\t\t\"x\": 398.1856238775675,\n" +
            "\t\t\t\t\"y\": 491.3138712169255,\n" +
            "\t\t\t\t\"index\": 43\n" +
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
            "\t\t\t\t\"x\": 397.6856238775675,\n" +
            "\t\t\t\t\"y\": 517.8856703989738,\n" +
            "\t\t\t\t\"index\": 44\n" +
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
            "\t\t\t\t\"x\": 924.2674091682718,\n" +
            "\t\t\t\t\"y\": 546.2324476288932,\n" +
            "\t\t\t\t\"index\": 45\n" +
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
            "\t\t\t\t\"x\": 1159.175492495265,\n" +
            "\t\t\t\t\"y\": 583.7414960207452,\n" +
            "\t\t\t\t\"index\": 46\n" +
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
            "\t\t\t\t\"x\": 389.8200804564968,\n" +
            "\t\t\t\t\"y\": 570.487206157118,\n" +
            "\t\t\t\t\"index\": 47\n" +
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
            "\t\t\t\t\"x\": 397.1856238775675,\n" +
            "\t\t\t\t\"y\": 542.3856703989738,\n" +
            "\t\t\t\t\"index\": 48\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"50\",\n" +
            "\t\t\t\t\"sno\": \"FD184631BA2BD7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.6.114\",\n" +
            "\t\t\t\t\"label\": \"重点工程生产办公室行政-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 1344.5170740729402,\n" +
            "\t\t\t\t\"y\": 548.7994830810917,\n" +
            "\t\t\t\t\"index\": 49\n" +
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
            "\t\t\t\t\"x\": 1318.0488518669915,\n" +
            "\t\t\t\t\"y\": 256.2445210827145,\n" +
            "\t\t\t\t\"index\": 50\n" +
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
            "\t\t\t\t\"x\": 599.7872457068486,\n" +
            "\t\t\t\t\"y\": 231.24452108271453,\n" +
            "\t\t\t\t\"index\": 51\n" +
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
            "\t\t\t\t\"x\": 1315.5488518669915,\n" +
            "\t\t\t\t\"y\": 239.78450825803174,\n" +
            "\t\t\t\t\"index\": 52\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"54\",\n" +
            "\t\t\t\t\"sno\": \"FD154430C863D7\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"type\": \"node\",\n" +
            "\t\t\t\t\"_type\": \"meeting_terminal\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.40\",\n" +
            "\t\t\t\t\"label\": \"B座二层1-group550\",\n" +
            "\t\t\t\t\"description\": \"\",\n" +
            "\t\t\t\t\"x\": 600.2872457068486,\n" +
            "\t\t\t\t\"y\": 214.78450825803174,\n" +
            "\t\t\t\t\"index\": 53\n" +
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
            "\t\t\t\t\"x\": 1315.0488518669915,\n" +
            "\t\t\t\t\"y\": 297.5772018863256,\n" +
            "\t\t\t\t\"index\": 54\n" +
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
            "\t\t\t\t\"x\": 1317.0488518669915,\n" +
            "\t\t\t\t\"y\": 223.0772018863256,\n" +
            "\t\t\t\t\"index\": 55\n" +
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
            "\t\t\t\t\"x\": 1317.5488518669915,\n" +
            "\t\t\t\t\"y\": 206.74452108271453,\n" +
            "\t\t\t\t\"index\": 56\n" +
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
            "\t\t\t\t\"x\": 1318.5488518669915,\n" +
            "\t\t\t\t\"y\": 279.2445210827145,\n" +
            "\t\t\t\t\"index\": 57\n" +
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
            "\t\t\t\t\"x\": 560.3215491705362,\n" +
            "\t\t\t\t\"y\": 500.81546448788237,\n" +
            "\t\t\t\t\"index\": 58\n" +
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
            "\t\t\t\t\"x\": 923.2674091682718,\n" +
            "\t\t\t\t\"y\": 613.9129090443615,\n" +
            "\t\t\t\t\"index\": 59\n" +
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
            "\t\t\t\t\"x\": 554.8039177344588,\n" +
            "\t\t\t\t\"y\": 432.6187524742829,\n" +
            "\t\t\t\t\"index\": 60\n" +
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
            "\t\t\t\t\"x\": 1338.531043494327,\n" +
            "\t\t\t\t\"y\": 502.0903428642874,\n" +
            "\t\t\t\t\"index\": 61\n" +
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
            "\t\t\t\t\"x\": 1163.1076384430771,\n" +
            "\t\t\t\t\"y\": 541.0547019790531,\n" +
            "\t\t\t\t\"index\": 62\n" +
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
            "\t\t\t\t\"x\": 218.2598368777255,\n" +
            "\t\t\t\t\"y\": 500.07720188632555,\n" +
            "\t\t\t\t\"index\": 63\n" +
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
            "\t\t\t\t\"x\": 736.8178871558455,\n" +
            "\t\t\t\t\"y\": 689.0963081300717,\n" +
            "\t\t\t\t\"index\": 64\n" +
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
            "\t\t\t\t\"x\": 562.3215491705362,\n" +
            "\t\t\t\t\"y\": 689.5963081300717,\n" +
            "\t\t\t\t\"index\": 65\n" +
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
            "\t\t\t\t\"x\": 1164.1076384430771,\n" +
            "\t\t\t\t\"y\": 560.7041920135222,\n" +
            "\t\t\t\t\"index\": 66\n" +
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
            "\t\t\t\t\"x\": 1160.675492495265,\n" +
            "\t\t\t\t\"y\": 490.93177199472785,\n" +
            "\t\t\t\t\"index\": 67\n" +
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
            "\t\t\t\t\"x\": 745.3228008797159,\n" +
            "\t\t\t\t\"y\": 423.118752474283,\n" +
            "\t\t\t\t\"index\": 68\n" +
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
            "\t\t\t\t\"x\": 935.5959754512,\n" +
            "\t\t\t\t\"y\": 460.5655186239388,\n" +
            "\t\t\t\t\"index\": 69\n" +
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
            "\t\t\t\t\"x\": 212.3340498778835,\n" +
            "\t\t\t\t\"y\": 528.7863421031301,\n" +
            "\t\t\t\t\"index\": 70\n" +
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
            "\t\t\t\t\"x\": 751.3394655007169,\n" +
            "\t\t\t\t\"y\": 448.118752474283,\n" +
            "\t\t\t\t\"index\": 71\n" +
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
            "\t\t\t\t\"x\": 560.8215491705362,\n" +
            "\t\t\t\t\"y\": 645.943164605357,\n" +
            "\t\t\t\t\"index\": 72\n" +
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
            "\t\t\t\t\"x\": 580.3215491705362,\n" +
            "\t\t\t\t\"y\": 550.8154644878823,\n" +
            "\t\t\t\t\"index\": 73\n" +
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
            "\t\t\t\t\"x\": 555.3039177344588,\n" +
            "\t\t\t\t\"y\": 481.618752474283,\n" +
            "\t\t\t\t\"index\": 74\n" +
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
            "\t\t\t\t\"x\": 564.8215491705362,\n" +
            "\t\t\t\t\"y\": 679.8732653813581,\n" +
            "\t\t\t\t\"index\": 75\n" +
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
            "\t\t\t\t\"x\": 1344.5170740729402,\n" +
            "\t\t\t\t\"y\": 453.61983414053276,\n" +
            "\t\t\t\t\"index\": 76\n" +
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
            "\t\t\t\t\"x\": 564.3215491705362,\n" +
            "\t\t\t\t\"y\": 599.8154644878823,\n" +
            "\t\t\t\t\"index\": 77\n" +
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
            "\t\t\t\t\"x\": 560.8215491705362,\n" +
            "\t\t\t\t\"y\": 457.118752474283,\n" +
            "\t\t\t\t\"index\": 78\n" +
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
            "\t\t\t\t\"x\": 218.7598368777255,\n" +
            "\t\t\t\t\"y\": 468.8856703989738,\n" +
            "\t\t\t\t\"index\": 79\n" +
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
            "\t\t\t\t\"x\": 1341.8015929108506,\n" +
            "\t\t\t\t\"y\": 484.2414960207453,\n" +
            "\t\t\t\t\"index\": 80\n" +
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
            "\t\t\t\t\"x\": 219.2598368777255,\n" +
            "\t\t\t\t\"y\": 603.2863421031301,\n" +
            "\t\t\t\t\"index\": 81\n" +
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
            "\t\t\t\t\"x\": 230.3337550879831,\n" +
            "\t\t\t\t\"y\": 417.8138712169254,\n" +
            "\t\t\t\t\"index\": 82\n" +
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
            "\t\t\t\t\"x\": 1338.031043494327,\n" +
            "\t\t\t\t\"y\": 532.1757824035265,\n" +
            "\t\t\t\t\"index\": 83\n" +
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
            "\t\t\t\t\"x\": 1346.5170740729402,\n" +
            "\t\t\t\t\"y\": 424.9080502496796,\n" +
            "\t\t\t\t\"index\": 84\n" +
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
            "\t\t\t\t\"x\": 925.2674091682718,\n" +
            "\t\t\t\t\"y\": 589.9129090443615,\n" +
            "\t\t\t\t\"index\": 85\n" +
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
            "\t\t\t\t\"x\": 925.7674091682718,\n" +
            "\t\t\t\t\"y\": 668.9960089278204,\n" +
            "\t\t\t\t\"index\": 86\n" +
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
            "\t\t\t\t\"x\": 924.2674091682718,\n" +
            "\t\t\t\t\"y\": 527.0184532720316,\n" +
            "\t\t\t\t\"index\": 87\n" +
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
            "\t\t\t\t\"x\": 1163.6076384430771,\n" +
            "\t\t\t\t\"y\": 461.0655186239388,\n" +
            "\t\t\t\t\"index\": 88\n" +
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
            "\t\t\t\t\"x\": 928.4739428932332,\n" +
            "\t\t\t\t\"y\": 502.51845327203165,\n" +
            "\t\t\t\t\"index\": 89\n" +
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
            "\t\t\t\t\"x\": 404.3200804564968,\n" +
            "\t\t\t\t\"y\": 416.19094123260004,\n" +
            "\t\t\t\t\"index\": 90\n" +
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
            "\t\t\t\t\"x\": 923.7674091682718,\n" +
            "\t\t\t\t\"y\": 571.8269217846798,\n" +
            "\t\t\t\t\"index\": 91\n" +
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
            "\t\t\t\t\"x\": 1395.5170740729402,\n" +
            "\t\t\t\t\"y\": 645.443164605357,\n" +
            "\t\t\t\t\"index\": 92\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"edges\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"0\",\n" +
            "\t\t\t\t\"target\": \"2\",\n" +
            "\t\t\t\t\"description\": \"宝利通MCU主:管理LAN ,QM-Video-Core-1:G0/0/8\",\n" +
            "\t\t\t\t\"id\": \"edge-0\",\n" +
            "\t\t\t\t\"index\": 93\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"0\",\n" +
            "\t\t\t\t\"description\": \"宝利通MCU主:信令LAN ,QM-Video-Core-1:G0/0/10\",\n" +
            "\t\t\t\t\"id\": \"edge-0-1\",\n" +
            "\t\t\t\t\"index\": 94\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"1\",\n" +
            "\t\t\t\t\"target\": \"3\",\n" +
            "\t\t\t\t\"description\": \"宝利通MCU备:管理LAN,QM-Video-Core-2:G0/0/13\",\n" +
            "\t\t\t\t\"id\": \"edge-1\",\n" +
            "\t\t\t\t\"index\": 95\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"3\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/27,QM-Video-Core-2:G0/0/27\",\n" +
            "\t\t\t\t\"id\": \"edge-2\",\n" +
            "\t\t\t\t\"index\": 96\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"2\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/28,QM-Video-Core-2:G0/0/28\",\n" +
            "\t\t\t\t\"id\": \"edge-3\",\n" +
            "\t\t\t\t\"index\": 97\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"8\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/9,721会议终端主用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-12\",\n" +
            "\t\t\t\t\"index\": 98\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"9\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/9,721会议终端备用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-13\",\n" +
            "\t\t\t\t\"index\": 99\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"52\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/7,639会议终端主用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-55\",\n" +
            "\t\t\t\t\"index\": 100\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"54\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/4,B座二层1:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-57\",\n" +
            "\t\t\t\t\"index\": 101\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"4\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/21,QM-Video-Access-1:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-4\",\n" +
            "\t\t\t\t\"index\": 102\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"6\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/23,QM-Video-Access-3:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-8\",\n" +
            "\t\t\t\t\"index\": 103\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"7\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/24,QM-Video-Access-4:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-10\",\n" +
            "\t\t\t\t\"index\": 104\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"5\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/22,QM-Video-Access-2:G0/0/49\",\n" +
            "\t\t\t\t\"id\": \"edge-6\",\n" +
            "\t\t\t\t\"index\": 105\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"51\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/11,应急指挥中心1:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-54\",\n" +
            "\t\t\t\t\"index\": 106\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"53\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/7,639会议终端备用行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-56\",\n" +
            "\t\t\t\t\"index\": 107\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"55\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/4,B座二层2:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-58\",\n" +
            "\t\t\t\t\"index\": 108\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"56\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/1,19006公司总指挥部1:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-59\",\n" +
            "\t\t\t\t\"index\": 109\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"57\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/3,19006公司总指挥部2:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-59-1\",\n" +
            "\t\t\t\t\"index\": 110\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"58\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-1:G0/0/11,应急指挥中心2:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-60\",\n" +
            "\t\t\t\t\"index\": 111\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"4\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/21,QM-Video-Access-1:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-5\",\n" +
            "\t\t\t\t\"index\": 112\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"6\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/23,QM-Video-Access-3:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-9\",\n" +
            "\t\t\t\t\"index\": 113\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"7\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/24,QM-Video-Access-4:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-11\",\n" +
            "\t\t\t\t\"index\": 114\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"5\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Core-2:G0/0/22,QM-Video-Access-2:G0/0/51\",\n" +
            "\t\t\t\t\"id\": \"edge-7\",\n" +
            "\t\t\t\t\"index\": 115\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"11\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/41,城区公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-14\",\n" +
            "\t\t\t\t\"index\": 116\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"12\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/11,朝阳公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-15\",\n" +
            "\t\t\t\t\"index\": 117\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"13\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/19,海淀公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-16\",\n" +
            "\t\t\t\t\"index\": 118\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"14\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/3,丰台公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-17\",\n" +
            "\t\t\t\t\"index\": 119\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"15\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/17,石景山公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-18\",\n" +
            "\t\t\t\t\"index\": 120\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"17\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/43,通州公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-20\",\n" +
            "\t\t\t\t\"index\": 121\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"18\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/37,昌平公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-21\",\n" +
            "\t\t\t\t\"index\": 122\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"19\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/7,门头沟公司双流(行政):LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-22\",\n" +
            "\t\t\t\t\"index\": 123\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"20\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/21,房山公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-23\",\n" +
            "\t\t\t\t\"index\": 124\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"22\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/23,平谷公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-25\",\n" +
            "\t\t\t\t\"index\": 125\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"23\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/5,怀柔公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-26\",\n" +
            "\t\t\t\t\"index\": 126\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"24\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/1,密云公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-27\",\n" +
            "\t\t\t\t\"index\": 127\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"26\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/45,延庆公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-29\",\n" +
            "\t\t\t\t\"index\": 128\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"30\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/33,检修公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-33\",\n" +
            "\t\t\t\t\"index\": 129\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"31\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/39,信通公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-34\",\n" +
            "\t\t\t\t\"index\": 130\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"37\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/29,电缆分公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-40\",\n" +
            "\t\t\t\t\"index\": 131\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"59\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/37,昌平公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-61\",\n" +
            "\t\t\t\t\"index\": 132\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"61\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/13,朝阳公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-63\",\n" +
            "\t\t\t\t\"index\": 133\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"65\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/21,房山公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-67\",\n" +
            "\t\t\t\t\"index\": 134\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"66\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/3,丰台公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-68\",\n" +
            "\t\t\t\t\"index\": 135\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"69\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/31,检修公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-71\",\n" +
            "\t\t\t\t\"index\": 136\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"72\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/19,海淀公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-74\",\n" +
            "\t\t\t\t\"index\": 137\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"73\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/5,怀柔公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-75\",\n" +
            "\t\t\t\t\"index\": 138\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"74\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/7,门头沟新会场行政（应急）:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-76\",\n" +
            "\t\t\t\t\"index\": 139\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"75\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/1,密云公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-77\",\n" +
            "\t\t\t\t\"index\": 140\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"76\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/23,平谷公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-78\",\n" +
            "\t\t\t\t\"index\": 141\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"78\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/47,通州公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-80\",\n" +
            "\t\t\t\t\"index\": 142\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"79\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-1:G0/0/41,城区公司五层:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-81\",\n" +
            "\t\t\t\t\"index\": 143\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"27\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/25,经研院行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-30\",\n" +
            "\t\t\t\t\"index\": 144\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"16\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/3,亦庄公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-19\",\n" +
            "\t\t\t\t\"index\": 145\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"28\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/17,电科院行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-31\",\n" +
            "\t\t\t\t\"index\": 146\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"29\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/21,工程公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-32\",\n" +
            "\t\t\t\t\"index\": 147\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"36\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/29,承发包公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-39\",\n" +
            "\t\t\t\t\"index\": 148\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"38\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/19,照明中心行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-41\",\n" +
            "\t\t\t\t\"index\": 149\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"46\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/47,银杰公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-49\",\n" +
            "\t\t\t\t\"index\": 150\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"47\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/23,谷新公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-50\",\n" +
            "\t\t\t\t\"index\": 151\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"60\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/5,电缆分公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-62\",\n" +
            "\t\t\t\t\"index\": 152\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"63\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/3,亦庄公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-64\",\n" +
            "\t\t\t\t\"index\": 153\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"67\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/17,电科院应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-69\",\n" +
            "\t\t\t\t\"index\": 154\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"68\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/19,照明中心应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-70\",\n" +
            "\t\t\t\t\"index\": 155\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"70\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/21,工程公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-72\",\n" +
            "\t\t\t\t\"index\": 156\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"86\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/39,大雁楼备:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-88\",\n" +
            "\t\t\t\t\"index\": 157\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"87\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/35,大雁楼主:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-89\",\n" +
            "\t\t\t\t\"index\": 158\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"88\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/11,国际会都现场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-90\",\n" +
            "\t\t\t\t\"index\": 159\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"89\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/1,通州大会议室:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-91\",\n" +
            "\t\t\t\t\"index\": 160\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"90\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/9,世园会现场指挥部备:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-92\",\n" +
            "\t\t\t\t\"index\": 161\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"92\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-2:G0/0/33,冬奥会西单办公室:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-94\",\n" +
            "\t\t\t\t\"index\": 162\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"34\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/25,客服中心行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-37\",\n" +
            "\t\t\t\t\"index\": 163\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"35\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/27,电动车公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-38\",\n" +
            "\t\t\t\t\"index\": 164\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"40\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/21,华商远大行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-43\",\n" +
            "\t\t\t\t\"index\": 165\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"41\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/37,华商三优行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-44\",\n" +
            "\t\t\t\t\"index\": 166\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"42\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/29,华商能源行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-45\",\n" +
            "\t\t\t\t\"index\": 167\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"43\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/35,华商电灯行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-46\",\n" +
            "\t\t\t\t\"index\": 168\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"44\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/31,吉北咨询行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-47\",\n" +
            "\t\t\t\t\"index\": 169\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"45\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/17,京电设计行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-48\",\n" +
            "\t\t\t\t\"index\": 170\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"48\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/13,中电联汽车行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-51\",\n" +
            "\t\t\t\t\"index\": 171\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"49\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/33,公司巡察办行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-52\",\n" +
            "\t\t\t\t\"index\": 172\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"50\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/19,重点工程生产办公室行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-53\",\n" +
            "\t\t\t\t\"index\": 184\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"64\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/25,客服中心应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-65\",\n" +
            "\t\t\t\t\"index\": 173\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"71\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/21,华商远大应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-73\",\n" +
            "\t\t\t\t\"index\": 174\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"80\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/43,昌平19006分指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-82\",\n" +
            "\t\t\t\t\"index\": 175\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"82\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/3,芦城应急抢修中心:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-84\",\n" +
            "\t\t\t\t\"index\": 176\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"83\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/47,大兴机场东:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-85\",\n" +
            "\t\t\t\t\"index\": 177\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"91\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-3:G0/0/5,会城门:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-93\",\n" +
            "\t\t\t\t\"index\": 178\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"21\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/23,大兴公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-24\",\n" +
            "\t\t\t\t\"index\": 179\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"25\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/25,顺义公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-28\",\n" +
            "\t\t\t\t\"index\": 180\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"32\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/33,培训中心行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-35\",\n" +
            "\t\t\t\t\"index\": 181\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"33\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/7,物资公司行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-36\",\n" +
            "\t\t\t\t\"index\": 182\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"39\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/37,华商伟业行政:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-42\",\n" +
            "\t\t\t\t\"index\": 183\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"62\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/23,大兴公司八层应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-63-1\",\n" +
            "\t\t\t\t\"index\": 185\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"77\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/25,顺义公司应急:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-79\",\n" +
            "\t\t\t\t\"index\": 186\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"81\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/17,国家会议中心现场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-83\",\n" +
            "\t\t\t\t\"index\": 187\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"84\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/47,照明中心广场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-86\",\n" +
            "\t\t\t\t\"index\": 188\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"85\",\n" +
            "\t\t\t\t\"description\": \"QM-Video-Access-4:G0/0/27,广场区现场指挥部:LAN1\",\n" +
            "\t\t\t\t\"id\": \"edge-87\",\n" +
            "\t\t\t\t\"index\": 189\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    public Object drawByAll(String deptid) {
        return Json.fromJson(testtemp);
    }

    public Topo getToPoByTagId(String tagid) {
        List<Topo> topos = query(Cnd.NEW().and("tag_id","=",tagid));
        if(Lang.isEmpty(topos)){
            return null;
        }
        Topo topo = topos.get(0);
        topo = fetchLinks(topo,"^base|tag$");

        return topo ;
    }
}
