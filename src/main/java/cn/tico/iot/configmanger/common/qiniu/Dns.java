package cn.tico.iot.configmanger.common.qiniu;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author haiming
 */
public interface Dns {
    List<InetAddress> lookup(String hostname) throws UnknownHostException;
}
