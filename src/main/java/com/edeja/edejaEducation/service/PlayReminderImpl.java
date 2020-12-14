package com.edeja.edejaEducation.service;

import com.edeja.edejaEducation.services.SendStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayReminderImpl implements PlayReminder {
    private static final Logger log = LoggerFactory.getLogger(PlayReminderImpl.class);

    @Autowired
    private SendStatisticsService sendStatisticsService;

    @Override
    public void remindUsers() {
        log.info("Schedule is working!");
        sendStatisticsService.check();
    }
}
