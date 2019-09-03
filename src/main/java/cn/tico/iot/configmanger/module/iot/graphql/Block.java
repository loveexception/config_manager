package cn.tico.iot.configmanger.module.iot.graphql;

import java.util.Map;

public interface Block {
    Object exec(String topic, String key, String value, long offset);
}
