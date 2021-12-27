package com.example.fypproject.intelimall.models;

public class UserModal {
    int id, is_allowed_in_app;
    String name, email_address, phone, address, joined_at;

    public UserModal() {
    }

    public UserModal(int id, int is_allowed_in_app, String name, String email_address, String phone, String address, String joined_at) {
        this.id = id;
        this.is_allowed_in_app = is_allowed_in_app;
        this.name = name;
        this.email_address = email_address;
        this.phone = phone;
        this.address = address;
        this.joined_at = joined_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_allowed_in_app() {
        return is_allowed_in_app;
    }

    public void setIs_allowed_in_app(int is_allowed_in_app) {
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
}
