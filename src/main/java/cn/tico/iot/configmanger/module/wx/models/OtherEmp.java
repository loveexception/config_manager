package cn.tico.iot.configmanger.module.wx.models;

import cn.tico.iot.configmanger.common.base.BaseModel;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 运维人员表 other_Emp
 * 
 * @author maodajun
 * @date 2019-08-05
 */
@Table("other_Emp")
public class OtherEmp extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Name
	@Column("id")
	@Comment("id")
	@ColDefine(type = ColType.VARCHAR, width = 64)
	@Prev(els = {@EL("uuid()")})
	private String id;

			/** 序号 */
	@Column("no")
	@Comment("序号")
	private String no;

			/** 部门 */
	@Column("dept_name")
	@Comment("部门")
	private String deptName;

			/** 职位 */
	@Column("title")
	@Comment("职位")
	private String title;

			/** 联系电话 */
	@Column("tel")
	@Comment("联系电话")
	private String tel;

			/** 邮箱：  */
	@Column("email")
	@Comment("邮箱： ")
	private String email;

			/** 照片：  */
	@Column("image")
	@Comment("照片： ")
	private String image;

			/** 名称 */
	@Column("cn_name")
	@Comment("名称")
	private String cnName;

			/** 编码 */
	@Column("en_name")
	@Comment("编码")
	private String enName;

			/** 状态 */
	@Column("status")
	@Comment("状态")
	private String status;

			/** 删除 */
	@Column("delflag")
	@Comment("删除")
	private String delflag;

			/** 创建者 */
	@Column("create_by")
	@Comment("创建者")
	private String createBy;

			/** 建立时间 */
	@Column("create_time")
	@Comment("建立时间")
	private Date createTime;

			/** 更新者 */
	@Column("update_by")
	@Comment("更新者")
	private String updateBy;

			/** 更新时间 */
	@Column("update_time")
	@Comment("更新时间")
	private Date updateTime;

	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}

	public void setNo(String no) 
	{
		this.no = no;
	}

	public String getNo() 
	{
		return no;
	}

	public void setDeptName(String deptName) 
	{
		this.deptName = deptName;
	}

	public String getDeptName() 
	{
		return deptName;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTel(String tel) 
	{
		this.tel = tel;
	}

	public String getTel() 
	{
		return tel;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setImage(String image) 
	{
		this.image = image;
	}

	public String getImage() 
	{
		return image;
	}

	public void setCnName(String cnName) 
	{
		this.cnName = cnName;
	}

	public String getCnName() 
	{
		return cnName;
	}

	public void setEnName(String enName) 
	{
		this.enName = enName;
	}

	public String getEnName() 
	{
		return enName;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setDelflag(String delflag) 
	{
		this.delflag = delflag;
	}

	public String getDelflag() 
	{
		return delflag;
	}

	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}

	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}

	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("no", getNo())
            .append("deptName", getDeptName())
            .append("title", getTitle())
            .append("tel", getTel())
            .append("email", getEmail())
            .append("image", getImage())
            .append("cnName", getCnName())
            .append("enName", getEnName())
            .append("status", getStatus())
            .append("delflag", getDelflag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
