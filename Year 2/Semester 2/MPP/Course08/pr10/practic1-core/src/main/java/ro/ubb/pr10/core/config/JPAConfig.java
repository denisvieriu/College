package ro.ubb.pr10.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by radu.
 */

@Configuration
@EnableJpaRepositories({"ro.ubb.pr10.core.repository"})
@EnableTransactionManagement
@EnableCaching
public class JPAConfig {


    private String jdbcUrl = "jdbc:postgresql://localhost:5432/practic1";


    private String username = "postgres";


    private String password = "postgres";


    private Boolean generateDDL = true;

    /**
     * http://www.baeldung.com/hikaricp
     *
     * @return
     */
        @Bean
        public DataSource dataSource() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setDriverClassName("org.postgresql.Driver");
            HikariDataSource dataSource = new HikariDataSource(config);
            return dataSource;
    }

//    @Bean
//    DataSource dataSource() {
//        BasicDataSource dataSource=new BasicDataSource();
//        dataSource.setDriverClassName(Driver.class.getName());
//        dataSource.setUrl(jdbcUrl);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setInitialSize(2);
//
//        return dataSource;
//    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(generateDDL);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ro.ubb.pr10.core.model");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory());
        return manager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }


}
