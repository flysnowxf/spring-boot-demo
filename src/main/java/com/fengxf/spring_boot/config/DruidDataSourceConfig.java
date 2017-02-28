package com.fengxf.spring_boot.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
	}

	@Bean
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(propertyResolver.getProperty("url"));
		datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
		datasource.setUsername(propertyResolver.getProperty("username"));
		datasource.setPassword(propertyResolver.getProperty("password"));
		// datasource.setTimeBetweenLogStatsMillis(60000);

		try {
			datasource.setFilters("stat,wall");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return datasource;
	}

}
