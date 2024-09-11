package com.fdmgroup.backend_streamhub.quartz_scheduler.service;

import com.fdmgroup.backend_streamhub.authenticate.dto.RegistrationRequest;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.*;
import com.fdmgroup.backend_streamhub.authenticate.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobService {
  
private final AccountService accountService;

  @Autowired
  public JobService(AccountService accountService) {
    this.accountService = accountService;
  }

  private static final Logger jobServiceLogger = LogManager.getLogger(JobService.class);

  public void executeInitJobs()
      throws InvalidEmailAddressException,
          UnavailableEmailAddressException,
          InvalidPasswordException,
          InvalidUsernameException,
          UnavailableUsernameException
        // commented out missing exception - tim
        //            , UnavailablePasswordException
      {
    jobServiceLogger.info("Starting initialization for mock users...");
    RegistrationRequest user1 =
        new RegistrationRequest("John.lim", "john.lim@yahoo.com", "O3!afa4aafa");
    RegistrationRequest user2 =
        new RegistrationRequest("MockUser", "User@gmail.com", "Password123.");

    if (accountService.pingUser(user1) == null) {
      accountService.registerUser(user1);
      accountService.registerUser(user2);
    } else {
      jobServiceLogger.info("Mock users already initialized.");
    }
  }
}
