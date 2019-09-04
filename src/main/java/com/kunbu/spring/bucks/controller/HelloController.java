package com.kunbu.spring.bucks.controller;

import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.config.MailConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResult hello(String text) {

        logger.info(">>> mail config: {}", mailConfigResource);

        return ApiResult.success("hi" + text);
    }

}