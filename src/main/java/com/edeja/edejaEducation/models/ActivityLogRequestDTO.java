package com.edeja.edejaEducation.models;

import com.edeja.edejaEducation.entity.adminEntity.XUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel
public class ActivityLogRequestDTO {

    @ApiModelProperty(notes = "Start date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    protected LocalDateTime startDate;

    @ApiModelProperty(notes = "End date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    protected LocalDateTime endDate;

    @ApiModelProperty(notes = "Name of user")
    protected String userName;

    @ApiModelProperty(notes = "Sender name")
    protected String senderName;

    @ApiModelProperty(notes = "Name of tenant")
    protected String tenant;

    @ApiModelProperty(notes = "Category for search", allowableValues = "ALL, LOGIN, LOGOUT, SEARCH", value = "description")
    protected String category;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getUser() {
        return userName;
    }

    public void setUser(XUser user) {
        this.userName = user.getUserName();
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(XUser user) {
        this.senderName = user.getSenderName();
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

