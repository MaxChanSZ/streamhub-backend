package com.fdmgroup.backend_streamhub.quartz_scheduler.jobs;

import com.fdmgroup.backend_streamhub.authenticate.exceptions.*;
import com.fdmgroup.backend_streamhub.quartz_scheduler.service.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InitJobs extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(InitJobs.class);

    private final JobService jobService;

    @Autowired
    public InitJobs(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    protected void executeInternal(@NonNull JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("Executing InitJobs");
            this.jobService.executeInitJobs();
            logger.info("InitJobs executed successfully");
        } catch (InvalidEmailAddressException | UnavailableEmailAddressException |
                 InvalidPasswordException | InvalidUsernameException |
                 UnavailableUsernameException | UnavailablePasswordException e) {
            logger.error("Error executing InitJobs: {}", e.getMessage());
            throw new JobExecutionException("Failed to execute InitJobs", (Throwable) e, false);
        }
    }
}


