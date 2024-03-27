package org.gateway.common.configs.schedule;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "spring.scheduler.enabled")
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    @Bean
    public CustomTaskScheduler taskScheduler() {
        return new CustomTaskScheduler();
    }
}
