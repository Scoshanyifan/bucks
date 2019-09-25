package com.kunbu.spring.bucks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-25 13:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlServerTest {

    private static final Logger logger = LoggerFactory.getLogger(SqlServerTest.class);

    @Autowired
    private JdbcTemplate sqlServerTemplate;

    @Test
    public void testDataSource() {
        String sql = "select count(*) from HRMS_YLYTN.dbo.BAsset";
        sqlServerTemplate.queryForObject(sql, Integer.class);
    }

}
