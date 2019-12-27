package cn.tico.iot.configmanger.module.iot.bean;

import cn.tico.iot.configmanger.common.utils.excel.annotation.ExcelField;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.integration.json4excel.annotation.J4EName;
import org.nutz.lang.util.NutMap;

import java.util.Map;

@Data
public  class I18NModel extends CommonModel {
    /**
     * 名称
     */
    @Column("cn_name")
    @Comment("名称")
    @GraphQLQuery(name = "cn_name", description = "中文名")
    @J4EName( "名称")
    private String cnName;
    /**
     * 编码
     */
    @Column("en_name")
    @Comment("编码")
    @GraphQLQuery(name = "en_name", description = "english name")
    @J4EName("英文名称")
    private String enName;

    @GraphQLQuery(name = "i18n", description = "组合名")
    public Map<String,Object> geti18n(){
        return NutMap.NEW().addv("cn_name",this.cnName).addv("en_name",this.enName);
    }





}
