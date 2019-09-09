package com.kunbu.spring.bucks.controller;

import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.common.entity.redis.UserInfo;
import com.kunbu.spring.bucks.config.MailConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 15:25
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private MailConfigResource mailConfigResource;

    @GetMapping("/get")
    public ApiResult hello(String text, HttpServletRequest request) {

//        logger.info(">>> mail config: {}", mailConfigResource);

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("2333");
        userInfo.setUserName("kunbu");
        request.getSession().setAttribute("userInfo", userInfo);
        return ApiResult.success("hi" + text);
    }

    @GetMapping("/error")
    public ApiResult error(String text) {

        throw new RuntimeException("hello error " + text);
    }

}
