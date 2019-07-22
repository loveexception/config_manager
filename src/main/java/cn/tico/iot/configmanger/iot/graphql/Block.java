package cn.tico.iot.configmanger.iot.graphql;

public interface Block {
    public void exec(String topic, String key, String value, long offset);
}
