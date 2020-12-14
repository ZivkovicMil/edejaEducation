package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name = "XAdminUser")
@XmlRootElement
public class XAdminUser extends XUserParent {

  private static final long serialVersionUID = 1L;

  @Column(name = "userName")
  private String userName;

  @ManyToOne
  @JoinColumn(name = "tenantId")
  private XTenant xTenant;

  @JoinTable(name = "XAdminUser_UserRole", joinColumns = {
      @JoinColumn(name = "members_x_id", referencedColumnName = "x_id")}, inverseJoinColumns = {
      @JoinColumn(name = "userRoles_x_id", referencedColumnName = "x_id")})
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<UserRole> userRoleList;

  @Column(name = "userLanguage")
  private String userLanguage;

  @Column(name = "canDownloadDocument")
  private boolean canDownloadDocument;

  public XAdminUser() {
  }

  public XAdminUser(Integer xId) {
    this.xId = xId;
  }

  public XTenant getxTenant() {
    return xTenant;
  }

  public void setxTenant(XTenant xTenant) {
    this.xTenant = xTenant;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof XAdminUser)) {
      return false;
    }
    XAdminUser other = (XAdminUser) object;
    if ((this.xId == null && other.xId != null) || (this.xId != null && !this.xId.equals(other.xId))) {
      return false;
    }
    return true;
  }

  public String getUserName() {
    return this.userName;
  }

  @Override
  public String getSenderName() {
    return this.userName;
  }

  @Override
  public boolean isUserActive() {
    return true;
  }

  @Override
  public String getUserLanguage() {
    return this.userLanguage;
  }

  @Override
  public boolean getCanDownloadDocument() {
    return this.canDownloadDocument;
  }

  @Override
  public String getTenantName() {
    return this.xTenant.getTenantId();
  }

  @Override
  public String getEmail() {
    return this.userName;
  }

  @Override
  public boolean showPrintedData() {
    return true;
  }

  @Override
  public void setUserLanguage(String userLanguage) {
    this.userLanguage = userLanguage;
  }

  @Override
  public void setCanDownloadDocument(boolean canDownloadDocument) {
    this.canDownloadDocument = canDownloadDocument;
  }

  @Override
  public List<UserRole> getUserRoles() {
    return this.userRoleList;
  }

  @Override
  public void setUserRoles(List<UserRole> userRoleList) {
    this.userRoleList = userRoleList;
  }

  @Override
  public String toString() {
    return "XAdminUser[ xId=" + xId + " ]";
  }
}
