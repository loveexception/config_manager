package cn.tico.iot.configmanger.iot.excel;

import cn.tico.iot.configmanger.module.iot.models.device.Device;
import org.junit.Test;
import org.nutz.integration.json4excel.J4E;
import org.nutz.json.Json;
import org.nutz.lang.Files;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ExcelTest {


    @Test
    public void testfindExcel(){
        InputStream inputStream = Files.findFileAsStream("test.xlsx");


        List<Device> deviceList =  J4E.fromExcel(inputStream, Device.class, null);
        System.out.println(Json.toJson(deviceList));
    }
}
