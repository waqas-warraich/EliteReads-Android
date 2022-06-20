package com.titanreads.topreads.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserFeedBack {

    public UserFeedBack(){

    }

    String feedBackId;
    String userEmail;
    String feedBackDescription;
    String userDeviceSpecs;

    @ServerTimestamp
    private Date date;

    public String getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(String feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDeviceSpecs() {
        return userDeviceSpecs;
    }

    public void setUserDeviceSpecs(String userDeviceSpecs) {
        this.userDeviceSpecs = userDeviceSpecs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFeedBackDescription() {
        return feedBackDescription;
    }

    public void setFeedBackDescription(String feedBackDescription) {
        this.feedBackDescription = feedBackDescription;
    }
}
