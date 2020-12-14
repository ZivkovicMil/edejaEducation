package com.edeja.edejaEducation.controllers;

import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.services.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/email")
@Api(value = "Email")
@CrossOrigin
public class EmailController extends MainController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    private final String EMAIL_SUBJECT = "Log for users changed salutation";

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "Send email")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public ResponseEntity<?> sendEmail(HttpServletRequest request) throws UserAuthenticationException {
        XUser xUser = (XUser) getUserFromJwtToken(request);
        emailService.sendMimeMessage(xUser.getEmail(), EMAIL_SUBJECT, xUser);
        log.info("Email with last salutation is sent");
        return new ResponseEntity<>(xUser, HttpStatus.OK);
    }
}
