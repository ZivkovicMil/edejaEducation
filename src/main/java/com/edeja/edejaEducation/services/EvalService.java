package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;

public interface EvalService {
    String makeBody(XActivityLog xActivityLog);

    String makeBodyForNoSalutation();
}
