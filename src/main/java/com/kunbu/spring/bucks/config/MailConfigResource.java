package com.kunbu.spring.bucks.config;

import com.kunbu.spring.bucks.utils.mail.MailConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 10:13
 **/
@Component
@PropertySource("classpath:application-context.yml")
public class MailConfigResource implements MailConfig {

    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.port}")
    private Integer port;
    /**
     * 属性信息：
     * mail.debug
     * mail.smtp.auth
     * mail.smtp.timeout
     * mail.smtp.starttls.enable
     * mail.smtp.socketFactory.class
     * ... ...
     */
    @Value("${mail.properties}")
    private HashMap<String, String> properties;


    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }
}
