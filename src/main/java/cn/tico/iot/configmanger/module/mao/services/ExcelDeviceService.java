package cn.tico.iot.configmanger.module.mao.services;

import cn.tico.iot.configmanger.module.iot.models.base.Kind;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import cn.tico.iot.configmanger.module.iot.models.device.Device;
import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.driver.Driver;
import cn.tico.iot.configmanger.module.iot.services.LocationService;
import cn.tico.iot.configmanger.module.sys.models.Dept;
import com.google.common.collect.Lists;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.integration.json4excel.annotation.J4EName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@IocBean
public class ExcelDeviceService extends Device{
    static final String REG_STAT = ".*(";
    static final String REG_END = ").*";
    static final String SPLIT =":";
    @Inject
    public Dao dao;
    @Inject
    public LocationService locationService;



    static final NutMap K2B = new NutMap()
            
            .addv("locationid", Location.class)
            .addv("kindid", Kind.class)
            .addv("driverid",Driver.class)
            .addv("gatewayid", Gateway.class)
            .addv("deptid", Dept.class)
            .addv("locationId", Location.class)
            .addv("kindId", Kind.class)
            .addv("driverId",Driver.class)
            .addv("gatewayId", Gateway.class)
            .addv("deptId", Dept.class)


            ;

    public   NutMap cnNamesChanges(NutMap map) {
        for (Map.Entry entry: map.entrySet()) {
            String value  = entry.getValue().toString();
            String tablename = entry.getKey().toString();
            Object entity = K2B.get(tablename);
            value =cnNametoId((Class) entity,value);
            entry.setValue(value);
        }
        return map;
    }

    public  NutMap mapById(Object dev) {
        Mirror mirror = Mirror.me(dev.getClass());

        NutMap map = NutMap.NEW();


        Field[] fields  = mirror.getFields(J4EName.class);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(field.getName().indexOf("id")>0){
                Object value = mirror.getValue(dev,field);
                map.addv(field.getName(),value);


            }
        }
        return map;
    }

    public  String cnNametoId(Class object,String cnName){
        if(object.getClass().getName().equals("Location")){
            return locationName(cnName);
        }
        Mirror mirror = Mirror.me(object);
        Field[] fields =  mirror.getFields(J4EName.class);


        String[] names = Strings.splitIgnoreBlank(cnName,"-");
        String lastName = cnName;

        if (Lang.isNotEmpty(names)){
            lastName = Lists.newArrayList(names).get(names.length-1);
        }else{
            return null;
        }
        final String temp = lastName;
        List<String > list = Arrays.stream(fields).map(field -> field.getName()).collect(Collectors.toList());

        final SqlExpressionGroup[] group = {null};

        list.stream().filter( name -> name.indexOf("ame")>=0)
                .forEach(name->{
                    if(Lang.isEmpty(group[0])){

                        group[0] = Cnd.exps(name,"like","%"+temp+"%");
                    }else{
                        group[0].or(name,"like","%"+temp+"%");

                    }
                });

        List<?> records =dao.query(object,Cnd.NEW().and(group[0]));


        Logs.get().debugf("device:%s",records);


        return ""+ Mapl.cell(Mapl.toMaplist(records), "[0].id");
    }

    private String locationName(String cnName) {
        //List<Location> all = locationManager.all();
        List <Location> all = locationService.findAllLocations();

        return byName(cnName,all).getId();

    }
    public Location byName(String longSplitName,List<Location> all){
        String[] array =Strings.splitIgnoreBlank(longSplitName,SPLIT);
        List<String> names = Lang.array2list(array);
        String last = Lists.newLinkedList(names).getLast();


        List<Location> ones = all.stream()
                .filter(one-> isMatchByKey(last, one.getCnName()))
                .filter(one ->{

                    String langName = Strings.sBlank(one.getParentName(),"")+SPLIT+one.getCnName();
                    boolean b =  names.stream().allMatch(name -> isMatchByKey(name, langName));
                    return b;
                }).collect(Collectors.toList());
        if(Lang.isEmpty(ones)){
            return null;
        }
        if(ones.size()!=1){
            return null;

        }
        return ones.get(0);

    }
    public static boolean isMatchByKey(String last, String cnName) {
        return Strings.isMatch(Pattern.compile(REG_STAT + last + REG_END), cnName);
    }


}
