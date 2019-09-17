package com.kunbu.spring.bucks.controller;

import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.common.entity.redis.UserInfo;
import com.kunbu.spring.bucks.config.MailConfigResource;
import com.kunbu.spring.bucks.utils.HttpRequestPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 15:25
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MailConfigResource mailConfigResource;

    @GetMapping("/mail")
    public ApiResult checkMailResource() {

        return ApiResult.success(mailConfigResource);
    }

    @PostMapping("/login")
    public ApiResult login(@RequestParam String name, HttpServletRequest request) {

        HttpRequestPrintUtil.printHttpRequest(request, logger);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(name);
        request.getSession().setAttribute("userInfo", userInfo);
        return ApiResult.success();
    }

    @GetMapping("/name")
    public ApiResult name(HttpServletRequest request) {

        return ApiResult.success(request.getSession().getAttribute("userInfo"));
    }

}
