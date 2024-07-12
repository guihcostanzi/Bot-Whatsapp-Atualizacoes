package com.grupogbs.bot.config;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.grupogbs.bot.repository",
		entityManagerFactoryRef = "botEntityManagerFactory",
		transactionManagerRef = "botTransactionManager"
		)
public class BotDataAccessConfig {

    @Bean
    @ConfigurationProperties("app.datasource.bot")
    public DataSourceProperties botDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.bot.configuration")
    public HikariDataSource botDataSource(DataSourceProperties botDataSourceProperties) {
        return botDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean
    public JdbcTemplate botJdbcTemplate(@Qualifier("botDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean botEntityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        entityManagerFactoryBean.setDataSource(botDataSource(botDataSourceProperties()));
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("com.grupogbs.bot.entities");             

        return entityManagerFactoryBean;
    }
   
    private HibernateJpaVendorAdapter vendorAdaptor() {
       HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       vendorAdapter.setShowSql(true);
       return vendorAdapter;
    }
   
    @Bean
    public PlatformTransactionManager botTransactionManager(EntityManagerFactory botEntityManagerFactory) {
	
	   JpaTransactionManager txManager = new JpaTransactionManager();
	   txManager.setEntityManagerFactory(botEntityManagerFactory);
       return txManager;
    }
	
}
