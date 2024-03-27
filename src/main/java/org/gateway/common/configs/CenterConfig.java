package org.gateway.common.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class CenterConfig {
	/**
	 * ${@link https://stackoverflow.com/questions/30337582/spring-boot-configure-and-use-two-data-sources}
	 * ${@link https://www.baeldung.com/spring-data-jpa-multiple-databases}
	 */
//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix="spring.task.execution.pool.max-size")
//	public DataSource primaryDataSource() {
//		return DataSourceBuilder.create().build();
//	}

//	@Bean
//	@ConfigurationProperties(prefix="spring.secondDatasource")
//	public DataSource secondaryDataSource() {
//		return DataSourceBuilder.create().build();
//	}
}
