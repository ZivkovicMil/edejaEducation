package com.edeja.edejaEducation.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class UserCredentials implements Serializable {

  private static final long serialVersionUID = -7092584207458604884L;

  private String username;

  private String password;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this) //
        .append("username", this.getUsername()) //
        .append("password", this.getPassword()) //
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder() //
        .append(this.getUsername()) //
        .append(this.getPassword()) //
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
    if (!(obj instanceof UserCredentials)) {
      return false;
    }
    UserCredentials other = (UserCredentials) obj;

    return new EqualsBuilder() //
        .append(this.getUsername(), other.getUsername()) //
        .append(this.getPassword(), other.getPassword()) //
        .isEquals();
  }
}
