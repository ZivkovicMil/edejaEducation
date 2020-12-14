package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.models.UserPlay;
import com.edeja.edejaEducation.models.XUserPlay;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimeLockerServiceImpl implements TimeLockerService {
    private Map<String, XUserPlay> map = new HashMap();

    public Map<String, XUserPlay> getMap() {
        return map;
    }

    public void updateSet(UserPlay userPlay, LocalDateTime timeOfSec, Integer countOfTries) {
        for (String key : map.keySet()) {
            if (key.equals(userPlay.getUsername())) {
                map.get(key).setTimeOfSec(timeOfSec);
                map.get(key).setCountOfTries(countOfTries);
            }
        }
    }
}
