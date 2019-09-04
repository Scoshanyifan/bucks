package com.kunbu.spring.bucks.config;

import com.kunbu.spring.bucks.utils.mail.MailConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 10:13
 **/
@Component
@ConfigurationProperties(prefix = "mail")
public class MailConfigResource implements MailConfig {

    private String host;
    private String userName;
    private String password;
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
    private HashMap<String, String> properties;

    public void setHost(String host) {
        this.host = host;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

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

    @Override
    public String toString() {
        return "MailConfigResource{" +
                "host='" + host + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", properties=" + properties +
                '}';
    }
}
