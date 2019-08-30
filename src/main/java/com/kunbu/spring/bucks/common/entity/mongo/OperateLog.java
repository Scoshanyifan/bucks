package com.kunbu.spring.bucks.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 操作日志
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-08-30 17:13
 **/
@Document(collection = "operatelog")
public class OperateLog {

    @Id
    private String id;

    private String operateType;

    private String content;

    private String operatorId;

    private Date operateTime;

    private String operateIp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    @Override
    public String toString() {
        return "OperateLog{" +
                "id='" + id + '\'' +
                ", operateType='" + operateType + '\'' +
                ", content='" + content + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }
}
