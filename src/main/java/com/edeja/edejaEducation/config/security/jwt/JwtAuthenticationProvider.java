package com.edeja.edejaEducation.config.security.jwt;

import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.models.AuthenticatedUser;
import com.edeja.edejaEducation.models.JwtAuthenticationToken;
import com.edeja.edejaEducation.models.JwtUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

  @Autowired
  private JwtTokenValidator jwtTokenValidator;

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
  }

  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {

    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    String token = jwtAuthenticationToken.getToken();
    JwtUserDto parsedUser = null;
    try {
      parsedUser = jwtTokenValidator.parseToken(token);
    } catch (UserAuthenticationException e) {
      log.info("Error retrieving user {}", token);
    }
    return new AuthenticatedUser(parsedUser, token);
  }

}
