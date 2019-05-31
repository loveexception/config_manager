package cn.tico.iot.configmanger.module.sys.services;

import cn.tico.iot.configmanger.common.base.Service;
import cn.tico.iot.configmanger.common.enums.ImageType;
import cn.tico.iot.configmanger.common.utils.UpLoadUtil;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import cn.tico.iot.configmanger.module.sys.models.Image;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.upload.TempFile;

/**
 * 图片管理 服务层实现
 * 
 * @author haiming
 * @date 2019-05-09
 */
@IocBean(args = {"refer:dao"})
public class ImageService extends Service<Image> {

	public ImageService(Dao dao) {
		super(dao);
	}

	/**
	 * 获取图片
	 * @param id
	 * @return
	 */
	public String get(String id){
		Image img =this.fetch(id);
		if(Lang.isNotEmpty(img)){
			if("Base64".equals(img.getPhotoType())){
				return img.getBase64();
			}else if("Qiniu".equals(img.getPhotoType())){
				return img.getUrl();
			}
			return "/open/file/get/" + img.getLocalPath();
		}
		return null;
	}

	/**
	 * 图片存储共用类
	 * @param tempFile
	 * @param type
	 * @param userId
	 * @return
	 */
	public String save(TempFile tempFile, ImageType type , String userId, String id){
		if(Strings.isNotBlank(id)){
			this.delete(id);
		}
		Image image =new Image();
		String url;
		switch (type){
			case Base64:
				url = UpLoadUtil.upLoadFileBase64(tempFile);
				image.setPhotoType(ImageType.Base64.getType());
				image .setBase64(url);
				break;
			case Qiniu:
				url =UpLoadUtil.upLoadFile(tempFile);
				image.setPhotoType(ImageType.Qiniu.getType());
				image .setUrl(url);
				break;
			default:
				url =UpLoadUtil.upLoadFileSysConfigPath(tempFile,userId);
				image.setPhotoType(ImageType.Local.getType());
				image .setLocalPath(url);
				break;
		}
		image = this.insert(image);
		return image.getId();
	}

}
