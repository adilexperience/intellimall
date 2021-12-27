package com.example.fypproject.intelimall.models;

public class RegisterResponseModal {
    String message;
    UserModal user;

    public RegisterResponseModal() {
    }

    public RegisterResponseModal(String message, UserModal user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModal getUser() {
        return user;
    }

    public void setUser(UserModal user) {
        this.user = user;
    }
}
