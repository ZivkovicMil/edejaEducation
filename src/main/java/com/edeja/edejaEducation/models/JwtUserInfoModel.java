package com.edeja.edejaEducation.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class JwtUserInfoModel implements Serializable {

  private static final long serialVersionUID = -3539652100855496052L;

  private String userId;

  private String email;

  private String displayName;

  private Date tokenExpiresAt;

  private Date tokenIssuedAt;

  private List<String> rights;

  private String userLanguage;

  private boolean canDownloadDocument;

  private boolean showPrintedData;

  private String tenantId;

  public JwtUserInfoModel() {
  }

  public JwtUserInfoModel(JwtUserDto jwtUserDto) {
    this.userId = jwtUserDto.getUserId();
    this.email = jwtUserDto.getEmail();
    this.displayName = jwtUserDto.getDisplayName();
    this.tokenExpiresAt = jwtUserDto.getTokenExpiresAt().toDate();
    this.tokenIssuedAt = jwtUserDto.getTokenIssuedAt().toDate();
    this.rights = jwtUserDto.getRights();
    this.userLanguage = jwtUserDto.getUserLanguage();
    this.canDownloadDocument = jwtUserDto.getCanDownloadDocument();
    this.showPrintedData = jwtUserDto.getShowPrintedData();
    this.tenantId = jwtUserDto.getTenantId();
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Date getTokenExpiresAt() {
    return tokenExpiresAt;
  }

  public void setTokenExpiresAt(Date tokenExpiresAt) {
    this.tokenExpiresAt = tokenExpiresAt;
  }

  public Date getTokenIssuedAt() {
    return tokenIssuedAt;
  }

  public void setTokenIssuedAt(Date tokenIssuedAt) {
    this.tokenIssuedAt = tokenIssuedAt;
  }

  public List<String> getRights() {
    return rights;
  }

  public void setRights(List<String> rights) {
    this.rights = rights;
  }

  public String getUserLanguage() {
    return userLanguage;
  }

  public void setUserLanguage(String userLanguage) {
    this.userLanguage = userLanguage;
  }

  public boolean isCanDownloadDocument() {
    return canDownloadDocument;
  }

  public void setCanDownloadDocument(boolean canDownloadDocument) {
    this.canDownloadDocument = canDownloadDocument;
  }

  public boolean isShowPrintedData() {
    return showPrintedData;
  }

  public void setShowPrintedData(boolean showPrintedData) {
    this.showPrintedData = showPrintedData;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

}
