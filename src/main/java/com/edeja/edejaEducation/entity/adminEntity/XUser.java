package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "XUser")
@XmlRootElement
public class XUser extends XUserParent {

    private static final long serialVersionUID = 1L;
    @Column(name = "authMode")
    private String authMode;
    @Column(name = "bpk")
    private String bpk;
    @Column(name = "salutation")
    private String salutation;

    @Basic(optional = false)
    @Column(name = "printingFacilityUser")
    private boolean printingFacilityUser;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "xUser")
    private XActor xActor;

    @Column(name = "email")
    private String email;

    @Column(name = "sendStatistic")
    private boolean sendStatistic;

    @Transient
    private List<UserRole> userRoles = new ArrayList<>();

    public XUser() {
    }

    public XUser(Integer xId) {
        this.xId = xId;
    }

    public XUser(Integer xId, boolean printingFacilityUser) {
        super();
        this.xId = xId;
        this.printingFacilityUser = printingFacilityUser;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getBpk() {
        return bpk;
    }

    public void setBpk(String bpk) {
        this.bpk = bpk;
    }

    public boolean getSendStatistic() {
        return sendStatistic;
    }

    public void setSendStatistic(boolean sendStatistic) {
        this.sendStatistic = sendStatistic;
    }

    @Override
    public String getUserName() {
        return this.email;
    }

    @Override
    public String getSenderName() {
        return this.xActor.getName();
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUserLanguage() {
        return this.xActor.getUserLanguage();
    }

    @Override
    public boolean getCanDownloadDocument() {
        return this.xActor.isCanDownloadDocument();
    }

    @Override
    public String getTenantName() {
        return this.xActor.getxTenant().getTenantId();
    }

    @Override
    public boolean showPrintedData() {
        return this.xActor.getxTenant().isShowPrintedData();
    }

    @Override
    public void setUserLanguage(String userLanguage) {
        this.xActor.setUserLanguage(userLanguage);
    }

    @Override
    public void setCanDownloadDocument(boolean canDownload) {
        this.xActor.setCanDownloadDocument(canDownload);
    }

    @Override
    public List<UserRole> getUserRoles() {
        return this.userRoles;
    }

    @Override
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean isUserActive() {
        return getxActor().isActive();
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public Integer getXId() {
        return xId;
    }

    public void setXId(Integer xId) {
        this.xId = xId;
    }

    public boolean getPrintingFacilityUser() {
        return printingFacilityUser;
    }

    public void setPrintingFacilityUser(boolean printingFacilityUser) {
        this.printingFacilityUser = printingFacilityUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public XActor getxActor() {
        return xActor;
    }

    public void setxActor(XActor xActor) {
        this.xActor = xActor;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof XUser)) {
            return false;
        }
        XUser other = (XUser) object;
        if ((this.xId == null && other.xId != null) || (this.xId != null && !this.xId.equals(other.xId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "XUser[ xId=" + xId + " ]";
    }

}