package cn.tico.iot.configmanger.module.mao.redis;

import cn.tico.iot.configmanger.MainLauncher;
import cn.tico.iot.configmanger.module.iot.models.base.Location;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.boot.NbApp;
import org.nutz.boot.test.junit4.NbJUnit4Runner;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;
import org.nutz.mapl.Mapl;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@IocBean
@RunWith(NbJUnit4Runner.class)
public class LocationManagerTest extends Assert {


    @Inject
    private LocationManager locationManager;


    @Inject
    private Jedis jedis;


    @Inject("refer:$ioc")
    private Ioc ioc;


    public static NbApp createNbApp() {

        return new NbApp().setMainClass(MainLauncher.class).setPrintProcDoc(false);
    }


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void findAncestors() {
        Location  location = makeLocation(10011);
        List<Location> list = makeLocations(100,1001,1002,10011,10012,10021,10022);
        Logs.get().debugf("one:%s, \n all:%s ",Json.toJson(location),Json.toJson(list));
        Location result = locationManager.findAncestors(location,list);

        assertEquals(2,location.getParents().size());
    }

    @Test
    public void zip() {
        List<Location> list = makeLocations(100,1001,1002,1003,10011,10012,10021,10022);
        list = locationManager.children(list);


        Logs.get().debug(new Gson().toJson(list.get(0)));


    }


    @Test
    public void zipid() {
        List<Location> list = makeLocations(100,1001,1002,1003,10011,10012,10021,10022,10023);
        Location locl = locationManager.zip("1002",list);
        // Logs.get().debug(Json.toJson(list));
        // assertNotNull(list.get(0).getChildren().get(0));

        list = locl.getChildren();
        Logs.get().debug(Json.toJson(list));
        assertEquals(3,list.size());
        list = list.get(0).getChildren();



        Logs.get().debug(Json.toJson(list));
        assertTrue(Lang.isEmpty(list));
        //list = list.get(0).getChildren();

    }
    @Test
    public void zipid2() {
        List<Location> list = makeLocations(100,1001,1002,1003,10011,10012,10021,10022,10023,100231,100232,100233,100234,100235);
        Location locl = locationManager.zip("10023",list);
        // Logs.get().debug(Json.toJson(list));
        // assertNotNull(list.get(0).getChildren().get(0));

        list = locl.getChildren();
        Logs.get().debug(Json.toJson(list));
        assertEquals(5,list.size());
        list = list.get(0).getChildren();



        Logs.get().debug(Json.toJson(list));
        assertTrue(Lang.isEmpty(list));
        //list = list.get(0).getChildren();

    }

    @Test
    public void init(){

        System.out.println("init");
        locationManager.init();
        Set<String> set = locationManager.keys();
        Logs.get().debug(set);

        assertTrue(set.size()>1);

        Location location = locationManager.get(set.iterator().next());
        Logs.get().debug(location);
        assertNotNull(location);

        //locationManager.get("");
    }
    @Test
    public void testInit(){
        LocationManager locationManager = ioc.get(LocationManager.class);
        locationManager.init();
    }

    @Test
    public void byName(){

        locationManager.init();


        List<Location> all = locationManager.all();
        Logs.get().debug(all);
        Location location = locationManager.byName("中国",all);
        Logs.get().debug(location);
        assertNotNull(location);
        assertEquals("100000",location.getId());

        //locationManager.get("");
    }

    @Test
    public void fatherName(){

        locationManager.init();


        //List<Location>   all = //makeLocations(100,1001,1002,1003,10011,10012,10021,10022,10023,100231,100232,100233,100234,100235);

       // Logs.get().debug(all);
        Location location=locationManager.get("387095cd13d94497b7666ed1a65e1712");
        location = locationManager.fatherName(location);
        //Location location = locationManager.byName("B座二层会议室",all);
        Logs.get().debug(location.getParentName());
        assertNotNull(location.getParentName());
        assertEquals("387095cd13d94497b7666ed1a65e1712",location.getId());

        //locationManager.get("");
    }
    @Test
    public void fatherName2(){

        locationManager.init();


        //List<Location>   all = //makeLocations(100,1001,1002,1003,10011,10012,10021,10022,10023,100231,100232,100233,100234,100235);

        // Logs.get().debug(all);
        Location location=locationManager.get("387095cd13d94497b7666ed1a65e1712");
        //location = locationManager.fatherName(location);
        //Location location = locationManager.byName("B座二层会议室",all);
        Logs.get().debug(location.getParentName());
        assertNotNull(location.getParentName());
        assertEquals("387095cd13d94497b7666ed1a65e1712",location.getId());

        //locationManager.get("");
    }
    @Test
    public void byNameB座二层会议室(){

        locationManager.init();


        List<Location> all = locationManager.all();
        Logs.get().debug(all);
        Location location = locationManager.byName("B座二层会议室",all);
        Logs.get().debug(location.getId());
        assertNotNull(location);
        assertEquals("B座二层会议室",location.getCnName());

        //locationManager.get("");
    }
    @Test
    public void byLocationB座二层会议室(){

        locationManager.init();


        Location me=locationManager.get("387095cd13d94497b7666ed1a65e1712");
        List<Location> list = locationManager.allFamilyWithMe("387095cd13d94497b7666ed1a65e1712");
        assertNotNull(me);
        assertFalse(Lang.isEmpty(list));
        Logs.get().debug(list);
        List p = list.stream().filter(one->one.getId().equals("387095cd13d94497b7666ed1a65e1712") )
                .collect(Collectors.toList());


        assertNotNull(p.get(0));



        //locationManager.get("");
    }

    @Test
    public void byName国网北京密云供电公司会议室(){

        locationManager.init();


        List<Location> all = locationManager.all();
        Location location = locationManager.byName("国网北京密云供电公司:应急会议室",all);
        Logs.get().debug(location);
        assertNotNull(location);
        assertEquals("国网北京密云供电公司",Mapl.cell(location.getParents().get("3"),"cnName"));

    }
    @Test
    public void Complete(){
      Boolean b =   Pattern.matches(".*(北京).*","国网北京密云供电公司:应急会议室");
      System.out.println(b);
       b = Strings.isMatch(Pattern.compile("会议室"),"国网北京密云供电公司:应急会议室");
        System.out.println(b);

    }
    public List<Location> makeLocations(Integer ... array) {
        return Lists.newArrayList(array).stream().map(id->makeLocation(id)).collect(Collectors.toList());
    }

    public  Location makeLocation(int i) {
        if(i==100){
            Location location =new Location();
            location.setId(""+100);
            location.setCnName("name:"+i);
            return  location;
        }
        int index = i;
        String ancestors = "";
        while(index > 100){
            index = index /10 ;
            ancestors = index + ","+ancestors;
        }
        Location location = Mapl.maplistToT(
                NutMap.NEW()
                        .addv("id",""+i)
                        .addv("parentId", ""+(i/10))
                        .addv("ancestors",ancestors)
                        .addv("level",ancestors.split(",").length)
                ,Location.class
        );

        return location ;
    }
}