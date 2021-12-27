package com.example.fypproject.intelimall.models;

public class RegisterRequestModal {
    String name, email_address, password, phone, address, joined_at;
    int is_allowed_in_app;

    public RegisterRequestModal() {
    }

    public RegisterRequestModal(String name, String email_address, String password, String phone, String address, String joined_at, int is_allowed_in_app) {
        this.name = name;
        this.email_address = email_address;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.joined_at = joined_at;
        this.is_allowed_in_app = is_allowed_in_app;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(String joined_at) {
        this.joined_at = joined_at;
    }

    public int getIs_allowed_in_app() {
        return is_allowed_in_app;
    }

    public void setIs_allowed_in_app(int is_allowed_in_app) {
        this.is_allowed_in_app = is_allowed_in_app;
    }
}
