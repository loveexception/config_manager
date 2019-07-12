package cn.tico.iot.configmanger.module.other.models;

import cn.tico.iot.configmanger.iot.bean.CommonModel;
import cn.tico.iot.configmanger.iot.bean.I18NModel;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

/**
 * 字典表 sys_dict
 *
 * @author haiming
 * @date 2019-04-16
 */
@Table("other_team")
public class Team extends I18NModel implements Serializable {
    private static final long serialVersionUID = 1L;



    /**
     * 标签名
     */
    @Column("no")
    @Comment("序号")
    private String no;


    /**
     * 标签名
     */
    @Column("dept_name")
    @Comment("部门")
    private String deptName;

    /**
     * 标签名
     */
    @Column("title")
    @Comment("职位")
    private String title;


    /**
     * 标签名
     */
    @Column("tel")
    @Comment("联系电话")
    private String tel;


    /**
     * 类型
     */
    @Column("email")
    @Comment("邮箱： ")
    private String email;
    /**
     * 类型
     */
    @Column("image")
    @Comment("照片： ")
    private String image;





}
