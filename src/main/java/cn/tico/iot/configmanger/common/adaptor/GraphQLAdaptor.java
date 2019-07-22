package cn.tico.iot.configmanger.common.adaptor;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.mvc.HttpAdaptor;
import org.nutz.mvc.adaptor.PairAdaptor;
import org.nutz.mvc.adaptor.ParamInjector;
import org.nutz.mvc.adaptor.injector.JsonInjector;
import org.nutz.mvc.adaptor.injector.VoidInjector;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Reader;
import java.lang.reflect.Type;

public class GraphQLAdaptor extends PairAdaptor {

        protected ParamInjector evalInjector(Type type, Param param) {
            if (param == null || "..".equals(param.value())) {

                return new GraphQLInjector(type);
            }
            return super.evalInjector(type, param);
        }

        public Object getReferObject(ServletContext sc,
                                     HttpServletRequest req,
                                     HttpServletResponse resp, String[] pathArgs) {
            try {
                Reader reader = Streams.utf8r(req.getInputStream());
                return Streams.read(reader).toString();
            }
            catch (Exception e) {
                throw Lang.wrapThrow(e);
            }
        }
    }
