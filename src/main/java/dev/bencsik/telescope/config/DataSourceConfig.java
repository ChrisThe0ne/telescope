package dev.bencsik.telescope.config;

import dev.bencsik.telescope.tenancy.MultiTenantDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        MultiTenantDataSource multiTenantDataSource = new MultiTenantDataSource();
        Map<Object, Object> tenantDataSources = new HashMap<>();

        // Default master database
        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
        defaultDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        defaultDataSource.setUrl(env.getProperty("spring.datasource.url"));
        defaultDataSource.setUsername(env.getProperty("spring.datasource.username"));
        defaultDataSource.setPassword(env.getProperty("spring.datasource.password"));

        tenantDataSources.put("master", defaultDataSource);
        multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource);
        multiTenantDataSource.setTargetDataSources(tenantDataSources);
        multiTenantDataSource.afterPropertiesSet();

        return multiTenantDataSource;
    }
}
