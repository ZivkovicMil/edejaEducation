package com.edeja.edejaEducation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements UserDetails {

  private static final long serialVersionUID = -7398018438669591353L;
  private final String token;
  private JwtUserDto jwtUser;

  public AuthenticatedUser(JwtUserDto jwtUser, String token) {
    this.jwtUser = jwtUser;
    this.token = token;
  }

  @Override
  public String getUsername() {
    return jwtUser.getEmail();
  }

  @JsonIgnore
  public JwtUserDto getJwtUser() {
    return jwtUser;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

  public String getToken() {
    return token;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    for (String right : jwtUser.getRights()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(right));
    }

    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this) //
        .append("token", this.getToken()) //
        .append("jwtUser", this.getJwtUser()) //
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder() //
        .append(this.getToken()) //
        .append(this.getJwtUser()) //
        .hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AuthenticatedUser)) {
      return false;
    }
    AuthenticatedUser other = (AuthenticatedUser) obj;

    return new EqualsBuilder() //
        .append(this.getToken(), other.getToken()) //
        .append(this.getJwtUser(), other.getJwtUser()) //
        .isEquals();
  }
}
