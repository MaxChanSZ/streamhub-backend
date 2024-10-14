package com.fdmgroup.backend_streamhub.quartz_scheduler.jobs;

import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IWatchPartyRepository;
import externalServices.email_service.service.EmailServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SendWatchPartyEmailJob extends QuartzJobBean {

    @Autowired
    private IWatchPartyRepository watchPartyRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMinutesLater = now.plusMinutes(10);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String currentDate = now.format(dateFormatter);
        String startTime = now.format(timeFormatter);
        String endTime = tenMinutesLater.format(timeFormatter);

        List<WatchParty> upcomingWatchParties = watchPartyRepository.findByScheduledDateAndScheduledTimeBetweenAndReminderEmailSentFalse(
                currentDate,
                startTime,
                endTime
        );

        for (WatchParty watchParty : upcomingWatchParties) {
            String to = watchParty.getAccount().getEmail();
            String subject = "Reminder: Watch Party Starting Soon - " + watchParty.getPartyName();
            String text = String.format("Your watch party '%s' is starting in 10 minutes! Don't forget to join at %s.",
                    watchParty.getPartyName(), watchParty.getScheduledTime());

            emailService.sendSimpleMessage(to, subject, text);

            watchParty.setReminderEmailSent(true);
            watchPartyRepository.save(watchParty);
        }
    }
}



