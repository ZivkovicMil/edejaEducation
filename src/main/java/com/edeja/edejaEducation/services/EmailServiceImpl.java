package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;
import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.models.UserPlay;
import com.edeja.edejaEducation.repositories.adminDao.XActivityLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EvalService evalService;

    @Autowired
    private XActivityLogDao xActivityLogDao;

    @Override
    public void sendMimeMessage(String to, String subject, XUser user) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            if (lastSalutation(user) == null) {
                helper.setText(evalService.makeBodyForNoSalutation(), true);
            } else {
                helper.setText(evalService.makeBody(lastSalutation(user)), true);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

    public void sendMimeMessageStatistics(String to, String subject, String stats) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(stats);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }


    private XActivityLog lastSalutation(XUser user) {
        List<XActivityLog> list = (List<XActivityLog>) xActivityLogDao.findAllSalutations(user.getEmail(), new Sort(Sort.Direction.DESC, "logDate"));
        if (list != null & !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}

