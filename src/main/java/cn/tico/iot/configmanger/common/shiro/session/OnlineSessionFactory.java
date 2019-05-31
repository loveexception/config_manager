package cn.tico.iot.configmanger.common.shiro.session;

import cn.tico.iot.configmanger.common.utils.IpUtils;
import cn.tico.iot.configmanger.common.utils.StringUtils;
import eu.bitwalker.useragentutils.UserAgent;
import cn.tico.iot.configmanger.common.bean.OnlineSession;
import cn.tico.iot.configmanger.module.monitor.models.UserOnline;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义sessionFactory会话
 *
 * @author ruoyi
 */
@IocBean
public class OnlineSessionFactory implements SessionFactory {
    public Session createSession(UserOnline userOnline) {
        OnlineSession onlineSession = userOnline.getSession();
        if (StringUtils.isNotNull(onlineSession) && onlineSession.getId() == null) {
            onlineSession.setId(userOnline.getSessionId());
        }
        return userOnline.getSession();
    }

    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null) {
                UserAgent userAgent = UserAgent.parseUserAgentString(Mvcs.getReq().getHeader("User-Agent"));
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtils.getIpAddr(request));
                session.setBrowser(browser);
                session.setOs(os);
            }
        }
        return session;
    }
}
