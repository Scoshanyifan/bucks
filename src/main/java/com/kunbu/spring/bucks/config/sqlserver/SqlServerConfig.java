package com.kunbu.spring.bucks.config.sqlserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-25 13:29
 **/
//@Configuration
public class SqlServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SqlServerConfig.class);

    @Bean(name = "sqlServerDataSource")
    @Qualifier("sqlServerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource getSqlServerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlServerTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("sqlServerDataSource") DataSource dataSource) {

        logger.info(">>> dataSource:{}", dataSource);

        return new JdbcTemplate(dataSource);
    }

}
