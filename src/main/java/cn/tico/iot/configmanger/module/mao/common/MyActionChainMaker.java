package cn.tico.iot.configmanger.module.mao.common;

import lombok.SneakyThrows;
import org.nutz.mvc.*;
import org.nutz.mvc.impl.NutActionChain;
import org.nutz.mvc.impl.processor.*;

import java.util.ArrayList;
import java.util.List;

public class MyActionChainMaker implements ActionChainMaker {


    // 该接口只有一个方法
    @SneakyThrows
    public ActionChain eval(NutConfig config, ActionInfo ai) {
        // 提醒: config可以获取ioc等信息, ai可以获取方法上的各种配置及方法本身
        // 正常处理的列表
        List<Processor> list = new ArrayList<>();
        list.add(new UpdateRequestAttributesProcessor()); // 设置base/msg等内置属性
        list.add(new EncodingProcessor()); // 设置编码信息@Encoding
        list.add(new ModuleProcessor()); // 获取入口类的对象,从ioc或直接new

        //自定义规则链
        list.add(new MyCorssProcessor());
        list.add(new MyTokenProcessor());
        //list.add(config.getIoc().get(MyTokenProcessor.class));
        //
        list.add(new ActionFiltersProcessor()); // 处理@Filters
        list.add(new AdaptorProcessor()); // 处理@Adaptor
        list.add(new MethodInvokeProcessor()); // 执行入口方法
        list.add(new ViewProcessor()); // 对入口方法进行渲染@Ok
        for (Processor p : list) {
            p.init(config, ai);
        }

        // 最后是专门负责兜底的异常处理器,这个处理器可以认为是全局异常处理器,对应@Fail
        Processor error = new FailProcessor();
        error.init(config, ai);
        return new NutActionChain(list, error, ai);
    }
}