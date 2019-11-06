package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.module.iot.models.Topu;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

@IocBean(args = {"refer:dao"})
public class TopuService extends Service<Topu> {

    public TopuService(Dao dao) {
        super(dao);
    }

    @Inject
    TagService tagService;

    public Object drawByTag(String tag) {
        return Json.fromJson(testtemp);
    }
    static  final  String testtemp = "{\n" +
            "\t\t\"nodes\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"0\",\n" +
            "\t\t\t\t\"label\": \"MCU-1\",\n" +
            "\t\t\t\t\"sno\": \"CROSSPOINT32001\",\n" +
            "\t\t\t\t\"ip\": \"196.168.115.252\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_8660\",\n" +
            "\t\t\t\t\"icon\": \"MCU.png\",\n" +
            "\t\t\t\t\"shape\": \"ellipse\",\n" +
            "\t\t\t\t\"x\": 600,\n" +
            "\t\t\t\t\"y\": 25\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"1\",\n" +
            "\t\t\t\t\"label\": \"MCU-2\",\n" +
            "\t\t\t\t\"sno\": \"CROSSPOINT32001\",\n" +
            "\t\t\t\t\"ip\": \"196.168.115.253\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_8660\",\n" +
            "\t\t\t\t\"icon\": \"MCU.png\",\n" +
            "\t\t\t\t\"shape\": \"ellipse\",\n" +
            "\t\t\t\t\"x\": 221.63229166666667,\n" +
            "\t\t\t\t\"y\": 29.202430555555566\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"2\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Core-1\",\n" +
            "\t\t\t\t\"sno\": \"2102350DLSDMJB001611\",\n" +
            "\t\t\t\t\"ip\": \"196.168.255.252\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_S5720_52P_Si\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"shape\": \"rect\",\n" +
            "\t\t\t\t\"x\": 600,\n" +
            "\t\t\t\t\"y\": 95\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"3\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Core-2\",\n" +
            "\t\t\t\t\"sno\": \"2102350DLSDMJB001614\",\n" +
            "\t\t\t\t\"ip\": \"196.168.255.253\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_S5720_52P_Si\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"shape\": \"rect\",\n" +
            "\t\t\t\t\"x\": 221.45034722222232,\n" +
            "\t\t\t\t\"y\": 94.92986111111114\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"4\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-1\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK5001449\",\n" +
            "\t\t\t\t\"ip\": \"196.168.255.1\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_S5720_52P_Si\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"shape\": \"rect\",\n" +
            "\t\t\t\t\"x\": 220,\n" +
            "\t\t\t\t\"y\": 235\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"5\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-2\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK5001438\",\n" +
            "\t\t\t\t\"ip\": \"196.168.255.2\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_S5720_52P_Si\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"shape\": \"rect\",\n" +
            "\t\t\t\t\"x\": 545,\n" +
            "\t\t\t\t\"y\": 235\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"6\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-3\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK5001523\",\n" +
            "\t\t\t\t\"ip\": \"196.168.255.3\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_S5720_52P_Si\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"shape\": \"rect\",\n" +
            "\t\t\t\t\"x\": 740,\n" +
            "\t\t\t\t\"y\": 235\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"7\",\n" +
            "\t\t\t\t\"label\": \"QM-Video-Access-4\",\n" +
            "\t\t\t\t\"sno\": \"2102359504DMK7002171\",\n" +
            "\t\t\t\t\"ip\": \"196.168.255.4\",\n" +
            "\t\t\t\t\"type\": \"HUAWEI_S5720_52P_Si\",\n" +
            "\t\t\t\t\"icon\": \"switch.png\",\n" +
            "\t\t\t\t\"shape\": \"rect\",\n" +
            "\t\t\t\t\"x\": 805,\n" +
            "\t\t\t\t\"y\": 235\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"8\",\n" +
            "\t\t\t\t\"label\": \"00-A座721（主会场）\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313048D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.31\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 25,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"9\",\n" +
            "\t\t\t\t\"label\": \"00-A座721（备）\",\n" +
            "\t\t\t\t\"sno\": \"FD152430C1D1D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.0.30\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 90,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"10\",\n" +
            "\t\t\t\t\"label\": \"02-朝阳公司\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFB0D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.66\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 155,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"11\",\n" +
            "\t\t\t\t\"label\": \"08-昌平公司\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFDAD7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.2.34\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 220,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"12\",\n" +
            "\t\t\t\t\"label\": \"10-房山公司\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313028D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.162\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 285,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"13\",\n" +
            "\t\t\t\t\"label\": \"14-密云公司\",\n" +
            "\t\t\t\t\"sno\": \"FD1722315AD1D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.2\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 350,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"14\",\n" +
            "\t\t\t\t\"label\": \"18-电科院\",\n" +
            "\t\t\t\t\"sno\": \"FD161631301AD7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.2\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 480,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"15\",\n" +
            "\t\t\t\t\"label\": \"23-物资公司\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFA5D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.5.178\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 805,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"16\",\n" +
            "\t\t\t\t\"label\": \"24-客服中心\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313016D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.194\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 675,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"17\",\n" +
            "\t\t\t\t\"label\": \"25-电动车公司\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CFE7D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.4.210\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 740,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"18\",\n" +
            "\t\t\t\t\"label\": \"27-电缆分公司\",\n" +
            "\t\t\t\t\"sno\": \"FD154430C4CBD7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.1.226\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 415,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"19\",\n" +
            "\t\t\t\t\"label\": \"28-照明中心\",\n" +
            "\t\t\t\t\"sno\": \"FD161630CF56D7\",\n" +
            "\t\t\t\t\"ip\": \"196.168.3.18\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 545,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"20\",\n" +
            "\t\t\t\t\"label\": \"主流程测试group_0823\",\n" +
            "\t\t\t\t\"sno\": \"FD1616313082D7\",\n" +
            "\t\t\t\t\"ip\": \"172.17.17.1\",\n" +
            "\t\t\t\t\"type\": \"group550\",\n" +
            "\t\t\t\t\"icon\": \"terminal.png\",\n" +
            "\t\t\t\t\"shape\": \"circle\",\n" +
            "\t\t\t\t\"x\": 610,\n" +
            "\t\t\t\t\"y\": 305\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"edges\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"0\",\n" +
            "\t\t\t\t\"target\": \"2\",\n" +
            "\t\t\t\t\"id\": \"edge-0\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"1\",\n" +
            "\t\t\t\t\"target\": \"3\",\n" +
            "\t\t\t\t\"id\": \"edge-1\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"3\",\n" +
            "\t\t\t\t\"id\": \"edge-2\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"4\",\n" +
            "\t\t\t\t\"id\": \"edge-3\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"4\",\n" +
            "\t\t\t\t\"id\": \"edge-4\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"5\",\n" +
            "\t\t\t\t\"id\": \"edge-5\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"5\",\n" +
            "\t\t\t\t\"id\": \"edge-6\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"6\",\n" +
            "\t\t\t\t\"id\": \"edge-7\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"6\",\n" +
            "\t\t\t\t\"id\": \"edge-8\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"2\",\n" +
            "\t\t\t\t\"target\": \"7\",\n" +
            "\t\t\t\t\"id\": \"edge-9\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"3\",\n" +
            "\t\t\t\t\"target\": \"7\",\n" +
            "\t\t\t\t\"id\": \"edge-10\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"8\",\n" +
            "\t\t\t\t\"id\": \"edge-11\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"9\",\n" +
            "\t\t\t\t\"id\": \"edge-12\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"10\",\n" +
            "\t\t\t\t\"id\": \"edge-13\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"11\",\n" +
            "\t\t\t\t\"id\": \"edge-14\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"12\",\n" +
            "\t\t\t\t\"id\": \"edge-15\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"13\",\n" +
            "\t\t\t\t\"id\": \"edge-16\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"14\",\n" +
            "\t\t\t\t\"id\": \"edge-17\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"7\",\n" +
            "\t\t\t\t\"target\": \"15\",\n" +
            "\t\t\t\t\"id\": \"edge-18\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"16\",\n" +
            "\t\t\t\t\"id\": \"edge-19\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"6\",\n" +
            "\t\t\t\t\"target\": \"17\",\n" +
            "\t\t\t\t\"id\": \"edge-20\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"4\",\n" +
            "\t\t\t\t\"target\": \"18\",\n" +
            "\t\t\t\t\"id\": \"edge-21\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"19\",\n" +
            "\t\t\t\t\"id\": \"edge-22\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"source\": \"5\",\n" +
            "\t\t\t\t\"target\": \"20\",\n" +
            "\t\t\t\t\"id\": \"edge-23\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}";
}
