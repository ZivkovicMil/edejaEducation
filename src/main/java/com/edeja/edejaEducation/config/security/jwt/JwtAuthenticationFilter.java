package com.edeja.edejaEducation.config.security.jwt;

import com.edeja.edejaEducation.exception.UserAuthenticationException;
import com.edeja.edejaEducation.helpers.JwtTokenHelper;
import com.edeja.edejaEducation.models.JwtAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@PropertySource("classpath:application.properties")
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private JwtTokenValidator jwtTokenValidator;

  @Autowired
  private JwtTokenGenerator tokenGenerator;

  public JwtAuthenticationFilter(JwtTokenValidator jwtTokenValidator) {
    super("/**");
    this.jwtTokenValidator = jwtTokenValidator;
  }

  @Override
  protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
    if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
      return false;
    }
    return true;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    JwtTokenHelper jwtTokenHelper = new JwtTokenHelper();
    String authToken = jwtTokenHelper.extractJwtFromHeader(request);

    try {
      this.jwtTokenValidator.parseToken(authToken);
    } catch (UserAuthenticationException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      return null;
    }

    JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
    Authentication authenticationResult = getAuthenticationManager().authenticate(authRequest);
    return authenticationResult;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
        "X-Auth-Token-Update, Refresh-Token, Origin, Accept, X-Requested-With, Content-Type, Authorization, Expires, Pragma");
    response.addHeader("Access-Control-Expose-Headers", "X-Auth-Token-Update, Refresh-Token");
    response.addHeader("Cache-Control","private, no-cache, no-store, must-revalidate");
    response.addHeader("Expires", "-1");
    response.addHeader("Pragma", "no-cache");
    response.addIntHeader("Access-Control-Max-Age", 3600);

    if (request.getMethod().equals("OPTIONS")) {
      // we don't want to secure OPTIONS calls
      chain.doFilter(request, response);
      return;
    }

    //Attempt to authenticate
    Authentication authResult;
    authResult = attemptAuthentication(request, response);

    SecurityContextHolder.getContext().setAuthentication(authResult);
    chain.doFilter(request, response);
  }

}
