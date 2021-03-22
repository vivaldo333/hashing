package com.ks.hashing.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.ks.hashing")
@EnableTransactionManagement
public class SpringJdbcConfig {

    @Bean(name = "dataSource")
    @Profile("prod")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
        //dataSource.setUsername("guest_user");
        //dataSource.setPassword("guest_password");

        return dataSource;
    }

    /*In-memory DB*/
    @Primary
    @Bean(name = "dataSource")
    @Profile("dev")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setName("ksdb")
                .addScript("classpath:sql/schema.sql")
                .addScript("classpath:sql/init-data.sql")
                .build();
    }

    /*Docker MySql DB*/
    @Primary
    @Bean(name = "dataSource")
    @Profile("!dev")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceMySql() {
        //DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/ksdb?serverTimezone=UTC");
        //dataSource.setUsername( "root" );
        //dataSource.setPassword( "root" );
        //return dataSource;
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "transactionManager")
    @Profile("!dev")
    PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource datasource) {
        return new JdbcTransactionManager(datasource); // DataSourceTransactionManager
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
