package com.kunbu.spring.bucks.controller;

import com.kunbu.spring.bucks.utils.HttpRequestPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录 @RequestMapping / @RequestParam / @RequestBody
 *
 * https://www.oschina.net/translate/using-the-spring-requestmapping-annotation
 * https://www.cnblogs.com/blogtech/p/11172168.html
 *
 * 关于GET / POST ，用post参数就不会显示在url上
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-17 10:13
 **/
@Controller
@ResponseBody
@RequestMapping("/basic")
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    /**
     * 将 HTTP 请求映射到 MVC 和 REST 控制器的处理方法上
     *
     * 可添加多个限定
     *
     * @param
     * @author kunbu
     * @time 2019/9/17 11:25
     * @return
     **/
    @RequestMapping(
            // 限定URI
            value = {"/", "/mapping"},
            // 限定请求方式
            method = {RequestMethod.GET, RequestMethod.POST},
            // 限定只处理json和xml
            consumes = {"application/json", "application/XML"},
            // 生成json响应
            produces = {"application/json"},
            // 限定只处理特定头的请求
            headers = {},
            // 限定只处理特定参数的请求
            params = {}
    )
    public Map<String ,Object> mapping( HttpServletRequest request,
            // 设置了默认值，required将自动设为false（测试带参数和不带参数的区别）
            @RequestParam(value = "name", defaultValue = "kunbu") String name) {

        HttpRequestPrintUtil.printHttpRequest(request, logger);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", name);
        return resultMap;
    }

    /**
     * 动态获取uri上的参数
     *
     * @param id
     * @author kunbu
     * @time 2019/9/17 11:26
     * @return
     **/
    @RequestMapping(value = "/value/{id}", method = RequestMethod.GET)
    public Map<String, Object> pathVariable(
            @PathVariable(value = "id", required = false) String id) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    /**
     * RequestParam 处理：
     *  1. url后的?参数
     *  2. form-data（）
     *  3. x-www-form-urlencoded
     *
     * @param request
     * @author kunbu
     * @time 2019/9/17 11:03
     * @return
     **/
    @RequestMapping(value = "/param", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> requestParam(HttpServletRequest request,
            // get post 均有效
            @RequestParam(value = "param", required = false) String param,
            // get post 均有效
            @RequestParam(value = "form-data", required = false) String formdata,
            // post 有效
            @RequestParam(value = "x-www-form-urlencoded", required = false) String xwwwformurlencoded) {

        HttpRequestPrintUtil.printHttpRequest(request, logger);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("param", param);
        resultMap.put("form-data", formdata);
        resultMap.put("x-www-form-urlencoded", xwwwformurlencoded);
        return resultMap;
    }

    /**
     * RequestBody 处理：
     *  1. Content-Type=application/json
     *  2. Content-Type=application/xml
     *  3. Content-Type=text/plain 等等
     *
     * @param request
     * @author kunbu
     * @time 2019/9/17 11:03
     * @return
     **/
    @RequestMapping(value = "/body", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> requestBody(HttpServletRequest request,
            // get post 均有效
            @RequestBody Map<String, Object> params) {

        HttpRequestPrintUtil.printHttpRequest(request, logger);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("params", params);
        return resultMap;
    }

}
