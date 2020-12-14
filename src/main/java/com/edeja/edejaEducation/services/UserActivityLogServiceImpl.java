package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;
import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.models.ActivityLogRequestDTO;
import com.edeja.edejaEducation.repositories.adminDao.XActivityLogDao;
import com.edeja.edejaEducation.repositories.adminDao.XUserDao;
import com.edeja.edejaEducation.types.Category;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserActivityLogServiceImpl implements UserActivityLogService {

    private static final Logger log = LoggerFactory.getLogger(UserActivityLogServiceImpl.class);

    @Autowired
    private XActivityLogDao xActivityLogDao;

    @Autowired
    private XUserDao xUserDao;

    @Value("${spring.application.name}")
    private String xapplicationName;

    @Override
    public void createActivityLog(XUser user, Category category, String details) {
        XActivityLog xActivityLog = XActivityLog
                .Builder
                .instance()
                .date(new Date())
                .category(category.name())
                .details(details)
                .senderName(createSenderName(user))
                .userName(user.getEmail())
                .userId(user.getXId())
                .applicationName(xapplicationName)
                .build();
        xActivityLogDao.save(xActivityLog);
    }

    @Override
    public Page<XActivityLog> allXActivities(ActivityLogRequestDTO activityLogRequestDTO, Pageable pageable) {
        return xActivityLogDao.findActivityData(xapplicationName, activityLogRequestDTO.getUser(),
                activityLogRequestDTO.getSenderName(), activityLogRequestDTO.getCategory(),
                activityLogRequestDTO.getStartDate(), activityLogRequestDTO.getEndDate(), pageable);
    }

    @Override
    public ActivityLogRequestDTO convertingXUserToActivityLogRequestDTO(XUserParent userParent, String category) {
        ActivityLogRequestDTO activityLogRequestDTO = new ActivityLogRequestDTO();
        if (category.equals(Category.LOGIN.name())) {
            activityLogRequestDTO.setCategory(Category.LOGIN.name());
        } else if (category.equals("logout")) {
            activityLogRequestDTO.setCategory(Category.LOGOUT.name());
        } else if (category.equals("salutation")) {
            activityLogRequestDTO.setCategory(Category.SALUTATION_CHANGED.name());
        }
        activityLogRequestDTO.setUser((XUser) userParent);
        return activityLogRequestDTO;
    }

    @Override
    public ActivityLogRequestDTO convertingXUserToActivityLogRequestDTO(XUserParent userParent) {
        ActivityLogRequestDTO activityLogRequestDTO = new ActivityLogRequestDTO();
        activityLogRequestDTO.setUser((XUser) userParent);
        return activityLogRequestDTO;
    }

    private String createSenderName(XUser user) {
        if (user.getxActor() != null) {
            if (Strings.isNullOrEmpty(user.getSenderName())) {
                return "PlayUser";
            } else {
                if (Strings.isNullOrEmpty(user.getSalutation())) {
                    log.info("Users sender name is: {}", user.getSenderName());
                    return user.getSenderName();
                } else {
                    log.info("Users sender name is: {}", user.getSalutation() + " " + user.getSenderName());
                    return user.getSalutation() + " " + user.getSenderName();
                }
            }
        } else {
            return "PlayUser";
        }
    }

    public String createStats() {
        List<XUser> userList = xUserDao.findAll();
        StringBuilder stats = new StringBuilder();
        for (XUser user : userList) {
            stats.append("User: " + user.getEmail() + ": Login: " + xActivityLogDao.findAllLogs(user.getEmail(), "login").size() +
                    " Logout: " + xActivityLogDao.findAllLogs(user.getEmail(), "logout").size() + "\n");
        }
        return String.valueOf(stats);
    }
}