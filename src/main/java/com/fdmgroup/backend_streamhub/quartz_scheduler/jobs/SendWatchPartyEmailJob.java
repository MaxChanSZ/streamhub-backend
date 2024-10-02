package com.fdmgroup.backend_streamhub.quartz_scheduler.jobs;

import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.WatchPartyRepository;
import externalServices.email_service.service.EmailServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class SendWatchPartyEmailJob extends QuartzJobBean {

    @Autowired
    private WatchPartyRepository watchPartyRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<WatchParty> upcomingWatchParties = watchPartyRepository.findByScheduledDateAndScheduledTimeAfter(
                today.toString(), now.toString());

        for (WatchParty watchParty : upcomingWatchParties) {
            String to = watchParty.getAccount().getEmail();
            String subject = "Upcoming Watch Party: " + watchParty.getPartyName();
            String text = String.format("Your watch party '%s' is scheduled for %s at %s. Don't forget to join!",
                    watchParty.getPartyName(), watchParty.getScheduledDate(), watchParty.getScheduledTime());

            emailService.sendSimpleMessage(to, subject, text);
        }
    }
}

