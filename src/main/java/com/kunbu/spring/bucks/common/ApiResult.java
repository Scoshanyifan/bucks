package com.kunbu.spring.bucks.common;


import com.kunbu.spring.bucks.error.ApiError;
import com.kunbu.spring.bucks.error.MicroServiceError;
import com.kunbu.spring.bucks.error.bis.CommonErrorEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-practice
 * @description: API返回模型
 * @author: kunbu
 * @create: 2019-08-03 15:01
 **/
public class ApiResult implements Serializable {

    private boolean success;
    private Object data;
    private String errorCode;
    private String errorMsg;

    private ApiResult() {
    }

    public static ApiResult success() {
        ApiResult result = new ApiResult();
        result.setSuccess(true);
        return result;
    }

    public static ApiResult success(Object data) {
        ApiResult result = new ApiResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static ApiResult failure() {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        return result;
    }

    @Deprecated
    public static ApiResult error(String errorCode, String errorMsg) {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public static ApiResult error(ApiError apiError) {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        if (apiError != null) {
            result.setErrorCode(apiError.getErrorCode());
            result.setErrorMsg(apiError.getErrorMsg());
        }
        return result;
    }

    public static ApiResult error(MicroServiceError serviceError) {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        if (serviceError != null) {
            result.setErrorCode(serviceError.getServiceErrorCode());
            result.setErrorMsg(serviceError.getServieErrorMsg());
        }
        return result;
    }

    public static ApiResult SYSTEM_BUSY() {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setErrorCode(CommonErrorEnum.SYSTEM_BUSY.getErrorCode());
        result.setErrorMsg(CommonErrorEnum.SYSTEM_BUSY.getErrorMsg());
        return result;
    }

    public static ApiResult OPERATION_ILLEGAL() {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setErrorCode(CommonErrorEnum.OPERATION_ILLEGAL.getErrorCode());
        result.setErrorMsg(CommonErrorEnum.OPERATION_ILLEGAL.getErrorMsg());
        return result;
    }

    public static ApiResult PARAM_ILLEGAL() {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setErrorCode(CommonErrorEnum.PARAM_ILLEGAL.getErrorCode());
        result.setErrorMsg(CommonErrorEnum.PARAM_ILLEGAL.getErrorMsg());
        return result;
    }

    public static ApiResult TOKEN_EXPIRE() {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setErrorCode(CommonErrorEnum.TOKEN_EXPIRE.getErrorCode());
        result.setErrorMsg(CommonErrorEnum.TOKEN_EXPIRE.getErrorMsg());
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * API分页
     *
     * @author kunbu
     * @time 2019/8/23 14:11
     **/
    public static class ApiPage implements Serializable {
        private static final List EMPTY_LIST = new ArrayList();

        private long pageNum;
        private long pageSize;
        private long totalPage;
        private long totalCount;
        private Object list;

        private ApiPage() {
        }

        public static ApiPage empty(long pageNum, long pageSize) {
            ApiPage page = new ApiPage();
            page.setPageNum(pageNum);
            page.setPageSize(pageSize);
            page.setTotalPage(0);
            page.setTotalCount(0);
            page.setList(EMPTY_LIST);
            return page;
        }

        public static ApiPage success(long pageNum, long pageSize, long totalPage, long totalCount, Object list) {
            ApiPage page = new ApiPage();
            page.setPageNum(pageNum);
            page.setPageSize(pageSize);
            page.setTotalPage(totalPage);
            page.setTotalCount(totalCount);
            page.setList(list);
            return page;
        }

        public long getPageNum() {
            return pageNum;
        }

        public void setPageNum(long pageNum) {
            this.pageNum = pageNum;
        }

        public long getPageSize() {
            return pageSize;
        }

        public void setPageSize(long pageSize) {
            this.pageSize = pageSize;
        }

        public long getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(long totalPage) {
            this.totalPage = totalPage;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public Object getList() {
            return list;
        }

        public void setList(Object list) {
            this.list = list;
        }
    }
}
