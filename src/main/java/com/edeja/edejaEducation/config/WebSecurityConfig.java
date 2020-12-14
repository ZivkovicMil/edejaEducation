package com.edeja.edejaEducation.config;

import com.edeja.edejaEducation.config.security.jwt.JwtAuthenticationFilter;
import com.edeja.edejaEducation.config.security.jwt.JwtAuthenticationProvider;
import com.edeja.edejaEducation.config.security.jwt.JwtTokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Autowired
  private JwtTokenValidator jwtTokenValidator;

  @Autowired
  private Environment environment;

  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Bean
  public JwtAuthenticationProvider createJwtAuthenticationProvider() {
    return new JwtAuthenticationProvider();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenValidator);
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
    String profileName = this.environment.getProperty("spring.profiles.active");
    if (profileName == null) {
      profileName = "default";
    }
    switch (profileName) {
      case "integration":
        http.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll();
        break;
      default:
        http.cors().and().authorizeRequests()
            .antMatchers("/swagger-ui.html/**",
                "/swagger-resources/**",
                "/users/v1/login",
                "/deliveryDetail/v1/download/*",
                "/general/v1/pingMe"
            ).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
            .and().httpBasic()
            .and().csrf().disable().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .requestMatchers().antMatchers(
            "/users/v1/logout",
            "users/v1/refreshToken",
            "users/v1/changeLanguage",
            "/general/v1/tenants",
            "/general/v1/countryCodes",
            "/statistics/v1/**",
            "/deliveryDetail/v1/ ",
            "/deliveryDetail/v1/getDocuments/*",
            "/activityLog/v1/**",
            "/gridPreferences/v1/**",
            "/search/v1/**"
        );
        break;
    }
  }
}
