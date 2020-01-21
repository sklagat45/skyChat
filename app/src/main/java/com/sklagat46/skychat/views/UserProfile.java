package com.sklagat46.skychat.views;

public class UserProfile {
    public String userProfileId;
    public String username;
    public String user_email;
    public String password;

    public UserProfile(String userProfileId, String username, String user_email,String password) {
        this.userProfileId =userProfileId;
        this.username=username;
        this.user_email = user_email;
        this.password = password;

    }
    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId=userProfileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String userEmail) {
        this.user_email = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

