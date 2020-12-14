package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@MappedSuperclass
public abstract class XUserParent implements Serializable {

  @Id
  @Basic(optional = false)
  @Column(name = "x_id")
  protected Integer xId;

  @Column(name = "passwordHash")
  protected String passwordHash;

  @Column(name = "gridPreferences")
  protected Long gridPreferences;


  public abstract String getUserName();

  public abstract String getSenderName();

  public abstract boolean isUserActive();

  public abstract String getUserLanguage();

  public abstract boolean getCanDownloadDocument();

  public abstract String getTenantName();

  public abstract String getEmail();

  public abstract boolean showPrintedData();

  public abstract List<UserRole> getUserRoles();

  public abstract void setUserRoles(List<UserRole> userRoles);

  public abstract void setUserLanguage(String userLanguage);

  public abstract void setCanDownloadDocument(boolean canDownload);


  public Integer getxId() {
    return xId;
  }

  public void setxId(Integer xId) {
    this.xId = xId;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public Long getGridPreferences() {
    return this.gridPreferences;
  }

  public void setGridPreferences(Long gridPreferences) {
    this.gridPreferences = gridPreferences;
  }



}
