package com.kunbu.spring.bucks.error;

/**
 * @author: KunBu
 * @time: 2019/8/23 13:42
 * @description:
 */
public interface MicroServiceError {

    String getServiceErrorCode();

    String getServiceErrorMsg();

}
