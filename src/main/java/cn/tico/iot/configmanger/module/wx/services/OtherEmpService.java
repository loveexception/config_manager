package cn.tico.iot.configmanger.module.wx.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.utils.StringUtils;
import cn.tico.iot.configmanger.module.sys.services.ConfigService;
import cn.tico.iot.configmanger.module.wx.models.OtherEmp;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * 运维人员 服务层实现
 *
 * @author maodajun
 * @date 2019-08-05
 */
@IocBean(args = {"refer:dao"})
public class OtherEmpService extends Service<OtherEmp> {
    public OtherEmpService(Dao dao) {
        super(dao);
    }

    public static final String APP_UPLOAD_PATH = "AppUploadPath";
    public static final String NGINX_HOST = "NginxHost";
    public static final String NGINX_UPLOAD_PATH = "NginxUploadPath";
    @Inject
    private ConfigService configService;

    public void addEmp(OtherEmp otherEmp) {
        String imageRealPath = otherEmp.getImage();
        if (!StringUtils.isEmpty(imageRealPath)) {
            String imageNginxPath = updateImagePath(imageRealPath);
            otherEmp.setImage(imageNginxPath);
        }
        insert(otherEmp);
    }

    public void updateEmp(OtherEmp otherEmp) {
        String imageRealPath = otherEmp.getImage();
        if (!StringUtils.isEmpty(imageRealPath)) {
            String imageNginxPath = updateImagePath(imageRealPath);
            otherEmp.setImage(imageNginxPath);
        }
        update(otherEmp);
    }

    private String updateImagePath(String imageRealPath) {
        String appUploadPath = configService.getValue(APP_UPLOAD_PATH);
        String nginxHost = configService.getValue(NGINX_HOST);
        String nginxUploadPath = configService.getValue(NGINX_UPLOAD_PATH);
        String imageNginxPath = imageRealPath.replaceFirst(appUploadPath, nginxHost + nginxUploadPath);
        return imageNginxPath;
    }
}
