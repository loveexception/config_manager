package cn.tico.iot.configmanger;

import cn.tico.iot.configmanger.common.base.Globals;
import cn.tico.iot.configmanger.common.utils.ShiroUtils;
import cn.tico.iot.configmanger.common.utils.TreeUtils;
import cn.tico.iot.configmanger.module.iot.graphql.GatewayBlock;
import cn.tico.iot.configmanger.module.iot.graphql.KafkaBlock;
import com.alibaba.fastjson.JSON;
import cn.tico.iot.configmanger.module.sys.models.Menu;
import cn.tico.iot.configmanger.module.sys.models.User;
import cn.tico.iot.configmanger.module.sys.services.ConfigService;
import cn.tico.iot.configmanger.module.sys.services.MenuService;
import cn.tico.iot.configmanger.module.sys.services.UserService;
import org.nutz.boot.NbApp;
import org.nutz.conf.NutConf;
import org.nutz.dao.Dao;
import org.nutz.el.opt.RunMethod;
import org.nutz.el.opt.custom.CustomMake;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.*;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author haiming
 */
@IocBean(create = "init", depose = "depose")
@IocBy(args="*slog")
@Localization(value = "locales/", defaultLocalizationKey = "zh-CN")
public class MainLauncher {
    private static final Log log = Logs.get();

    @Inject("refer:$ioc")
    private Ioc ioc;

    @Inject
    protected Dao dao;

    @Inject
    protected PropertiesProxy conf;

    @Inject
    protected KafkaBlock block;

    @Inject
    public GatewayBlock gatewayBlock;

    @Inject
    private UserService userService;
    @Inject
    private MenuService menuService;


    @At({"/", "/index"})
    @Ok("re")
    public String index(HttpServletRequest req) {
        User user = ShiroUtils.getSysUser();
        if (Lang.isEmpty(user)) {
            return "th:/login.html";
        }
        user =userService.fetchLinks(user,"dept|image");
        req.setAttribute("user", user);
        if(Lang.isNotEmpty(user.getImage())){
            req.setAttribute("image", user.getImage().getBase64());
        }
        List<Menu> menuList = menuService.getMenuList(user.getId());
        req.setAttribute("menus", TreeUtils.getChildPerms(menuList, "0"));
        return "th:/index.html";
    }

    /**
     * 系统介绍
     *
     * @return
     */
    @At({"/sys/main"})
    @Ok("th:/main.html")
    public NutMap main() {
        return NutMap.NEW().setv("version", "1.0");
    }

    /**
     * NB自身初始化完成后会调用这个方法
     */
    public void init() {
        // 初始化系统变量
        Globals.init(ioc.get(ConfigService.class));
        initSysTask(ioc);
        /**
         * 自定义EL表达式
         * 文档
         * http://nutzam.com/core/el/overview.html
         */
        CustomMake.me().register("array2str", new RunMethod(){
            @Override
            public Object run(List<Object> fetchParam) {
                String tmp = JSON.toJSONString(fetchParam);
                return tmp;
            }
            @Override
            public String fetchSelf() {
                return "array2str";
            }
        });
        // 创建数据库
        //Daos.createTablesInPackage(dao, "cn.tico.iot", false);
        block.init();
        new Thread(()->block.consume("register", gatewayBlock)).start();

    }

    public void depose() {
    }

    public static void main(String[] args) throws Exception {
        new NbApp().setArgs(args).setPrintProcDoc(true).run();
        NutConf.USE_FASTCLASS = true;
    }

    /**
     * 初始化 定时任务
     * @param ioc
     */
    private void initSysTask(Ioc ioc) {
//        QuartzManager quartzManager = ioc.get(QuartzManager.class);
//        TaskService taskService = ioc.get(TaskService.class);
//        quartzManager.clear();
//        List<Task> taskList = taskService.query( Cnd.where("status", "=", true));
//        for (Task sysTask : taskList) {
//            try {
//                QuartzJob qj = new QuartzJob();
//                qj.setJobName(sysTask.getId());
//                qj.setJobGroup(sysTask.getId());
//                qj.setClassName(sysTask.getJobClass());
//                qj.setCron(sysTask.getCron());
//                qj.setComment(sysTask.getNote());
//                qj.setDataMap(sysTask.getData());
//                quartzManager.add(qj);
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        }
    }
}
