package com.edeja.edejaEducation.helpers;

import com.edeja.edejaEducation.Messages;
import com.edeja.edejaEducation.config.security.jwt.JwtTokenGenerator;
import com.edeja.edejaEducation.config.security.jwt.JwtTokenValidator;
import com.edeja.edejaEducation.config.security.jwt.key.exception.UnableToLoadKeyException;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.JwtUserDto;
import com.edeja.edejaEducation.models.JwtUserInfoModel;
import com.edeja.edejaEducation.types.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenHelper implements Serializable {

  private static final long serialVersionUID = -3301605591108950415L;

  private static final Logger log = LoggerFactory.getLogger(JwtTokenHelper.class);

  @Autowired
  JwtTokenValidator jwtTokenValidator;

  @Autowired
  JwtTokenGenerator jwtTokenGenerator;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expirationTimeRefreshTokenInDays}")
  private int expirationTimeRefreshTokenInDays;

  public JwtTokenHelper() {
  }

  /**
   * @param token
   * @return
   */
  public String refreshToken(String token) throws UserAuthenticationException {
    String newToken = null;
    try {
      JwtUserDto jwtUserDto = jwtTokenValidator.parseToken(token);
      newToken = jwtTokenGenerator.generateToken(jwtUserDto, calculateExpirationDate(expirationTimeRefreshTokenInDays));
    } catch (UserAuthenticationException e) {
      throw new UserAuthenticationException(Messages.INVALID_OR_MISSING_TOKEN);
    } catch (UnableToLoadKeyException e) {
      log.error("Unable to load secret key");
    }
    return newToken;
  }

  /**
   * @param request
   * @return
   */
  public String extractJwtFromHeader(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION.headerName());
    if (header != null && header.startsWith(HttpHeaders.AUTHORIZATION_PREFIX_BEARER.headerName())) {
      String authToken = header.substring(HttpHeaders.AUTHORIZATION_PREFIX_BEARER.headerName().length());
      return authToken;
    }
    return null;
  }

  /**
   * @param xUserParent
   * @return
   * @throws UserAuthenticationException
   * @throws UnableToLoadKeyException
   */
  public JwtUserInfoModel generateJwtAndPlaceItInHeader(XUserParent xUserParent, HttpServletResponse response)
      throws UserAuthenticationException, UnableToLoadKeyException {

    String authenticationToken = jwtTokenGenerator.generateToken(xUserParent);
    String refreshToken = refreshToken(authenticationToken);
    JwtUserInfoModel jwtUserInfoModel = new JwtUserInfoModel(jwtTokenValidator.parseToken(authenticationToken));

    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
        "X-Auth-Token-Update, Refresh-Token, Origin, Accept, X-Requested-With, Content-Type, Authorization, Expires, Pragma");
    response.addHeader("Access-Control-Expose-Headers", "X-Auth-Token-Update, Refresh-Token");
    response.addHeader("Cache-Control","private, no-cache, no-store, must-revalidate");
    response.addHeader("Expires", "-1");
    response.addHeader("Pragma", "no-cache");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addIntHeader("Access-Control-Max-Age", 3600);

    response.setHeader(HttpHeaders.X_AUTH_TOKEN_UPDATE.headerName(), authenticationToken);
    response.setHeader(HttpHeaders.REFRESH_TOKEN.headerName(), refreshToken);
    response.setStatus(HttpStatus.OK.value());
    return jwtUserInfoModel;
  }

  private int calculateExpirationDate(int expirationDate) {
    return expirationDate * 24 * 60;
  }
}
