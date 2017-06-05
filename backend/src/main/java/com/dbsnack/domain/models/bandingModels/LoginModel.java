package com.dbsnack.domain.models.bandingModels;

public class LoginModel {

    private String username;

    private String password;

    Boolean isRememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        isRememberMe = rememberMe;
    }
}
