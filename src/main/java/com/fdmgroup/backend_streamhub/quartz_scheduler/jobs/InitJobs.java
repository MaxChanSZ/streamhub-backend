package com.fdmgroup.backend_streamhub.quartz_scheduler.jobs;

import com.fdmgroup.backend_streamhub.authenticate.exceptions.*;
import com.fdmgroup.backend_streamhub.quartz_scheduler.service.JobService;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class InitJobs extends QuartzJobBean {

    @Autowired private JobService jobService;
    private final Logger logger = Logger.getLogger(InitJobs.class.getName());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("InitJobsExecutedSuccessfully");
            this.jobService.executeInitJobs();
        } catch (InvalidEmailAddressException e) {
            throw new RuntimeException(e);
        } catch (UnavailableEmailAddressException e) {
            throw new RuntimeException(e);
        } catch (InvalidPasswordException e) {
            throw new RuntimeException(e);
        } catch (InvalidUsernameException e) {
            throw new RuntimeException(e);
        } catch (UnavailableUsernameException e) {
            throw new RuntimeException(e);
        }
    }
    // commented out missing exception
    //        catch (UnavailablePasswordException e) {
    //            throw new RuntimeException(e);
    //        }
}


