package cn.tico.iot.configmanger.iot.excel;

import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.services.MysqlTestDao;
import cn.tico.iot.configmanger.module.mao.services.ExcelDeviceService;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nutz.integration.json4excel.J4E;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelTest {
    static ExcelDeviceService service = new ExcelDeviceService();
    @BeforeClass
    public static void init(){
        service.dao = MysqlTestDao.NEW();
    }


    @Test
    public void testfindExcel(){
        InputStream inputStream = Files.findFileAsStream("test5.xlsx");



        List<Device> deviceList =  J4E.fromExcel(inputStream, Device.class, null);
        List <Map> mm  = deviceList.stream().map(dev ->{
                    NutMap map = service.mapById(dev);
                    map = service.cnNamesChanges(map);
                    NutMap d = Lang.obj2nutmap(dev);
                    d.putAll(map);
                    return d;

                }).collect(Collectors.toList());

//        Device dev = deviceList.get(0);
//
//        NutMap map = service.mapById(dev);
//        System.out.println(map);
//
//
//        map = service.cnNamesChanges(map);
//        NutMap nutMap = Lang.obj2nutmap(dev);
//        nutMap.putAll(map);

       // Logs.get().debug(nutMap);

        Logs.get().infof("map:%s",mm);


    }

    @Test
    public void testExcelRow(){


        List<Row> rows = Lists.newArrayList(J4E.loadExcel(Files.findFileAsStream("test5.xlsx")).getSheetAt(0).rowIterator());
        Row title = rows.get(0);


        List<Integer> tagIndex = Lists.newArrayList(title.cellIterator())
                .stream()
                .filter(cell -> cell.getStringCellValue().indexOf("标签")>=0)
                .map(cell->cell.getColumnIndex())
                .collect(Collectors.toList());

        Logs.get().debugf("%s",tagIndex);

        List  tags = rows.stream().skip(1).map(
                row-> tagIndex.stream().map(index->row.getCell(index))
                .collect(Collectors.toList())
        ).collect(Collectors.toList());
        Logs.get().debug(tags);



    }


}
