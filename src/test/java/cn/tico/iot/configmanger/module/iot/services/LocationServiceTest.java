package cn.tico.iot.configmanger.module.iot.services;

import cn.tico.iot.configmanger.module.iot.models.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LocationServiceTest {
    LocationService service ;
    Dao dao ;

    @Before
    public void setUp() throws Exception {
         dao = MysqlTestDao.NEW();
         dao.create(Location.class,true);
         service = new LocationService(dao);

    }

    @After
    public void tearDown() throws Exception {
        dao.drop(Location.class);
        dao = null;
        service = null;
    }

    @Test
    public void getTrees() {
       List list = service.selectTree(null,null);
       assertEquals(0,list.size());
    }
    @Test
    public void autoID() {
        Location location = new Location();
        //location.setId();
        dao.insert(location);
        List<Map<String,Object>> list = service.selectTree(null,null);
        System.out.println(list.get(0).get("id"));
        assertEquals(1,list.size());
    }
    @Test
    public void getTrees100() {
        Location location = new Location();
        location.setId("100");
        dao.insert(location);
        List list = service.selectTree(null,null);
        assertEquals(1,list.size());
    }
    @Test
    public void getTrees101() {
        Location
                location = new Location();
        location.setId("100");
        dao.insert(location);
        location = new Location();
        location.setId("101");
        location.setParentId("100");
        dao.insert(location);
        List list = service.selectTree("100",null);
        assertEquals(1,list.size());
    }
    @Test
    public void getTrees102() {
        Location
                location = new Location();
        location.setId("100");
        dao.insert(location);
        location = new Location();
        location.setId("101");
        location.setParentId("100");
        dao.insert(location);
        location = new Location();
        location.setId("102");
        location.setParentId("100");
        dao.insert(location);
        List list = service.selectTree("100",null);
        assertEquals(2,list.size());
    }

    @Test
    public void getTrees103() {
        Location
                location = new Location();
        location.setId("100");
        dao.insert(location);
        location = new Location();
        location.setId("101");
        location.setParentId("100");
        dao.insert(location);
        location = new Location();
        location.setId("103");
        location.setParentId("101");
        dao.insert(location);
        List list = service.selectTree("100",null);
        assertEquals(1,list.size());
    }

    @Test
    public void getTreesCNname() {
        Location location = new Location();
        location.setId("100");
        dao.insert(location);
        location = new Location();
        location.setId("101");
        location.setParentId("100");
        location.setCnName("毛大军");
        dao.insert(location);
        location = new Location();
        location.setId("103");

        location.setCnName("毛若涵");
        dao.insert(location);
        List
                list = service.selectTree(null,"毛若涵");
        assertEquals(1,list.size());
        list = service.selectTree(null,"毛");
        assertEquals(2,list.size());
        list = service.selectTree("100","毛");
        assertEquals(1,list.size());
    }
    @Test
    public void getTreesEnname() {
        Location location = new Location();
        location.setId("100");
        dao.insert(location);
        location = new Location();
        location.setId("101");
        location.setParentId("100");

        location.setCnName("hello");
        dao.insert(location);
        location = new Location();
        location.setId("103");
        location.setCnName("world");
        dao.insert(location);
        List
                list = service.selectTree(null,"hello");
        assertEquals(1,list.size());
        list = service.selectTree(null,"o");
        assertEquals(2,list.size());
        list = service.selectTree("100","");
        assertEquals(1,list.size());
    }


    @Test
    public void selectFathers() {

        Location
                location = new Location();
        dao.insert(location);
        String id = location.getId();

        location = new Location();
        location.setParentId(id);
        dao.insert(location);
        id = location.getId();

        location = new Location();
        location.setParentId(id);
        dao.insert(location);

        List list = service.selectFathers(location.getParentId(),"");
        System.out.println(list);
        assertEquals(2,list.size());
    }

    /*
    @Test
    public void selectTree() {
    }



    @Test
    public void insertDept() {
    }

    @Test
    public void update() {
    }

    @Test
    public void checkDeptNameUnique() {
    }

    @Test
    public void count() {
    }

    @Test
    public void count1() {
    }

    @Test
    public void count2() {
    }

    @Test
    public void count3() {
    }

    @Test
    public void fetch() {
    }

    @Test
    public void fetch1() {
    }

    @Test
    public void fetchLinks() {
    }

    @Test
    public void fetchLinks1() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insert1() {
    }

    @Test
    public void fastInsert() {
    }

    @Test
    public void update1() {
    }

    @Test
    public void updateIgnoreNull() {
    }

    @Test
    public void update2() {
    }

    @Test
    public void update3() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete1() {
    }

    @Test
    public void delete2() {
    }

    @Test
    public void delete3() {
    }

    @Test
    public void delete4() {
    }

    @Test
    public void delete5() {
    }

    @Test
    public void vDelete() {
    }

    @Test
    public void vDelete1() {
    }

    @Test
    public void getPageNumber() {
    }

    @Test
    public void getPageSize() {
    }

    @Test
    public void listPage() {
    }

    @Test
    public void listPage1() {
    }

    @Test
    public void tableList() {
    }

    @Test
    public void tableList1() {
    }

    @Test
    public void tableList2() {
    }

    @Test
    public void mirror() {
    }

    @Test
    public void setEntityType() {
    }

    @Test
    public void getEntity() {
    }

    @Test
    public void getEntityClass() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void clear1() {
    }

    @Test
    public void query() {
    }

    @Test
    public void each() {
    }

    @Test
    public void count4() {
    }

    @Test
    public void count5() {
    }

    @Test
    public void fetch2() {
    }

    @Test
    public void fetchx() {
    }

    @Test
    public void exists() {
    }

    @Test
    public void update4() {
    }

    @Test
    public void updateRelation() {
    }

    @Test
    public void deletex() {
    }

    @Test
    public void create() {
    }

    @Test
    public void insert2() {
    }

    @Test
    public void query1() {
    }

    @Test
    public void each1() {
    }

    @Test
    public void func() {
    }

    @Test
    public void func1() {
    }

    @Test
    public void getObject() {
    }

    @Test
    public void getObject1() {
    }

    @Test
    public void _query() {
    }

    @Test
    public void _query1() {
    }

    @Test
    public void _insert() {
    }

    @Test
    public void _fastInsert() {
    }

    @Test
    public void _insert1() {
    }

    @Test
    public void _insert2() {
    }

    @Test
    public void _insertWith() {
    }

    @Test
    public void _insertLinks() {
    }

    @Test
    public void _insertRelation() {
    }

    @Test
    public void _update() {
    }

    @Test
    public void _update1() {
    }

    @Test
    public void _updateIgnoreNull() {
    }

    @Test
    public void _updateWith() {
    }

    @Test
    public void _updateLinks() {
    }

    @Test
    public void _delete() {
    }

    @Test
    public void _deleteWith() {
    }

    @Test
    public void _deleteLinks() {
    }

    @Test
    public void _fetch() {
    }

    @Test
    public void _fetchLinks() {
    }

    @Test
    public void _fetchLinks1() {
    }

    @Test
    public void _clearLinks() {
    }

    @Test
    public void setExpert() {
    }

    @Test
    public void query2() {
    }

    @Test
    public void setDao() {
    }

    @Test
    public void dao() {
    }*/
}