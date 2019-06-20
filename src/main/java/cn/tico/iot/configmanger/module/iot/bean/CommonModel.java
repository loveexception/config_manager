package cn.tico.iot.configmanger.module.iot.bean;

import cn.tico.iot.configmanger.module.sys.models.User;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.random.R;

import java.util.Date;

@Data
public abstract class CommonModel {


    @Name
    @Column("id")
    @Comment
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @Prev(els = {@EL("uuid()")})
    @GraphQLQuery
    private String id;


    @Column("create_by")
    @Comment("创建者")
    @Prev(els = @EL("$me.uid()"))
    @ColDefine(type = ColType.VARCHAR, width = 32)
    protected String createBy;


    @Column("create_time")
    @Prev(els = {@EL("$me.now()")})
    protected Date createTime;

    @Column("update_by")
    @Comment("更新者")
    @Prev(els = @EL("$me.uid()"))
    @ColDefine(type = ColType.VARCHAR, width = 32)
    protected String updateBy;

    @Prev(els=@EL("$me.now()"))
    @Column("update_time")
    protected Date updateTime;

    public String uuid() {
        return R.UU32().toLowerCase();
    }

    public String uid() {
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            return user == null ? "" : user.getId();
        } catch (Exception e) {
            return "";
        }
    }

    public Date now() {
        return new Date();
    }
}
