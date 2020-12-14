package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.models.UserPlay;
import com.edeja.edejaEducation.models.XUserPlay;
import org.joda.time.LocalDateTime;

import java.util.Map;

public interface TimeLockerService {
    Map<String, XUserPlay> getMap();

    void updateSet(UserPlay userPlay, LocalDateTime timeOfSec, Integer countOfTrys);
}
