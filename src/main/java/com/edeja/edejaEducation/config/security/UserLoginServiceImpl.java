package com.edeja.edejaEducation.config.security;

import com.edeja.edejaEducation.Messages;
import com.edeja.edejaEducation.entity.adminEntity.UserRole;
import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.EmailValidationException;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.helpers.PasswordHelper;
import com.edeja.edejaEducation.models.Salutation;
import com.edeja.edejaEducation.models.UserCredentials;
import com.edeja.edejaEducation.repositories.adminDao.XActorDao;
import com.edeja.edejaEducation.repositories.adminDao.XAdminUserDao;
import com.edeja.edejaEducation.repositories.adminDao.XTenantDao;
import com.edeja.edejaEducation.repositories.adminDao.XUserDao;
import com.edeja.edejaEducation.services.UserActivityLogService;
import com.edeja.edejaEducation.services.UserEmailValidationService;
import com.edeja.edejaEducation.types.Category;
import com.edeja.edejaEducation.types.RoleName;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    private static final Logger log = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    private static final String LOGIN_TEXT = "Benutzer erfolgreich eingeloggt";

    private static final String LOGOUT_TEXT = "Benutzer erfolgreich ausgeloggt";

    private static final String SALUTATION_CHANGED_TEXT = "Salutation is changed";

    @Autowired
    private UserEmailValidationService userEmailValidationService;

    @Autowired
    private UserActivityLogService userActivityLogService;

    private XUserDao xUserDao;

    private XAdminUserDao xAdminUserDao;

    private XTenantDao xTenantDao;

    private XActorDao xActorDao;

    public UserLoginServiceImpl(XUserDao xUserDao, XAdminUserDao xAdminUserDao, XTenantDao xTenantDao, XActorDao xActorDao) {
        this.xUserDao = xUserDao;
        this.xAdminUserDao = xAdminUserDao;
        this.xTenantDao = xTenantDao;
        this.xActorDao = xActorDao;
    }

    /**
     * @param credentials
     * @return
     * @throws UserAuthenticationException
     */
    @Override
    public XUserParent login(final UserCredentials credentials) throws UserAuthenticationException, EmailValidationException {

        if (null == credentials || null == credentials.getUsername() || null == credentials.getPassword()) {
            throw new UserAuthenticationException(Messages.INVALID_USERNAME_OR_PASSWORD);
        }

        try {
            userEmailValidationService.emailValidation(credentials.getUsername());
            XUserParent xUserParent = loginUserOrAdmin(credentials.getUsername(), credentials.getPassword());
            if (!xUserParent.isUserActive()) {
                log.info("Person with username: {} is inactive!", credentials.getUsername());
                throw new UserAuthenticationException(Messages.PERSON_IS_INACTIVE);
            }
            if (xUserParent != null) {
                log.info("Person with username: {} logged in.", credentials.getUsername());
                userActivityLogService.createActivityLog((XUser) xUserParent, Category.LOGIN, LOGIN_TEXT);
                log.info("ActivityLog is written!");
            }
            return xUserParent;
        } catch (UserAuthenticationException ex) {
            throw new UserAuthenticationException(ex.getMessages());
        } catch (EmailValidationException e) {
            throw new EmailValidationException(e.getMessages());
        }
    }

    @Override
    public void logout(XUserParent xUserParent) {
        userActivityLogService.createActivityLog((XUser) xUserParent, Category.LOGOUT, LOGOUT_TEXT);
        log.info("User {} is loged out!", xUserParent.getEmail());
    }

    @Override
    public void updateSalutation(XUser xUser,Salutation salutation) {
        xUser.setSalutation(salutation.getSalutation());
        xUserDao.save(xUser);
        log.info("User {} is changed salutation!", xUser.getEmail());
        userActivityLogService.createActivityLog((XUser) xUser, Category.SALUTATION_CHANGED, SALUTATION_CHANGED_TEXT);
    }

    /**
     * @param userName
     * @param password
     * @return
     * @throws UserAuthenticationException
     */
    private XUserParent loginUserOrAdmin(String userName, String password) throws UserAuthenticationException {
        XUser xUser;
        xUser = xUserDao.findAccountByLogin(userName);
        if (xUser == null || !xUser.getPasswordHash().equals(PasswordHelper.calcPasswordHash(password))) {
            throw new UserAuthenticationException(Messages.PERSON_NOT_FOUND); //$NON-NLS-1$
        } else {
            UserRole userRole = new UserRole();
            userRole.setUserRole(RoleName.ROLE_USER.roleName());
            List<UserRole> roleList = xUser.getUserRoles();
            roleList.add(userRole);
        }
        return xUser;
    }

    /**
     * @param xUserParent
     */
    @Override
    public void savePersonChanges(XUserParent xUserParent) {
        log.info("User with email: {} changed language.", xUserParent.getEmail());
        XUser xUser = (XUser) xUserParent;
        this.xUserDao.save(xUser);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public XUser findByUserId(int id) {
        Optional<XUser> tmp = xUserDao.findById(id);
        try {
            return tmp.orElseThrow(() -> new UserAuthenticationException(Messages.PERSON_NOT_FOUND));
        } catch (UserAuthenticationException e) {
            log.info("Person with id: {} not found.", id);
        }
        return null;
    }
}
