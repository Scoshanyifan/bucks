package com.kunbu.spring.bucks.common.param.mongo;

import com.kunbu.spring.bucks.common.param.BaseQueryParam;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-27 16:46
 **/
public class OperateLogQueryParam extends BaseQueryParam {

    private String operateType;

    private String content;

    private String operatorId;

    private String operateTimeStart;

    private String operateTimeEnd;

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

    public String getOperateTimeStart() {
        return operateTimeStart;
    }

    public void setOperateTimeStart(String operateTimeStart) {
        this.operateTimeStart = operateTimeStart;
    }

    public String getOperateTimeEnd() {
        return operateTimeEnd;
    }

    public void setOperateTimeEnd(String operateTimeEnd) {
        this.operateTimeEnd = operateTimeEnd;
    }

    @Override
    public String toString() {
        return "OperateLogQueryParam{" +
                "operateType='" + operateType + '\'' +
                ", content='" + content + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operateTimeStart='" + operateTimeStart + '\'' +
                ", operateTimeEnd='" + operateTimeEnd + '\'' +
                "} " + super.toString();
    }
}
