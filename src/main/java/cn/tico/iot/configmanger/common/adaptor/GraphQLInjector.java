package cn.tico.iot.configmanger.common.adaptor;

import org.nutz.lang.Streams;
import org.nutz.mapl.Mapl;
import org.nutz.mvc.adaptor.ParamInjector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class GraphQLInjector implements ParamInjector {
    public GraphQLInjector(Type type) {
        this.type = type;
    }

    private Type type;




    @SuppressWarnings("unchecked")
    public Object get(    ServletContext sc,
                          HttpServletRequest req,
                          HttpServletResponse resp,
                          Object refer) {


        return refer;
    }
}
