package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;
import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.models.ActivityLogRequestDTO;
import com.edeja.edejaEducation.models.UserPlay;
import com.edeja.edejaEducation.types.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserActivityLogService {

    void createActivityLog(XUser user, Category category, String details);

    Page<XActivityLog> allXActivities(ActivityLogRequestDTO activityLogRequestDTO, Pageable pageable);

    ActivityLogRequestDTO convertingXUserToActivityLogRequestDTO(XUserParent userParent, String category);

    ActivityLogRequestDTO convertingXUserToActivityLogRequestDTO(XUserParent userParent);

    String createStats();
}
