package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EvalServiceImpl implements EvalService{

    @Autowired
    TemplateEngine templateEngine;

    public String makeBody(XActivityLog xActivityLog) {
        Context context = new Context();
        context.setVariable("logId", xActivityLog.getId());
        context.setVariable("logDate", xActivityLog.getLogDate());
        context.setVariable("logCategory", xActivityLog.getCategory());
        context.setVariable("logDetails", xActivityLog.getDetails());
        context.setVariable("logSenderName", xActivityLog.getSendername());
        context.setVariable("logUserName", xActivityLog.getUsername());
        context.setVariable("logApplicationName", xActivityLog.getApplicationName());
        String result = this.templateEngine.process("customTemplate", context);
        return result;
    }

    public String makeBodyForNoSalutation(){
        Context context = new Context();
        String result = this.templateEngine.process("noSalutationTemplate", context);
        return result;
    }
}
