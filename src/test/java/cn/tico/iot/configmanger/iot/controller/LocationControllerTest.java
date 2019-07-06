package cn.tico.iot.configmanger.iot.controller;

import cn.tico.iot.configmanger.iot.models.base.Location;
import cn.tico.iot.configmanger.iot.services.LocationService;
import com.google.gson.Gson;
import org.junit.Test;
import org.nutz.json.Json;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LocationControllerTest {

    @Test
    public void treeObject() {

        String json = "[{\n" +
                "   \"deptid\": \"100\",\n" +
                "   \"desription\": \"65432\",\n" +
                "   \"lng\": 1.0,\n" +
                "   \"lat\": 1.0,\n" +
                "   \"parentId\": \"0\",\n" +
                "   \"ancestors\": \"0\",\n" +
                "   \"level\": \"1\",\n" +
                "   \"orderNum\": 0,\n" +
                "   \"cnName\": \"中国\",\n" +
                "   \"enName\": \"china\",\n" +
                "   \"id\": \"100000\",\n" +
                "   \"status\": \"true\",\n" +
                "   \"delFlag\": \"false\",\n" +
                "   \"createBy\": \"1\",\n" +
                "   \"createTime\": \"2019-06-22 10:31:13\",\n" +
                "   \"updateBy\": \"1\",\n" +
                "   \"updateTime\": \"2019-06-22 10:31:50\"\n" +
                "}, {\n" +
                "   \"deptid\": \"100\",\n" +
                "   \"desription\": \"3211\",\n" +
                "   \"lng\": 0.0,\n" +
                "   \"lat\": 0.0,\n" +
                "   \"parentId\": \"100000\",\n" +
                "   \"ancestors\": \"0,100000\",\n" +
                "   \"level\": \"2\",\n" +
                "   \"orderNum\": 0,\n" +
                "   \"cnName\": \"天津\",\n" +
                "   \"enName\": \"33\",\n" +
                "   \"id\": \"1c519cedda624a4282e4a94a64c06fe6\",\n" +
                "   \"status\": \"true\",\n" +
                "   \"delFlag\": \"false\",\n" +
                "   \"createBy\": \"1\",\n" +
                "   \"createTime\": \"2019-07-05 18:35:30\",\n" +
                "   \"updateBy\": \"1\",\n" +
                "   \"updateTime\": \"2019-07-05 18:35:30\"\n" +
                "}, {\n" +
                "   \"deptid\": \"100\",\n" +
                "   \"desription\": \"3211\",\n" +
                "   \"lng\": 0.0,\n" +
                "   \"lat\": 0.0,\n" +
                "   \"parentId\": \"88b83333ea6d4ec9b038584ba8dfa270\",\n" +
                "   \"ancestors\": \"0,100000,1c519cedda624a4282e4a94a64c06fe6,88b83333ea6d4ec9b038584ba8dfa270\",\n" +
                "   \"level\": \"4\",\n" +
                "   \"orderNum\": 0,\n" +
                "   \"cnName\": \"会议室二\",\n" +
                "   \"enName\": \"43432\",\n" +
                "   \"id\": \"7d81a1a70bdd4d669b644106b1c5d14f\",\n" +
                "   \"status\": \"true\",\n" +
                "   \"delFlag\": \"false\",\n" +
                "   \"createBy\": \"1\",\n" +
                "   \"createTime\": \"2019-07-05 18:35:56\",\n" +
                "   \"updateBy\": \"1\",\n" +
                "   \"updateTime\": \"2019-07-05 18:35:56\"\n" +
                "}, {\n" +
                "   \"deptid\": \"100\",\n" +
                "   \"desription\": \"3211\",\n" +
                "   \"lng\": 0.0,\n" +
                "   \"lat\": 0.0,\n" +
                "   \"parentId\": \"1c519cedda624a4282e4a94a64c06fe6\",\n" +
                "   \"ancestors\": \"0,100000,1c519cedda624a4282e4a94a64c06fe6\",\n" +
                "   \"level\": \"3\",\n" +
                "   \"orderNum\": 0,\n" +
                "   \"cnName\": \"方言公司\",\n" +
                "   \"enName\": \"43432\",\n" +
                "   \"id\": \"88b83333ea6d4ec9b038584ba8dfa270\",\n" +
                "   \"status\": \"true\",\n" +
                "   \"delFlag\": \"false\",\n" +
                "   \"createBy\": \"1\",\n" +
                "   \"createTime\": \"2019-07-05 18:35:44\",\n" +
                "   \"updateBy\": \"1\",\n" +
                "   \"updateTime\": \"2019-07-05 18:35:44\"\n" +
                "}]";
        LocationService service = new LocationService(null);
        Location[] obj = Json.fromJsonAsArray(Location.class,json);
        Location root = service.zip(Arrays.asList(obj));
        System.out.println(Json.toJson(root));
    }
    @Test
    public void testMeJSon(){
        String json = "{\n" +
                "    \"deptid\": \"100\",\n" +
                "    \"desription\": \"65432\",\n" +
                "    \"lng\": 1.0,\n" +
                "    \"lat\": 1.0,\n" +
                "    \"parentId\": \"0\",\n" +
                "    \"ancestors\": \"0\",\n" +
                "    \"level\": \"1\",\n" +
                "    \"orderNum\": 0,\n" +
                "    \"children\": [\n" +
                "    {\n" +
                "        \"deptid\": \"100\",\n" +
                "        \"desription\": \"3211\",\n" +
                "        \"lng\": 0.0,\n" +
                "        \"lat\": 0.0,\n" +
                "        \"parentId\": \"100000\",\n" +
                "        \"ancestors\": \"0,100000\",\n" +
                "        \"level\": \"2\",\n" +
                "        \"orderNum\": 0,\n" +
                "        \"children\": [\n" +
                "        {\n" +
                "            \"deptid\": \"100\",\n" +
                "            \"desription\": \"3211\",\n" +
                "            \"lng\": 0.0,\n" +
                "            \"lat\": 0.0,\n" +
                "            \"parentId\": \"1c519cedda624a4282e4a94a64c06fe6\",\n" +
                "            \"ancestors\": \"0,100000,1c519cedda624a4282e4a94a64c06fe6\",\n" +
                "            \"level\": \"3\",\n" +
                "            \"orderNum\": 0,\n" +
                "            \"children\": [\n" +
                "            {\n" +
                "                \"deptid\": \"100\",\n" +
                "                \"desription\": \"3211\",\n" +
                "                \"lng\": 0.0,\n" +
                "                \"lat\": 0.0,\n" +
                "                \"parentId\": \"88b83333ea6d4ec9b038584ba8dfa270\",\n" +
                "                \"ancestors\": \"0,100000,1c519cedda624a4282e4a94a64c06fe6,88b83333ea6d4ec9b038584ba8dfa270\",\n" +
                "                \"level\": \"4\",\n" +
                "                \"orderNum\": 0,\n" +
                "                \"cnName\": \"会议室二\",\n" +
                "                \"enName\": \"43432\",\n" +
                "                \"id\": \"7d81a1a70bdd4d669b644106b1c5d14f\",\n" +
                "                \"status\": \"true\",\n" +
                "                \"delFlag\": \"false\",\n" +
                "                \"createBy\": \"1\",\n" +
                "                \"createTime\": \"Jul 5, 2019 6:35:56 PM\",\n" +
                "                \"updateBy\": \"1\",\n" +
                "                \"updateTime\": \"Jul 5, 2019 6:35:56 PM\"\n" +
                "            }],\n" +
                "            \"cnName\": \"方言公司\",\n" +
                "            \"enName\": \"43432\",\n" +
                "            \"id\": \"88b83333ea6d4ec9b038584ba8dfa270\",\n" +
                "            \"status\": \"true\",\n" +
                "            \"delFlag\": \"false\",\n" +
                "            \"createBy\": \"1\",\n" +
                "            \"createTime\": \"Jul 5, 2019 6:35:44 PM\",\n" +
                "            \"updateBy\": \"1\",\n" +
                "            \"updateTime\": \"Jul 5, 2019 6:35:44 PM\"\n" +
                "        }],\n" +
                "        \"cnName\": \"天津\",\n" +
                "        \"enName\": \"33\",\n" +
                "        \"id\": \"1c519cedda624a4282e4a94a64c06fe6\",\n" +
                "        \"status\": \"true\",\n" +
                "        \"delFlag\": \"false\",\n" +
                "        \"createBy\": \"1\",\n" +
                "        \"createTime\": \"Jul 5, 2019 6:35:30 PM\",\n" +
                "        \"updateBy\": \"1\",\n" +
                "        \"updateTime\": \"Jul 5, 2019 6:35:30 PM\"\n" +
                "    }],\n" +
                "    \"cnName\": \"中国\",\n" +
                "    \"enName\": \"china\",\n" +
                "    \"id\": \"100000\",\n" +
                "    \"status\": \"true\",\n" +
                "    \"delFlag\": \"false\",\n" +
                "    \"createBy\": \"1\",\n" +
                "    \"updateBy\": \"1\",\n" +
                "}";

        Object obj = Json.fromJson(json);
        Gson gson = new Gson();
        System.out.println(gson.toJsonTree(obj));
        System.out.println(Json.toJson(obj));
    }
}