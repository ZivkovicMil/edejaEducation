package com.edeja.edejaEducation.config.security;

import com.edeja.edejaEducation.Messages;
import com.edeja.edejaEducation.entity.adminEntity.UserRole;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.AuthenticatedUser;
import com.edeja.edejaEducation.models.JwtUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PersonResolver {

    private static final Logger log = LoggerFactory.getLogger(PersonResolver.class);

    @Autowired
    private UserLoginService userLoginService;

    public XUserParent resolveLoggedInPersonDto(UsernamePasswordAuthenticationToken principal)
            throws UserAuthenticationException {
        checkPrincipal(principal);

        AuthenticatedUser user = (AuthenticatedUser) principal.getPrincipal();
        JwtUserDto jwtUser = user.getJwtUser();
        return resolveLoggedInPerson(jwtUser);
    }

    private void checkPrincipal(UsernamePasswordAuthenticationToken principal) throws UserAuthenticationException {
        if (null == principal) {
            log.warn("tried to resolve person from null principal");
            throw new UserAuthenticationException(Messages.ERROR_GENERAL);
        }

        if (!(principal.getPrincipal() instanceof AuthenticatedUser)) {
            log.warn("tried to resolve person from principal of invalid type: {} instead of AuthenticatedUser",
                    principal.getPrincipal().getClass().getSimpleName());
            throw new UserAuthenticationException(Messages.ERROR_GENERAL);
        }
    }

    public XUserParent resolveLoggedInPerson(JwtUserDto jwtUser) {
        XUserParent xUserParent;

        xUserParent = userLoginService.findByUserId(Integer.parseInt(jwtUser.getUserId()));
        List<UserRole> userRoles = jwtUser.getRights().stream().map(role -> {
            UserRole userRole = new UserRole();
            userRole.setUserRole(role);
            return userRole;
        }).collect(Collectors.toList());
        xUserParent.setUserRoles(userRoles);

        return xUserParent;
    }

}
