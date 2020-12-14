package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XUser;

public interface SendStatisticsService {

    void check();

    void resetUser(XUser xUser);
}
