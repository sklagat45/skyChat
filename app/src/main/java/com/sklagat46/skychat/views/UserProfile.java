package com.sklagat46.skychat.views;

public class UserProfile {
    public static String userProfileId;
    public static String txtusername;
    public static String txtuser_email;
    public static String txtpassword;

    public UserProfile(String userProfileId, String username, String user_email,String password) {
        this.userProfileId ="";
        this.txtusername="";
        this.txtuser_email = "";
        this.txtpassword = "";

    }
    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId=userProfileId;
    }

    public String getUsername() {
        return txtusername;
    }

    public void setUsername(String username) {
        this.txtusername=username;
    }

    public String getUserEmail() {
        return txtuser_email;
    }

    public void setUserEmail(String userEmail) {
        this.txtuser_email = userEmail;
    }

    public String getPassword() {
        return txtpassword;
    }

    public void setPassword(String password) {
        this.txtpassword = password;
    }
}

