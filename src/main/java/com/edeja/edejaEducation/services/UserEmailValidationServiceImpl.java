package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.Messages;
import com.edeja.edejaEducation.exception.EmailValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserEmailValidationServiceImpl implements UserEmailValidationService {

    private static final Logger log = LoggerFactory.getLogger(UserEmailValidationServiceImpl.class);

    @Override
    public void emailValidation(String mail) throws EmailValidationException {
        try {
            if (!checkMail(mail)) {
                log.info("Entered Email is not valid!", mail);
                throw new EmailValidationException(Messages.INVALID_EMAIL);
            }
        } catch (EmailValidationException e) {
            throw new EmailValidationException(Messages.INVALID_EMAIL);
        }
    }

    private boolean checkMail(String eMail) {
        int charAtDot = 0;
        if (eMail.contains("@") == false || eMail.contains(".") == false) {
            return false;
        } else {
            for (int i = eMail.length() - 1; i >= 0; i--) {
                if (eMail.charAt(i) == '.') {
                    charAtDot = eMail.length() - 1 - i;
                    if (charAtDot == 2 || charAtDot == 3) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
