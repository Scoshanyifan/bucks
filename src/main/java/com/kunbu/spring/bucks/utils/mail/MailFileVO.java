package com.kunbu.spring.bucks.utils.mail;

import java.io.InputStream;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 10:25
 **/
public class MailFileVO {

    private InputStream resource;
    private String name;
    /** MIME type */
    private String type;

    public MailFileVO(InputStream resource, String name, String type) {
        super();
        this.resource = resource;
        this.name = name;
        this.type = type;
    }
    public InputStream getResource() {
        return resource;
    }
    public void setResource(InputStream resource) {
        this.resource = resource;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
