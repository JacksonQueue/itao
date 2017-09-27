package com.leafnet.itao.model.sys;

import com.leafnet.itao.model.basic.BasicEntity;

public class SysManagerUser extends BasicEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 座机号码
     */
    private String tel;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 所属部门
     */
    private String deptId;

    /**
     * 当前状态
     */
    private String userStatus;

    /**
     * 是否锁住
     */
    private Boolean isLocked;

    /**
     * 备注
     */
    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", lastModifyTime=").append(lastModifyTime);
        sb.append(", validity=").append(validity);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", tel=").append(tel);
        sb.append(", mobile=").append(mobile);
        sb.append(", deptId=").append(deptId);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", isLocked=").append(isLocked);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}