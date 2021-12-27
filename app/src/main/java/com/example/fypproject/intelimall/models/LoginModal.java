package com.example.fypproject.intelimall.models;

public class LoginModal {
    String message;
    boolean status;
    UserModal user;

    public LoginModal() {
    }

    public LoginModal(String message, boolean status, UserModal user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserModal getUser() {
        return user;
    }

    public void setUser(UserModal user) {
        this.user = user;
    }
}
