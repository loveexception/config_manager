package cn.tico.iot.configmanger.module.sys.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 图片管理表 sys_image
 *
 * @author haiming
 * @date 2019-05-09
 */
@Table("sys_image" )
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    @Name
    @Column("id" )
    @Comment("id " )
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @Prev(els = {@EL("uuid()" )})
    private String id;

    /**
     * 图片类型
     */
    @Column("photo_type" )
    @Comment("图片类型 " )
    private String photoType;

    /**
     * 数据
     */
    @Column("base64" )
    @Comment("数据 " )
    private String base64;

    /**
     * 本地地址
     */
    @Column("local_path" )
    @Comment("本地地址 " )
    private String localPath;

    /**
     * 网页地址
     */
    @Column("url" )
    @Comment("网页地址 " )
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , getId())
                .append("base64" , getBase64())
                .append("localPath" , getLocalPath())
                .append("url" , getUrl())
                .append("photoType" , getPhotoType())
                .toString();
    }
}
