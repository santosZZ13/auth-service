package org.authen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CenterConfig {
	//	/**
//	 * ${@link https://stackoverflow.com/questions/30337582/spring-boot-configure-and-use-two-data-sources}
//	 * ${@link https://www.baeldung.com/spring-data-jpa-multiple-databases}
//	 */
////	@Bean
////	@Primary
////	@ConfigurationProperties(prefix="spring.task.execution.pool.max-size")
////	public DataSource primaryDataSource() {
////		return DataSourceBuilder.create().build();
////	}
//
////	@Bean
////	@ConfigurationProperties(prefix="spring.secondDatasource")
////	public DataSource secondaryDataSource() {
////		return DataSourceBuilder.create().build();
////	}
}
