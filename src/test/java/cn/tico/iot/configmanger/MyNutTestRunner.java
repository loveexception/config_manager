package cn.tico.iot.configmanger;

import org.junit.runners.model.InitializationError;
import org.nutz.boot.NbApp;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.mock.NutTestRunner;



public class MyNutTestRunner extends NutTestRunner {

    public MyNutTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }


    
    /**
     * 可覆盖createIoc,实现参数覆盖, bean替换,等定制.
     */
    protected Ioc createIoc() {
        Ioc ioc = super.createIoc();
        PropertiesProxy conf = ioc.get(PropertiesProxy.class, "conf");
        conf.put("db.url", "jdbc:h2:~/test");
        return ioc;
    }
}