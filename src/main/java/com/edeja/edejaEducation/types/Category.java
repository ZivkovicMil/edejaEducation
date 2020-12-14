package com.edeja.edejaEducation.types;

public enum Category {
  LOGIN("User successfully logged in"),
  LOGOUT("User successfully logged out"),
  SALUTATION_CHANGED("Salutation changed"),
  ALL("Showing all results");
  private String categoryDescription;

  Category(String categoryDescription) {
    this.categoryDescription = categoryDescription;
  }

  public String getCategoryDescription() {
    return categoryDescription;
  }
}
