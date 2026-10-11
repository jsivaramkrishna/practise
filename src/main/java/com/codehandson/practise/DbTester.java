package com.codehandson.practise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbTester implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DbTester.class);
    private final JdbcTemplate jdbcTemplate;

    public DbTester(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.info("Database connection test successful, SELECT 1 returned: {}", result);
        } catch (Exception e) {
            logger.error("Database connection test failed", e);
        }
    }
}

