package com.grupogbs.bot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DelsoftDataAccessConfig {

	@Bean
    @ConfigurationProperties("app.datasource.delsoft")
    public DataSourceProperties delsoftDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.delsoft.configuration")
    public HikariDataSource delsoftDataSource(DataSourceProperties delsoftDataSourceProperties) {
        return delsoftDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean
    public JdbcTemplate delsoftJdbcTemplate(@Qualifier("delsoftDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
	
}
