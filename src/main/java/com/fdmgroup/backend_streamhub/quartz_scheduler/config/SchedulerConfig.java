package com.fdmgroup.backend_streamhub.quartz_scheduler.config;

import com.fdmgroup.backend_streamhub.quartz_scheduler.jobs.SendWatchPartyEmailJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@EnableAutoConfiguration
public class SchedulerConfig {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Bean
    public JobDetail watchPartyEmailJobDetail() {
        return JobBuilder.newJob().ofType(SendWatchPartyEmailJob.class)
                .storeDurably()
                .withIdentity("WatchPartyEmailJob")
                .withDescription("Send reminder emails for upcoming watch parties")
                .build();
    }

    @Bean
    public Trigger watchPartyEmailTrigger(JobDetail watchPartyEmailJobDetail) {
        return TriggerBuilder.newTrigger().forJob(watchPartyEmailJobDetail)
                .withIdentity("WatchPartyEmailTrigger")
                .withDescription("Trigger for sending watch party reminder emails")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))  // Run every minute
                .build();
    }
}

