package com.edeja.edejaEducation.config.security.jwt;

import com.edeja.edejaEducation.config.security.jwt.key.KeyLoader;
import com.edeja.edejaEducation.config.security.jwt.key.exception.UnableToLoadKeyException;
import com.edeja.edejaEducation.entity.adminEntity.UserRole;
import com.edeja.edejaEducation.entity.adminEntity.XActor;
import com.edeja.edejaEducation.entity.adminEntity.XUserParent;
import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.JwtUserDto;
import com.edeja.edejaEducation.types.JwtTokenFields;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtTokenGenerator {

  private static final Logger log = LoggerFactory.getLogger(JwtTokenGenerator.class);

  @Value("${jwt.expirationTimeAuthenticationTokenInMinutes}")
  private int expirationTimeAuthenticationTokenInMinutes;

  @Value("${jwt.payloadEncryptionPassword}")
  private String payloadEncryptionPassword;

  @Value("${jwt.payloadEncryptionSalt}")
  private String payloadEncryptionSalt;

  @Value("${jwt.secret}")
  private String secret;

  @Autowired
  private KeyLoader keyLoader;

  private JwtUserDto personDtoToJwtUser(XUserParent xUserParent) {

    JwtUserDto jwtUserDto = new JwtUserDto();

    jwtUserDto.setUserId(xUserParent.getxId().toString());
    jwtUserDto.setEmail(xUserParent.getEmail());
    if (!Strings.isNullOrEmpty(xUserParent.getSenderName())) {
      jwtUserDto.setDisplayName(xUserParent.getSenderName());
    } else {
      jwtUserDto.setDisplayName(xUserParent.getEmail());
    }
    jwtUserDto.setUserLanguage(xUserParent.getUserLanguage());
    jwtUserDto.setCanDownloadDocument(xUserParent.getCanDownloadDocument());
    jwtUserDto.setTenantId(xUserParent.getTenantName());
    jwtUserDto.setShowPrintedData(xUserParent.showPrintedData());
    List<String> roleList = new ArrayList<>();
    List<UserRole> userRole = xUserParent.getUserRoles();
    if (userRole != null){
      for(UserRole role : userRole) {
        roleList.add(role.getUserRole());
      }
    }
    jwtUserDto.setRights(roleList);

    return jwtUserDto;
  }

  public String generateToken(XUserParent xUserParent) throws UserAuthenticationException, UnableToLoadKeyException {
    return generateToken(personDtoToJwtUser(xUserParent));
  }

  public String generateToken(JwtUserDto u, int time) throws UserAuthenticationException, UnableToLoadKeyException {

    Claims claims = Jwts.claims().setSubject(u.getDisplayName());
    claims.put(JwtTokenFields.USER_ID.fieldName(), u.getUserId());
    claims.put(JwtTokenFields.EMAIL.fieldName(), u.getEmail());
    claims.put(JwtTokenFields.DISPLAYNAME.fieldName(), u.getDisplayName());
    claims.put(JwtTokenFields.USERLANGUAGE.fieldName(), u.getUserLanguage());
    claims.put(JwtTokenFields.CANDOWNLOADDOCUMENT.fieldName(), u.getCanDownloadDocument());
    claims.put(JwtTokenFields.SHOWPRINTEDDATA.fieldName(), u.getShowPrintedData());
    claims.put(JwtTokenFields.RIGHTS.fieldName(), u.getRights());
    claims.put(JwtTokenFields.TENANTID.fieldName(), u.getTenantId());

    DateTime now = DateTime.now();
    DateTime expiration = now.plusMinutes(time);

    try {
      return Jwts.builder() //
          .setClaims(claims) //
          .setIssuedAt(now.toDate()) //
          .setExpiration(expiration.toDate()) //
          //TODO change security key in some moment
          //        .signWith(SignatureAlgorithm.RS512, keyLoader.loadJwtKey()) //
          .signWith(
              SignatureAlgorithm.HS256,
              secret.getBytes("UTF-8")
          )
          .compact();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String generateToken(JwtUserDto u) throws UserAuthenticationException, UnableToLoadKeyException {
    return generateToken(u, expirationTimeAuthenticationTokenInMinutes);
  }

}
