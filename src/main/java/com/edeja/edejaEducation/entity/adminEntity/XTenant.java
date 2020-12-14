package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "XTenant")
@XmlRootElement
public class XTenant implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "x_id")
  private Integer xId;
  @Basic(optional = false)
  @Column(name = "activateLdapAutoSync")
  private boolean activateLdapAutoSync;
  @Basic(optional = false)
  @Column(name = "activateProfileAutoSync")
  private boolean activateProfileAutoSync;
  @Column(name = "adminEmail")
  private String adminEmail;
  @Column(name = "configDir")
  private String configDir;
  @Column(name = "ldapCronExpression")
  private String ldapCronExpression;
  @Column(name = "maximumUserCount")
  private Integer maximumUserCount;
  @Column(name = "passwordHash")
  private String passwordHash;
  @Column(name = "profileCronExpression")
  private String profileCronExpression;
  @Column(name = "allowExtractedCredentials")
  private Boolean allowExtractedCredentials;
  @Column(name = "allowExtractedSelfRegister")
  private Boolean allowExtractedSelfRegister;
  @Column(name = "allowManualSelfRegister")
  private Boolean allowManualSelfRegister;
  @Column(name = "tenantDetail")
  private String tenantDetail;
  @Basic(optional = false)
  @Column(name = "tenantid", unique = true, nullable = false)
  private String tenantId;
  @Column(name = "configDomain")
  private String configDomain;
  @Column(name = "configPassword")
  private String configPassword;
  @Column(name = "configUserName")
  private String configUserName;
  @Basic(optional = false)
  @Column(name = "shouldCertificateBeCreated")
  private boolean shouldCertificateBeCreated;
  @Basic(optional = false)
  @Column(name = "printingFacility")
  private boolean printingFacility;
  @Column(name = "showPrintedData")
  private boolean showPrintedData;
  @OneToMany
  private List<XActor> xActor;
  @OneToMany
  private List<XAdminUser> xAdminUsers;

  public XTenant() {
  }

  public XTenant(Integer xId) {
    this.xId = xId;
  }

  public Integer getxId() {
    return xId;
  }

  public void setxId(Integer xId) {
    this.xId = xId;
  }


  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public boolean isActivateLdapAutoSync() {
    return activateLdapAutoSync;
  }

  public void setActivateLdapAutoSync(boolean activateLdapAutoSync) {
    this.activateLdapAutoSync = activateLdapAutoSync;
  }

  public boolean isActivateProfileAutoSync() {
    return activateProfileAutoSync;
  }

  public void setActivateProfileAutoSync(boolean activateProfileAutoSync) {
    this.activateProfileAutoSync = activateProfileAutoSync;
  }

  public String getAdminEmail() {
    return adminEmail;
  }

  public void setAdminEmail(String adminEmail) {
    this.adminEmail = adminEmail;
  }

  public String getConfigDir() {
    return configDir;
  }

  public void setConfigDir(String configDir) {
    this.configDir = configDir;
  }

  public String getLdapCronExpression() {
    return ldapCronExpression;
  }

  public void setLdapCronExpression(String ldapCronExpression) {
    this.ldapCronExpression = ldapCronExpression;
  }

  public Integer getMaximumUserCount() {
    return maximumUserCount;
  }

  public void setMaximumUserCount(Integer maximumUserCount) {
    this.maximumUserCount = maximumUserCount;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getProfileCronExpression() {
    return profileCronExpression;
  }

  public void setProfileCronExpression(String profileCronExpression) {
    this.profileCronExpression = profileCronExpression;
  }

  public Boolean getAllowExtractedCredentials() {
    return allowExtractedCredentials;
  }

  public void setAllowExtractedCredentials(Boolean allowExtractedCredentials) {
    this.allowExtractedCredentials = allowExtractedCredentials;
  }

  public Boolean getAllowExtractedSelfRegister() {
    return allowExtractedSelfRegister;
  }

  public void setAllowExtractedSelfRegister(Boolean allowExtractedSelfRegister) {
    this.allowExtractedSelfRegister = allowExtractedSelfRegister;
  }

  public Boolean getAllowManualSelfRegister() {
    return allowManualSelfRegister;
  }

  public void setAllowManualSelfRegister(Boolean allowManualSelfRegister) {
    this.allowManualSelfRegister = allowManualSelfRegister;
  }

  public String getTenantDetail() {
    return tenantDetail;
  }

  public void setTenantDetail(String tenantDetail) {
    this.tenantDetail = tenantDetail;
  }

  public String getConfigDomain() {
    return configDomain;
  }

  public void setConfigDomain(String configDomain) {
    this.configDomain = configDomain;
  }

  public String getConfigPassword() {
    return configPassword;
  }

  public void setConfigPassword(String configPassword) {
    this.configPassword = configPassword;
  }

  public String getConfigUserName() {
    return configUserName;
  }

  public void setConfigUserName(String configUserName) {
    this.configUserName = configUserName;
  }

  public boolean isShouldCertificateBeCreated() {
    return shouldCertificateBeCreated;
  }

  public void setShouldCertificateBeCreated(boolean shouldCertificateBeCreated) {
    this.shouldCertificateBeCreated = shouldCertificateBeCreated;
  }

  public boolean isPrintingFacility() {
    return printingFacility;
  }

  public void setPrintingFacility(boolean printingFacility) {
    this.printingFacility = printingFacility;
  }

  public boolean isShowPrintedData() {
    return showPrintedData;
  }

  public void setShowPrintedData(boolean showPrintedData) {
    this.showPrintedData = showPrintedData;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof XTenant)) {
      return false;
    }
    XTenant other = (XTenant) object;
    return (this.xId != null || other.xId == null) && (this.xId == null || this.xId.equals(other.xId));
  }

  @Override
  public String toString() {
    return "XTenant[ xId=" + xId + " ]";
  }


}
