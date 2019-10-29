package cn.tico.iot.configmanger.module.iot.graphql;

import cn.tico.iot.configmanger.module.iot.models.device.Gateway;
import cn.tico.iot.configmanger.module.iot.models.device.SubGateway;
import cn.tico.iot.configmanger.module.iot.services.GatewayService;
import cn.tico.iot.configmanger.module.iot.services.SubGatewayService;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nutz.dao.Dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubGatewayBlockTest {
    public static final String SNO = "sno_mao_1";
    public static final String MESSAGE = "{'sno':'"+SNO+"' }";

    //测试对象
    SubGatewayBlock gateway ;
    @Mock //模拟对象
    Dao dao ;
    @Mock
    KafkaBlock kafkaBlock ;
    @Mock
    GitBlock gitBlock;



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); // 刷新项目中的模拟对象


        gateway = new SubGatewayBlock();
        gateway.gatewayService = new GatewayService(dao);
        gateway.subGatewayService = new SubGatewayService(dao);
        gateway.kafkaBlock =kafkaBlock;
        gateway.gitBlock = gitBlock;



    }
    @After
    public void tearDown(){
        verifyNoMoreInteractions(dao);//对模拟对象计数
        verifyNoMoreInteractions(kafkaBlock);
        verifyNoMoreInteractions(gitBlock);

    }


    @Test
    public void a_1_SNO_gateway_有已绑定1个() {
        when(dao.count(eq(Gateway.class),any())).thenReturn(1);
        Object obj = gateway.exec("register",null, MESSAGE,0);
        verify(dao,times(1)).count((Class<?>) any(),any());
        assertNull(obj);


    }
    @Test
    public void a_2_SNO_gateway_有已绑定2个() {
        when(dao.count(eq(Gateway.class),any())).thenReturn(2);
        Object obj = gateway.exec("register",null,MESSAGE,0);
        verify(dao,times(1)).count((Class)any(),any());
        assertNull(obj);

    }
    @Test
    public void a_3_SNO_subgateway_有已绑定1个() {
        when(dao.count(eq(SubGateway.class),any())).thenReturn(1);
        when(dao.count(eq(Gateway.class),any())).thenReturn(0);
        SubGateway sub =new SubGateway();
        sub.setId("1");
        when(dao.query(eq(SubGateway.class),any())).thenReturn(Lists.newArrayList(sub));

        when(dao.deletex(eq(SubGateway.class),any())).thenReturn(1);

        Object obj = gateway.exec("register",null,MESSAGE,0);
        verify(dao,times(2)).count((Class<?>) any(),any());
        verify(dao,times(1)).deletex( any(),any());
        verify(dao,times(1)).query((Class<?>) any(),any());
        assertNotNull(obj);

    }

    @Test
    public void a_4_SNO_subgateway_all无绑定_subgateway_1() {
        when(dao.count(eq(SubGateway.class),any())).thenReturn(0);
        when(dao.count(eq(Gateway.class),any())).thenReturn(0);


        SubGateway sub =new SubGateway();
        sub.setId("1");
        when(dao.query(eq(SubGateway.class),any())).thenReturn(Lists.newArrayList(sub));

        when(dao.query(eq(Gateway.class),any())).thenReturn(Lists.newArrayList());

        Object obj = gateway.exec("register",null,MESSAGE,0);
        verify(dao,times(2)).count((Class<?>) any(),any());
        verify(dao,times(2)).query((Class<?>) any(),any());
        assertNotNull(obj);

    }
    @Test
    public void a_4_SNO_subgateway_all无绑定_subgateway_0() {
        when(dao.count(eq(SubGateway.class),any())).thenReturn(0);
        when(dao.count(eq(Gateway.class),any())).thenReturn(0);


        when(dao.query(eq(Gateway.class),any())).thenReturn(Lists.newArrayList());
        when(dao.query(eq(SubGateway.class),any())).thenReturn(Lists.newArrayList());

        SubGateway sub =new SubGateway();
        sub.setId("1");

        when(dao.insert( any())).thenReturn(sub);


        Object obj = gateway.exec("register",null,MESSAGE,0);
        verify(dao,times(2)).count((Class<?>) any(),any());
        verify(dao,times(2)).query((Class<?>) any(),any());
        verify(dao,times(1)).insert(any());
        assertNotNull(obj);

    }

    @Test
    public void a_4_SNO_subgateway_all无绑定_subgateway_1_gateWay_1() {
        Gateway gate =new Gateway();
        gate.setId("1");
        gate.setSno(SNO);
        SubGateway subGateway = new SubGateway();
        subGateway.setId("2");
        subGateway.setSno(SNO);

        when(dao.count(eq(SubGateway.class),any())).thenReturn(0);
        when(dao.count(eq(Gateway.class),any())).thenReturn(0);


        when(dao.query(eq(Gateway.class),any())).thenReturn(Lists.newArrayList(gate));
        when(dao.query(eq(SubGateway.class),any())).thenReturn(Lists.newArrayList(subGateway));

        when(dao.update(any())).thenReturn(1);





        Object obj = gateway.exec("register",null,MESSAGE,0);
        verify(dao,times(2)).count((Class<?>) any(),any());
        verify(dao,times(2)).query((Class<?>) any(),any());
        verify(dao,times(2)).update(any());

        verify(kafkaBlock,times(1)).produce(any(),any(),any());
        assertNotNull(obj);

    }
}