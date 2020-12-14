package com.edeja.edejaEducation.controllers;

import com.edeja.edejaEducation.config.security.UserLoginService;
import com.edeja.edejaEducation.config.security.jwt.key.exception.UnableToLoadKeyException;
import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.EmailValidationException;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.helpers.JwtTokenHelper;
import com.edeja.edejaEducation.models.JwtUserInfoModel;
import com.edeja.edejaEducation.models.Salutation;
import com.edeja.edejaEducation.models.UserCredentials;
import com.edeja.edejaEducation.services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users/v1")
@Api(value = "users")
@CrossOrigin
public class  UsersController extends MainController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserActivityLogService userActivityLogService;

    @Autowired
    private UserLoginService loginService;

    @Autowired
    private UserEmailValidationService userEmailValidationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SendStatisticsService sendStatisticsService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * @param credentials
     * @param response
     * @return
     * @throws UserAuthenticationException
     * @throws UnableToLoadKeyException
     */
    @ApiOperation(value = "Login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody UserCredentials credentials, HttpServletResponse response)
            throws UserAuthenticationException, UnableToLoadKeyException, EmailValidationException {

        log.info("Received request to login to initialize a new session with username {}", credentials.getUsername());
        XUserParent person = loginService.login(credentials);
        JwtUserInfoModel jwtUserInfoModel = jwtTokenHelper.generateJwtAndPlaceItInHeader(person, response);
        log.info("Successfully answering login");
        return new ResponseEntity<>(jwtUserInfoModel, HttpStatus.OK);
    }

    /**
     * @param request
     * @return
     * @throws UnableToLoadKeyException
     * @throws UserAuthenticationException
     */
    @ApiOperation(value = "Refresh token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response)
            throws UnableToLoadKeyException, UserAuthenticationException {

        XUserParent xUserParent = getUserFromJwtToken(request);
        if (xUserParent instanceof XUser) {
            log.info("Received request to refreshAndGetAuthenticationToken for user with email {}", xUserParent.getEmail());
        }
        JwtUserInfoModel jwtUserInfoModel = jwtTokenHelper.generateJwtAndPlaceItInHeader(xUserParent, response);
        log.info("Successfully answering refreshToken");
        return new ResponseEntity<>(jwtUserInfoModel, HttpStatus.OK);
    }

    @ApiOperation(value = "Logout")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws UserAuthenticationException {
        XUserParent xUserParent = getUserFromJwtToken(request);
        loginService.logout(xUserParent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Update Salutation")
    @RequestMapping(value = "/updateSalutation", method = RequestMethod.POST)
    public ResponseEntity<?> updateSalutation(@Valid @RequestBody Salutation salutation, HttpServletRequest request) throws UserAuthenticationException {
        XUser xUser = (XUser) getUserFromJwtToken(request);
        loginService.updateSalutation(xUser, salutation);
        log.info("User: {} changed his salutation into: {}", xUser.getEmail(), salutation.getSalutation());
        return new ResponseEntity<XUser>(xUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Reset user")
    @RequestMapping(value = "/resetUser", method = RequestMethod.POST)
    public ResponseEntity<?> resetUser(HttpServletRequest request) throws UserAuthenticationException {
        XUser xUser = (XUser) getUserFromJwtToken(request);
        sendStatisticsService.resetUser(xUser);
        log.info("User {} has restarted his data", xUser.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Upload file")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) throws UserAuthenticationException {
        XUser xUser = (XUser) getUserFromJwtToken(request);
        uploadService.upload(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
