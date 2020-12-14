package com.edeja.edejaEducation.controllers;

import com.edeja.edejaEducation.Messages;
import com.edeja.edejaEducation.config.security.PersonResolver;
import com.edeja.edejaEducation.config.security.jwt.JwtTokenValidator;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.EmailValidationException;
import com.edeja.edejaEducation.exception.UserAccessRightsToContentException;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.exception.UserRoleValidationException;
import com.edeja.edejaEducation.helpers.JwtTokenHelper;
import com.edeja.edejaEducation.models.ErrorDTO;
import com.edeja.edejaEducation.models.JwtUserDto;
import com.edeja.edejaEducation.types.RoleName;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(value = "Main controller")
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private PersonResolver personResolver;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    /**
     * @param httpServletRequest
     * @return
     * @throws UserAuthenticationException
     */
    public XUserParent getUserFromJwtToken(HttpServletRequest httpServletRequest) throws UserAuthenticationException {
        String refreshToken = jwtTokenHelper.extractJwtFromHeader(httpServletRequest);
        JwtUserDto jwtUserDto = jwtTokenValidator.parseToken(refreshToken);
        return personResolver.resolveLoggedInPerson(jwtUserDto);
    }

    /**
     * @param httpServletRequest
     * @return
     * @throws UserAuthenticationException
     */
    public JwtUserDto getInformationAboutUserFromJwtToken(HttpServletRequest httpServletRequest) throws UserAuthenticationException {
        String refreshToken = jwtTokenHelper.extractJwtFromHeader(httpServletRequest);
        JwtUserDto jwtUserDto = jwtTokenValidator.parseToken(refreshToken);
        return jwtUserDto;
    }

    public void accessRightsOfTenantToContent(HttpServletRequest httpServletRequest, List<String> tenantid)
            throws UserAuthenticationException, UserRoleValidationException, UserAccessRightsToContentException {

        JwtUserDto jwtUserDto = getInformationAboutUserFromJwtToken(httpServletRequest);
        List<String> userRoles = jwtUserDto.getRights();

        if (userRoles == null || userRoles.isEmpty()) {
            throw new UserRoleValidationException(Messages.USER_ROLE_IS_NOT_VALID);
        }

        boolean adminUserCheck = userRoles.contains(RoleName.ROLE_ADMIN.roleName());
        boolean ordinaryUserCheck = userRoles.contains(RoleName.ROLE_USER.roleName());

        if (!adminUserCheck && !ordinaryUserCheck) {
            throw new UserRoleValidationException(Messages.USER_ROLE_IS_NOT_VALID);
        }

        if (adminUserCheck) {
            return;
        }

        if (ordinaryUserCheck) {
            if (tenantid == null || tenantid.isEmpty() || tenantid.size() > 1 || !tenantid.contains(jwtUserDto.getTenantId())) {
                throw new UserAccessRightsToContentException(Messages.USER_ACCESS_RIGHTS_TO_CONTENT);
            }
        }

    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ErrorDTO> handler(UserAuthenticationException exception) {
        String errorCode = exception.getMessages().getIdentification();
        String errorMessage = exception.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserRoleValidationException.class)
    public ResponseEntity<ErrorDTO> handlerValidationOfUserRole(UserRoleValidationException exception) {
        String errorCode = String.valueOf(exception.getMessages().getIdentification());
        String errorMessage = exception.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAccessRightsToContentException.class)
    public ResponseEntity<ErrorDTO> handlerAccessRights(UserAccessRightsToContentException exception) {
        String errorCode = String.valueOf(exception.getMessages().getIdentification());
        String errorMessage = exception.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<ErrorDTO> handlerEmailValidation(EmailValidationException exception) {
        String errorCode = String.valueOf(exception.getMessages().getIdentification());
        String errorMessage = exception.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.NOT_FOUND);
    }
}
