package com.edeja.edejaEducation.types;

public enum JwtTokenFields {
  USER_ID("userId"), //
  EMAIL("email"), //
  DISPLAYNAME("displayName"), //
  RIGHTS("rights"), //
  SHOWPRINTEDDATA("showPrintedData"), //
  USERLANGUAGE("userLanguage"), //
  CANDOWNLOADDOCUMENT("canDownloadDocument"), //
  TENANTID("tenantId"), //
  ADDITIONAL_FIELDS("additionalFields");

  private String fieldName;

  JwtTokenFields(String fieldName) {
    this.fieldName = fieldName;
  }

  public String fieldName() {
    return fieldName;
  }
}
