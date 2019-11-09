package com.kunbu.spring.bucks.auth;

import com.kunbu.spring.bucks.auth.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-11-01 09:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    private static final Logger logger = LoggerFactory.getLogger(RoleTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testRoleId() {

        Role role = new Role();
        role.setRoleName("超级管理员");

        mongoTemplate.insert(role);

        System.out.println(role);
    }

}
