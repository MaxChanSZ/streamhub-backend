package com.fdmgroup.backend_streamhub.quartz_scheduler.config;

import com.fdmgroup.backend_streamhub.quartz_scheduler.jobs.InitJobs;
import com.fdmgroup.backend_streamhub.quartz_scheduler.jobs.SendWatchPartyEmailJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

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
                .withDescription("Send emails for upcoming watch parties")
                .build();
    }

    @Bean
    public Trigger watchPartyEmailTrigger(JobDetail watchPartyEmailJobDetail) {
        return TriggerBuilder.newTrigger().forJob(watchPartyEmailJobDetail)
                .withIdentity("WatchPartyEmailTrigger")
                .withDescription("Trigger for sending watch party emails")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))  // Run daily at midnight
                .build();
    }
}
