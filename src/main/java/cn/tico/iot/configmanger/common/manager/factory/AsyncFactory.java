package cn.tico.iot.configmanger.common.manager.factory;


import cn.tico.iot.configmanger.common.bean.OnlineSession;
import cn.tico.iot.configmanger.common.utils.AddressUtils;
import cn.tico.iot.configmanger.common.utils.LogUtils;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.module.monitor.models.Logininfor;
import cn.tico.iot.configmanger.module.monitor.models.UserOnline;
import cn.tico.iot.configmanger.module.monitor.services.LogininforService;
import cn.tico.iot.configmanger.module.monitor.services.OperLogService;
import cn.tico.iot.configmanger.module.monitor.services.UserOnlineService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.bitwalker.useragentutils.UserAgent;

import java.util.Date;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
@IocBean
public class AsyncFactory {
    private static Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    @Inject
    private UserOnlineService userOnlineService;
    @Inject
    private OperLogService operLogService;
    @Inject
    private LogininforService logininforService;

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
    public TimerTask syncSessionToDb(OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                UserOnline online = new UserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
//                online.setStatus(session.getStatus());
                online.setSession(session);
                userOnlineService.insert(online);

            }
        };
    }


    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public TimerTask recordLogininfor( String username,  boolean status,  String message,  Object... args) {
         UserAgent userAgent = UserAgent.parseUserAgentString(Mvcs.getReq().getHeader("User-Agent"));
         String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(AddressUtils.getRealAddressByIP(ip));
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                Logininfor logininfor = new Logininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                logininfor.setStatus(status);
                logininfor.setLoginTime(new Date());
                // 日志状态
//                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status))
//                {
//                    logininfor.setStatus(Constants.SUCCESS);
//                }
//                else if (Constants.LOGIN_FAIL.equals(status))
//                {
//                    logininfor.setStatus(Constants.FAIL);
//                }
                // 插入数据
                logininforService.insert(logininfor);
            }
        };
    }

//    /**
//     * 操作日志记录
//     *
//     * @param operLog 操作日志信息
//     * @return 任务task
//     */
//    public TimerTask recordOper( OperLog operLog) {
//        return new TimerTask() {
//            @Override
//            public void run() {
//                // 远程查询操作地点
//                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
//                operLogService.insert(operLog);
//            }
//        };
//    }

}
