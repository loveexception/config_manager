package cn.tico.iot.configmanger.module.sys.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import cn.tico.iot.configmanger.module.mao.models.Pushs;
import cn.tico.iot.configmanger.module.mao.models.Upgrades;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 部门
 * @author haiming
 */
@Table("sys_dept")
public class Dept extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    @Name
    @Comment("ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @Prev(els = {@EL("uuid()")})
    @GraphQLQuery(name = "id", description = "主键")
    private String id;

    /**
     * 父部门ID
     */
    @Column("parent_id")
    @Comment("父部门ID")
    @GraphQLQuery(name = "parent_id", description = "父部门ID")
    private String parentId;

    /**
     * 祖级列表
     */
    @Column("ancestors")
    @Comment("祖级列表")
    @GraphQLQuery(name = "ancestors", description = "祖级列表")

    private String ancestors;

    /**
     * 部门名称
     */
    @Column("dept_name")
    @Comment("部门名称")
    @GraphQLQuery(name = "dept_name", description = "部门名称")
    private String deptName;

    /**
     * 显示顺序
     */
    @Column("order_num")
    @Comment("显示顺序")
    @GraphQLQuery(name = "order_num", description = "显示顺序")

    private int orderNum;

    /**
     * 负责人
     */
    @Column("leader")
    @Comment("负责人")
    @GraphQLQuery(name = "leader", description = "负责人")

    private String leader;

    /**
     * 联系电话
     */
    @Column("phone")
    @Comment("联系电话")
    @GraphQLQuery(name = "phone", description = "联系电话")

    private String phone;
    /**
     * 邮箱
     */
    @Column("email")
    @Comment("邮箱")
    @GraphQLQuery(name = "email", description = "邮箱")
    private String email;

    /**
     * 状态（0正常 1停用）
     */
    @Column("status")
    @Comment("状态（true 正常 false停用） ")
    private boolean status;

    @Column("del_flag")
    @Comment("删除标记")
    @ColDefine(type = ColType.BOOLEAN)
    private boolean delFlag;


    private List<Upgrades> upgrades;

    private List<Pushs> pushs;


    public List<Upgrades> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(List<Upgrades> upgrades) {
        this.upgrades = upgrades;
    }

    public List<Pushs> getPushs() {
        return pushs;
    }

    public void setPushs(List<Pushs> pushs) {
        this.pushs = pushs;
    }




    /**
     * 父部门名称
     */
    private String parentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
