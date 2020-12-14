package com.edeja.edejaEducation.types;

public enum HttpHeaders {
  AUTHORIZATION("Authorization"), //
  AUTHORIZATION_PREFIX_BEARER("Bearer "), //
  REFRESH_TOKEN("Refresh-Token"), //
  X_AUTH_TOKEN_UPDATE("X-Auth-Token-Update");

  private String headerName;

  HttpHeaders(String headerName) {
    this.headerName = headerName;
  }

  public String headerName() {
    return headerName;
  }
}
