package com.melhorpreco.scraping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConnectionTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void testPostgreSQLConnection() {
        assertThat(dataSource).isNotNull();
        try {
            jdbcTemplate.execute("SELECT 1");
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(false).as("Failed to connect to PostgreSQL: " + e.getMessage()).isTrue();
        }
    }

    @Test
    void testRedisConnection() {
        assertThat(redisTemplate).isNotNull();
        try {
            redisTemplate.opsForValue().set("test_key", "test_value");
            assertThat(redisTemplate.opsForValue().get("test_key")).isEqualTo("test_value");
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(false).as("Failed to connect to Redis: " + e.getMessage()).isTrue();
        }
    }
}
