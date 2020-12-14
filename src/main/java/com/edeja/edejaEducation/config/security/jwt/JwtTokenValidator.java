package com.edeja.edejaEducation.config.security.jwt;

import com.edeja.edejaEducation.Messages;
import com.edeja.edejaEducation.config.security.jwt.key.KeyLoader;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.JwtUserDto;
import com.edeja.edejaEducation.types.JwtTokenFields;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class JwtTokenValidator extends DefaultHandlerExceptionResolver {

  private static final Logger log = LoggerFactory.getLogger(JwtTokenValidator.class);

  @Value("${jwt.payloadEncryptionPassword}")
  private String payloadEncryptionPassword;

  @Value("${jwt.payloadEncryptionSalt}")
  private String payloadEncryptionSalt;

  @Value("${jwt.secret}")
  private String secret;

  @Autowired
  private KeyLoader keyLoader;


  public JwtUserDto parseToken(String token) throws UserAuthenticationException {

    if (token == null) {
      throw new UserAuthenticationException(Messages.INVALID_OR_MISSING_TOKEN);
    }

    Jws<Claims> claimsJws = null;
    try {
      try {
        claimsJws = Jwts.parser()
            //          .setSigningKey(keyLoader.loadJwtKey()) //
            .setSigningKey(secret.getBytes("UTF-8"))
            .parseClaimsJws(token);
      } catch (UnsupportedEncodingException e) {
        log.error("Unsupported encoding found!", e);
      } catch (SignatureException e) {
        log.error("Bad JWT token: {}", token);
        throw new UserAuthenticationException(Messages.INVALID_OR_MISSING_TOKEN);
      } catch (ExpiredJwtException e) {
        log.info("Token is expired: {}", token);
        throw new UserAuthenticationException(Messages.INVALID_OR_MISSING_TOKEN);
      }

      Claims body = claimsJws.getBody();

      JwtUserDto jwtUser = new JwtUserDto();
      jwtUser.setUserId(stringFromToken(body, JwtTokenFields.USER_ID));
      jwtUser.setEmail(stringFromToken(body, JwtTokenFields.EMAIL));
      jwtUser.setDisplayName(stringFromToken(body, JwtTokenFields.DISPLAYNAME));
      jwtUser.setRights(stringListFromToken(body, JwtTokenFields.RIGHTS));
      jwtUser.setUserLanguage(stringFromToken(body, JwtTokenFields.USERLANGUAGE));
      jwtUser.setShowPrintedData(body.get(JwtTokenFields.SHOWPRINTEDDATA.fieldName(), Boolean.class));
      jwtUser.setCanDownloadDocument(body.get(JwtTokenFields.CANDOWNLOADDOCUMENT.fieldName(), Boolean.class));
      jwtUser.setTenantId(stringFromToken(body, JwtTokenFields.TENANTID));
      Date issuedAt = body.getIssuedAt();
      jwtUser.setTokenIssuedAt(toDateTimeOrNull(issuedAt));
      Date expiration = body.getExpiration();
      jwtUser.setTokenExpiresAt(toDateTimeOrNull(expiration));
      return jwtUser;
    } catch (JwtException e) {
      log.error("Unable to parse JWT token: {}", token);
      throw new UserAuthenticationException(Messages.INVALID_OR_MISSING_TOKEN);
    }

  }

  private DateTime toDateTimeOrNull(Date date) {
    return date == null ? null : new DateTime(date);
  }

  private List<String> stringListFromToken(Claims body, JwtTokenFields field) {
    List<String> fieldValue = body.get(field.fieldName(), List.class);

    if (fieldValue == null) {
      fieldValue = new ArrayList<>();
    }

    return fieldValue;
  }

  private String stringFromToken(Claims body, JwtTokenFields field) {
    String fieldValue = body.get(field.fieldName(), String.class);

    if (fieldValue == null) {
      log.warn("Field [{}] is NULL, setting it to empty string.", field.name());
    }

    return StringUtils.defaultString(fieldValue);
  }

}
