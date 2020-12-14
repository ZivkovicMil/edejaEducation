package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.controllers.UsersController;
import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.helpers.ReminderMode;
import com.edeja.edejaEducation.helpers.UserPlayList;
import com.edeja.edejaEducation.models.UserPlay;
import com.edeja.edejaEducation.models.XUserPlay;
import com.edeja.edejaEducation.repositories.adminDao.XUserDao;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendStatisticsServiceImpl implements SendStatisticsService {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    private LocalDateTime currentTime;

    @Autowired
    private UserPlayList userPlayList;

    @Autowired
    private UserActivityLogService userActivityLogService;

    @Autowired
    private XUserDao xUserDao;

    @Autowired
    private TimeLockerService timeLockerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CsvFileService csvFileService;

    public void check() {
        currentTime = LocalDateTime.now();
        for (UserPlay user : userPlayList.getList()) {
            if (xUserDao.findAccountByLogin(user.getUsername()) != null) {
                if (!timeLockerService.getMap().containsKey(user.getUsername())) {
                    timeLockerService.getMap().put(user.getUsername(), new XUserPlay());
                }
                if (user.getReminderMode().equals(ReminderMode.ONETIME)) {
                    if (timeLockerService.getMap().get(user.getUsername()).getCountOfTries() < 1) {
                        checkNotificationType(user);
                        timeLockerService.updateSet(user, currentTime, 1);
                    }
                } else {
                    if (checkFlag(user)) {
                        if (timeLockerService.getMap().get(user.getUsername()).getCountOfTries() < user.getCounter()) {
                            if (timeCheck(user)) {
                                checkNotificationType(user);
                                timeLockerService.updateSet(user, currentTime, timeLockerService.getMap().get(user.getUsername()).getCountOfTries() + 1);
                            } else {
                                log.info("Stats are not send due little time between two sends!");
                            }
                            if (timeLockerService.getMap().get(user.getUsername()).getCountOfTries() == user.getCounter()) {
                                xUserDao.updateFlagStatistics(user.getUsername(), false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void resetUser(XUser xUser) {
        xUserDao.updateFlagStatistics(xUser.getEmail(), true);
        timeLockerService.getMap().remove(xUser.getEmail());
    }

    private boolean checkFlag(UserPlay userPlay) {
        if (xUserDao.findAccountByLogin(userPlay.getUsername()).getSendStatistic()) {
            return true;
        }
        return false;
    }

    private boolean timeCheck(UserPlay userPlay) {
        if (timeLockerService.getMap().get(userPlay.getUsername()).getTimeOfSec() == null) {
            return true;
        }
        Period period = new Period(currentTime, timeLockerService.getMap().get(userPlay.getUsername()).getTimeOfSec());
        if (Math.abs(period.getSeconds()) >= userPlay.getTimeInSec()) {
            return true;
        }
        return false;
    }

    private void checkNotificationType(UserPlay userPlay) {
        switch (userPlay.getNotificationByType()) {
            case LOG:
                log.info("Statistics for user {} :{}", userPlay.getUsername(), userActivityLogService.createStats());
                break;
            case CSV:
                csvFileService.createFile(userPlay.getNameOfCSVFile(), userActivityLogService.createStats());
                log.info("Files are written in csv");
                break;
            case EMAIL:
                emailService.sendMimeMessageStatistics(userPlay.getUsername(), "Statistics", userActivityLogService.createStats());
                break;
        }
    }
}
