package com.example.employee.mgmt.system.dao;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Properties;

public class BaseDao {

    JdbcTemplate jdbcTemplate;

    /**
     * 初始化数据库操作对象
     */
    public BaseDao() {
        // 创建druid数据源
        DruidDataSource dataSource = new DruidDataSource();
        Properties prop = new Properties();
        try {
            // 加载配置文件
            prop.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSource.setUsername(prop.getProperty("jdbc.username"));
        dataSource.setPassword(prop.getProperty("jdbc.password"));
        dataSource.setUrl(prop.getProperty("jdbc.url"));
        dataSource.setDriverClassName(prop.getProperty("jdbc.driverClassName"));

        // 创建jdbcTemplate 并传入数据源
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
