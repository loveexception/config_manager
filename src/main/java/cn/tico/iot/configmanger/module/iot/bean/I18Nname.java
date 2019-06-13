package cn.tico.iot.configmanger.module.iot.bean;

import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;

@Data
public class I18Nname {
    /**
     * 名称
     */
    @Column("cn_name")
    @Comment("名称")
    private String cnName;
    /**
     * 编码
     */
    @Column("en_name")
    @Comment("编码")
    private String enName;
}
