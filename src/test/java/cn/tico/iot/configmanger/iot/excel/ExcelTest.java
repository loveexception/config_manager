package cn.tico.iot.configmanger.iot.excel;

import cn.tico.iot.configmanger.module.iot.models.device.Device;
import org.junit.Test;
import org.nutz.integration.json4excel.J4E;
import org.nutz.integration.json4excel.annotation.J4EName;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Mirror;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ExcelTest {


    @Test
    public void testfindExcel(){
        InputStream inputStream = Files.findFileAsStream("test5.xlsx");


        List<Device> deviceList =  J4E.fromExcel(inputStream, Device.class, null);
        //System.out.println(Json.toJson(deviceList));
        //System.out.println(deviceList.get(0));
        Device dev = deviceList.get(0);
        Mirror mirror = Mirror.me(Device.class);
        NutMap map = NutMap.NEW();


                Field[] fields  = mirror.getFields(J4EName.class);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(field.getName().indexOf("id")>0){
                 Object value = mirror.getValue(dev,field);

                String key =field.getName().replace("id","");

                map.addv(field.getName(),value);


            }
        }


        Logs.get().infof("map:%s",map);


    }
}
