package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.EmailValidationException;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.UserCredentials;

public interface UserEmailValidationService {

    void emailValidation(String email) throws EmailValidationException;

}
