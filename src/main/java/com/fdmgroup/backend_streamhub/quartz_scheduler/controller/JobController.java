package com.fdmgroup.backend_streamhub.quartz_scheduler.controller;

import com.fdmgroup.backend_streamhub.quartz_scheduler.jobs.InitJobs;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
public class JobController {

    @Autowired
    private Scheduler scheduler;

    //Manual trigger for mock-user init if necessary
    @PostMapping("jobs/mock-user-init")
    public ResponseEntity test() {
        try {
            JobDetail jobDetail = JobBuilder.newJob(InitJobs.class).withIdentity(UUID.randomUUID().toString()).build();
            Trigger trigger = buildTrigger(jobDetail, ZonedDateTime.now());

            scheduler.scheduleJob(jobDetail, trigger);

            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    private JobDetail buildJobDetail(){
        return JobBuilder.newJob(InitJobs.class)
                .withIdentity(UUID.randomUUID().toString())
                .withDescription("Starting init")
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "init-triggers")
                .withDescription("Init Trigger")
                .startNow()
                .build();
    }
}
