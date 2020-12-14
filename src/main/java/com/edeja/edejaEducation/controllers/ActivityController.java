package com.edeja.edejaEducation.controllers;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.services.UserActivityLogService;
import com.edeja.edejaEducation.types.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/activities")
@Api(value = "activities")
@CrossOrigin
public class ActivityController extends MainController {

    @Autowired
    private UserActivityLogService userActivityLogService;
    private JavaMailSender javaMailSender;

    @ApiOperation(value = "Return List")
    @RequestMapping(value = "/returnList", method = RequestMethod.GET)
    public ResponseEntity<?> returnList(HttpServletRequest request, @PageableDefault(page = 0, size = 10)
            Pageable pageable)
            throws UserAuthenticationException {
        XUserParent userParent = getUserFromJwtToken(request);
        Page<XActivityLog> page = userActivityLogService.allXActivities(userActivityLogService.convertingXUserToActivityLogRequestDTO(userParent), pageable);
        return new ResponseEntity<Page<XActivityLog>>(page, HttpStatus.OK);
    }

    @ApiOperation(value = "Return List by category")
    @RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> returnList(@PathVariable String category, HttpServletRequest request, @PageableDefault(page = 0, size = 10)
            Pageable pageable)
            throws UserAuthenticationException {
        XUserParent userParent = getUserFromJwtToken(request);
        Page<XActivityLog> page = userActivityLogService.allXActivities(userActivityLogService.convertingXUserToActivityLogRequestDTO(userParent,category), pageable);
        return new ResponseEntity<Page<XActivityLog>>(page, HttpStatus.OK);
    }
}
