package com.edeja.edejaEducation.models;

import com.edeja.edejaEducation.helpers.ReminderMode;
import com.edeja.edejaEducation.helpers.SendNotificationByType;

public class UserPlay {

    private String username;
    private Integer timeInSec;
    private ReminderMode reminderMode;
    private SendNotificationByType notificationByType;
    private Integer counter;
    private String nameOfCSVFile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(Integer timeInSec) {
        this.timeInSec = timeInSec;
    }

    public ReminderMode getReminderMode() {
        return reminderMode;
    }

    public void setReminderMode(ReminderMode reminderMode) {
        this.reminderMode = reminderMode;
    }

    public SendNotificationByType getNotificationByType() {
        return notificationByType;
    }

    public void setNotificationByType(SendNotificationByType notificationByType) {
        this.notificationByType = notificationByType;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getNameOfCSVFile() {
        return nameOfCSVFile;
    }

    public void setNameOfCSVFile(String nameOfCSVFile) {
        this.nameOfCSVFile = nameOfCSVFile;
    }
}
