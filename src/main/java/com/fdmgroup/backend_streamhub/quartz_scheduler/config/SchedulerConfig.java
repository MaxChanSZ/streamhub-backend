package com.fdmgroup.backend_streamhub.quartz_scheduler.config;

import com.fdmgroup.backend_streamhub.quartz_scheduler.jobs.InitJobs;
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
    public JobDetail jobDetail() {
        logger.info("jobDetail bean initalized");
        return JobBuilder.newJob().ofType(InitJobs.class)
                .storeDurably()
                .withIdentity("Initialization")
                .withDescription("Invoking init jobs...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        logger.info("trigger bean initalized");
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Initialization")
                .withDescription("Initialization trigger")
                .build();
    }

    @Bean
    public Scheduler scheduler(JobDetail job, Trigger trigger, SchedulerFactoryBean schedulerFactory) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        if (scheduler.checkExists(job.getKey())) {
            scheduler.deleteJob(job.getKey());
        }
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        logger.info("scheduler bean initalized");
        return scheduler;
    }
}
