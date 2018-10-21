package ro.ubb.jdbctemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.jdbctemplate.repository.StudentRepository;
import ro.ubb.jdbctemplate.repository.StudentRepositoryImpl;

@Configuration
public class CatalogConfig {
    @Bean
    StudentRepository studentRepository() {
        return new StudentRepositoryImpl();
    }
}
