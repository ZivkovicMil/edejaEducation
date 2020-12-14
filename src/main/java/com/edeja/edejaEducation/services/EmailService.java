package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XUser;

public interface EmailService {
    void sendMimeMessage(String to, String subject, XUser user);
    void sendMimeMessageStatistics(String to, String subject,String stats);
}
