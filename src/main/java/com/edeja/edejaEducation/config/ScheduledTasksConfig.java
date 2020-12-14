package com.edeja.edejaEducation.config;

import com.edeja.edejaEducation.service.PlayReminder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
public class ScheduledTasksConfig {

    private final PlayReminder playReminder;

    public ScheduledTasksConfig(PlayReminder playReminder) {
        this.playReminder = playReminder;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void remindUsers() {
        this.playReminder.remindUsers();
    }

}
