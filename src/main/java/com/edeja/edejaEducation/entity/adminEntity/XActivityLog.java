package com.edeja.edejaEducation.entity.adminEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "XActivityLog")
@XmlRootElement
public class XActivityLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "x_id")
    private Integer xid;

    @Column(name = "logDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;

    @Column(name = "category")
    private String category;

    @Column(name = "details")
    private String details;

    @Column(name = "sendername")
    private String sendername;

    @Column(name = "username")
    private String username;

    @Column(name = "applicationName")
    private String applicationName;

    public XActivityLog() {
        super();
    }

    public XActivityLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return "XActivityLog [date=" + this.logDate
                + ", category=" + this.category
                + ", details=" + this.details
                + ", sendername=" + this.sendername
                + ", username=" + this.username
                + ", application name=" + this.applicationName
                + "]";
    }

    /**
     * The Class Builder.
     */
    public static class Builder {

        private XActivityLog entry = new XActivityLog();

        private Builder() {
            super();
        }

        public static Builder instance() {
            return new Builder();
        }

        public XActivityLog build() {
            return this.entry;
        }

        public Builder date(Date logDate) {
            this.entry.setLogDate(logDate);
            return this;
        }

        public Builder category(String category) {
            this.entry.setCategory(category);
            return this;
        }

        public Builder details(String details) {
            this.entry.setDetails(details);
            return this;
        }

        public Builder senderName(String senderName) {
            this.entry.setSendername(senderName);
            return this;
        }

        public Builder userName(String userName) {
            this.entry.setUsername(userName);
            return this;
        }

        public Builder applicationName(String applicationName) {
            this.entry.setApplicationName(applicationName);
            return this;
        }

        public Builder userId(Integer xId) {
            this.entry.setXid(xId);
            return this;
        }
    }
}
