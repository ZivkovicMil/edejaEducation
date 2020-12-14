package com.edeja.edejaEducation.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private static final long serialVersionUID = -4644145765959942789L;
  private String token;

  public JwtAuthenticationToken(String token) {
    super(null, null);
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this) //
        .append("token", this.getToken()) //
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder() //
        .append(this.getToken()) //
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
    if (!(obj instanceof JwtAuthenticationToken)) {
      return false;
    }
    JwtAuthenticationToken other = (JwtAuthenticationToken) obj;

    return new EqualsBuilder() //
        .append(this.getToken(), other.getToken()) //
        .isEquals();
  }
}
