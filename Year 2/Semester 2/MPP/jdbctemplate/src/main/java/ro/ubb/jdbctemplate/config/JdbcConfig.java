package ro.ubb.jdbctemplate.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    JdbcOperations jdbcOperations() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(Driver.class.getName());
        ds.setUrl("jdbc:postgresql://localhost:5432/jdbctemplate");
        ds.setUsername(System.getProperty("username"));
        ds.setPassword(System.getProperty("password"));
        ds.setInitialSize(2);
        return ds;
    }
}
