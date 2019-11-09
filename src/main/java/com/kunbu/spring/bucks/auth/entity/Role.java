package com.kunbu.spring.bucks.auth.entity;

import com.kunbu.spring.bucks.constant.biz.CommonStateEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @project: cloud-lift-base
 * @author: kunbu
 * @create: 2019-10-31 11:03
 **/
@Document(collection = "sys_role")
public class Role implements Serializable {

    public static final String ROLE_NAME = "name";
    public static final String ROLE_ID = "id";
    public static final String ROLE_OPERATETIME = "operateTime";

    /**
     * 角色Id
     */
    @Id
    private String id;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态，是否可以使用
     */
    private String state= CommonStateEnum.USE.name();
    /**
     * 角色描述
     */
    private String remark;
    /**
     * 更新时间
     */
    private Date operateTime;
    /**
     * 操作者
     */
    private String operator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                ", operateTime=" + operateTime +
                ", operator='" + operator + '\'' +
                '}';
    }
}
