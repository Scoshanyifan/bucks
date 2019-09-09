package com.kunbu.spring.bucks.utils.mail;

import com.alibaba.fastjson.JSONObject;
import com.kunbu.spring.bucks.config.MailConfigResource;
import com.kunbu.spring.bucks.utils.DateFormatUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * 异常邮件工具
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 10:51
 **/
@Component
public class ExceptionMailUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMailUtil.class);

    private static final String EXCEPTION_FORMAT = "%s\n%s\n%s";

    @Autowired
    private MailConfigResource mailConfigResource;

    /**
     * 发送异常
     *
     * @param error 异常信息
     * @param title 标头
     * @param content 业务数据
     * @author kunbu
     * @time 2019/9/4 11:04
     * @return
     **/
    public void sendException(Throwable error, Object title, Object content) {

        String[] tos = {"1274462659@qq.com"};

        String timeStr = DateFormatUtils.format(new Date(), DateFormatUtil.DATE_PATTERN_7);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        error.printStackTrace(new PrintStream(bos));

        String exceptionMsg = String.format(EXCEPTION_FORMAT, title, JSONObject.toJSONString(content.toString()), bos.toString());
        try {
            MailSenderUtil.sendTextSimple(tos, title + timeStr, exceptionMsg, mailConfigResource);
        } catch (Exception e) {
            logger.error(">>> ExceptionMailUtil send mail error.", e);
        }

    }


}
