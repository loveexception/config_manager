package cn.tico.iot.configmanger.module.mao.common;

import cn.tico.iot.configmanger.TestBase;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.lang.Stopwatch;
import org.nutz.log.Logs;

import static org.junit.Assert.*;

public class MyTokenProcessorTest extends TestBase {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void login(){
        Stopwatch sw = Stopwatch.begin();


        Subject subject  = ShiroUtils.getSubject();

        Logs.get().debug(subject.isAuthenticated());


        sw.stop();
        Logs.get().debug(sw.getDuration());
    }
}