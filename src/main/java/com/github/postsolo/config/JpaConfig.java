package com.github.postsolo.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.github.postsolo.repository.users","com.github.postsolo.repository.userRole","com.github.postsolo.repository.roles","com.github.postsolo.repository.comment","com.github.postsolo.repository.Post","com.github.postsolo.repository.like","com.github.postsolo.repository.commentByComment"},
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
        transactionManagerRef = "tmJpa"

)
@RequiredArgsConstructor
public class JpaConfig {
//    private final DataSource dataSource;




    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier("dataSource")DataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.github.postsolo.repository.users","com.github.postsolo.repository.userRole","com.github.postsolo.repository.roles","com.github.postsolo.repository.comment","com.github.postsolo.repository.Post","com.github.postsolo.repository.like","com.github.postsolo.repository.commentByComment");

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql","true");
        properties.put("hibernate.use_sql_comment","true");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "tmJpa")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean(dataSource).getObject());
        return transactionManager;
    }
}
