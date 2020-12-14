package com.edeja.edejaEducation.models;

import org.joda.time.LocalDateTime;

public class XUserPlay {
    private LocalDateTime timeOfSec;
    private int countOfTries;

    public LocalDateTime getTimeOfSec() {
        return timeOfSec;
    }

    public void setTimeOfSec(LocalDateTime timeOfSec) {
        this.timeOfSec = timeOfSec;
    }

    public int getCountOfTries() {
        return countOfTries;
    }

    public void setCountOfTries(int countOfTrys) {
        this.countOfTries = countOfTrys;
    }
}
