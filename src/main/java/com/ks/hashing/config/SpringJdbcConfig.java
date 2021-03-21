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
@ComponentScan("package com.ks.hashing")
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

    /*@Primary
    @Bean(name = "dataSource")
    @Profile("dev")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        //DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/ksdb?serverTimezone=UTC");
        //dataSource.setUsername( "root" );
        //dataSource.setPassword( "root" );
        //return dataSource;
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "transactionManager")
    @Primary
    PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource datasource) {
        PlatformTransactionManager transactionManager = new JdbcTransactionManager(datasource); // DataSourceTransactionManager,
        return transactionManager;
    }*/

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
