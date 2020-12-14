package com.edeja.edejaEducation.types;

public enum RoleName {
  ROLE_USER("USER"), //
  ROLE_ADMIN("ADMIN");

  private String roleName;

  RoleName(String roleName) {
    this.roleName = roleName;
  }

  public String roleName() {
    return roleName;
  }
}
