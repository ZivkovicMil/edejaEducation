package com.edeja.edejaEducation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class JwtUserDto implements Serializable {

  private static final long serialVersionUID = -3539652100855496054L;

  private String userId;

  private String email;

  private String displayName;

  private DateTime tokenExpiresAt;

  private DateTime tokenIssuedAt;

  private List<String> rights;

  private String userLanguage;

  private boolean canDownloadDocument;

  private boolean showPrintedData;

  private String tenantId;

  private HashMap<String, String> additionalFields = new HashMap<>();

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

  public void setTokenIssuedAt(DateTime tokenIssuedAt) {
    this.tokenIssuedAt = tokenIssuedAt;
  }

  public DateTime getTokenIssuedAt() {
    return tokenIssuedAt;
  }

  public void setTokenExpiresAt(DateTime tokenExpiresAt) {
    this.tokenExpiresAt = tokenExpiresAt;
  }

  public DateTime getTokenExpiresAt() {
    return tokenExpiresAt;
  }

  public void setRights(List<String> rights) {
    this.rights = rights;
  }

  public List<String> getRights() {
    return rights;
  }

  public String getUserLanguage() {
    return userLanguage;
  }

  public void setUserLanguage(String userLanguage) {
    this.userLanguage = userLanguage;
  }

  public boolean getCanDownloadDocument() {
    return canDownloadDocument;
  }

  public void setCanDownloadDocument(boolean canDownloadDocument) {
    this.canDownloadDocument = canDownloadDocument;
  }

  public boolean getShowPrintedData() {
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

  public void setAdditionalFields(HashMap<String, String> additionalFields) {
    this.additionalFields = additionalFields;
  }

  @JsonIgnore
  public HashMap<String, String> getAdditionalFields() {
    return additionalFields;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this) //
        .append("userId", this.getUserId()) //
        .append("email", this.getEmail()) //
        .append("displayName", this.getDisplayName()) //
        .append("tokenExpiresAt", this.getTokenExpiresAt()) //
        .append("tokenIssuedAt", this.getTokenIssuedAt()) //
        .append("userLanguage", this.getUserLanguage()) //
        .append("showPrintedData", this.getShowPrintedData()) //
        .append("canDownloadDocument", this.getCanDownloadDocument()) //
        .append("rights", this.getRights()) //
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder() //
        .append(this.getUserId()) //
        .append(this.getEmail()) //
        .append(this.getDisplayName()) //
        .append(this.getTokenExpiresAt()) //
        .append(this.getTokenIssuedAt()) //
        .append(this.getUserLanguage()) //
        .append(this.getCanDownloadDocument()) //
        .append(this.getShowPrintedData()) //
        .append(this.getRights()) //
        .hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof JwtUserDto)) {
      return false;
    }
    JwtUserDto other = (JwtUserDto) obj;

    return new EqualsBuilder() //
        .append(this.getUserId(), other.getUserId()) //
        .append(this.getEmail(), other.getEmail()) //
        .append(this.getDisplayName(), other.getDisplayName()) //
        .append(this.getTokenExpiresAt(), other.getTokenExpiresAt()) //
        .append(this.getTokenIssuedAt(), other.getTokenIssuedAt()) //
        .append(this.getUserLanguage(), other.getUserLanguage()) //
        .append(this.getCanDownloadDocument(), other.getCanDownloadDocument()) //
        .append(this.getShowPrintedData(), other.getShowPrintedData()) //
        .append(this.getRights(), other.getRights()) //
        .isEquals();
  }
}
