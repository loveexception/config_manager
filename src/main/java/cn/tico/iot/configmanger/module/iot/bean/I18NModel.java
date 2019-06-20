package cn.tico.iot.configmanger.module.iot.bean;

import cn.tico.iot.configmanger.module.iot.bean.CommonModel;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;

@Data
public  class I18NModel extends CommonModel {
    /**
     * 名称
     */
    @Column("cn_name")
    @Comment("名称")
    @GraphQLQuery(name = "cn_name", description = "中文名")
    private String cnName;
    /**
     * 编码
     */
    @Column("en_name")
    @Comment("编码")
    @GraphQLQuery(name = "en_name", description = "english name")
    private String enName;


}
