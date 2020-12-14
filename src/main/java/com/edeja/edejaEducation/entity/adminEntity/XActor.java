package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "XActor")
@XmlRootElement
public class XActor implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @Column(name = "x_id")
  private Integer xId;

  @Column(name = "active")
  private boolean active;

  @Column(name = "ident")
  private String ident;

  @Column(name = "ldapAttributes")
  private String ldapAttributes;

  @Column(name = "name")
  private String name;

  @Column(name = "store")
  private String store;

  @Column(name = "storeReference")
  private String storeReference;

  @Column(name = "ldapConfigId")
  private String ldapConfigId;

  @Column(name = "canDownloadDocument")
  private boolean canDownloadDocument;

  @Column(name = "userLanguage")
  private String userLanguage;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "x_id", nullable = false)
  private XUser xUser;

  @ManyToOne
  @JoinColumn(name = "tenantId")
  private XTenant xTenant;


  public XActor() {
  }

  public XActor(Integer xId) {
    this.xId = xId;
  }

  public Integer getxId() {
    return xId;
  }

  public void setxId(Integer xId) {
    this.xId = xId;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getIdent() {
    return ident;
  }

  public void setIdent(String ident) {
    this.ident = ident;
  }

  public String getLdapAttributes() {
    return ldapAttributes;
  }

  public void setLdapAttributes(String ldapAttributes) {
    this.ldapAttributes = ldapAttributes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getStoreReference() {
    return storeReference;
  }

  public void setStoreReference(String storeReference) {
    this.storeReference = storeReference;
  }

  public String getLdapConfigId() {
    return ldapConfigId;
  }

  public void setLdapConfigId(String ldapConfigId) {
    this.ldapConfigId = ldapConfigId;
  }

  public boolean isCanDownloadDocument() {
    return canDownloadDocument;
  }

  public void setCanDownloadDocument(boolean canDownloadDocument) {
    this.canDownloadDocument = canDownloadDocument;
  }

  public String getUserLanguage() {
    return userLanguage;
  }

  public void setUserLanguage(String userLanguage) {
    this.userLanguage = userLanguage;
  }

  public XTenant getxTenant() {
    return xTenant;
  }

  public void setxTenant(XTenant xTenant) {
    this.xTenant = xTenant;
  }

  @Override
  public String toString() {
    return "generate.XActor[ xId=" + xId + " ]";
  }

}
