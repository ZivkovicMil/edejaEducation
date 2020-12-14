package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name = "UserRole")
@XmlRootElement
public class UserRole implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @Column(name = "x_id")
  private Integer xId;

  @Column(name = "userRole")
  private String userRole;


  public UserRole() {
  }

  public Integer getxId() {
    return xId;
  }

  public void setxId(Integer xId) {
    this.xId = xId;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "generate.UserRole[ xId=" + xId + " ]";
  }

}
