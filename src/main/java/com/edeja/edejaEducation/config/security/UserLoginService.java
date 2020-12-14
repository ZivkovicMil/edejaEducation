package com.edeja.edejaEducation.config.security;

import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.EmailValidationException;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.Salutation;
import com.edeja.edejaEducation.models.UserCredentials;

/**
 * Facade to expose login functionality.
 */
public interface UserLoginService {

    XUserParent login(UserCredentials credentials) throws UserAuthenticationException, EmailValidationException;

    void logout(XUserParent xUserParent);

    void updateSalutation(XUser xUser, Salutation salutation);

    XUser findByUserId(int id);

    void savePersonChanges(XUserParent personDto) throws UserAuthenticationException;

}
