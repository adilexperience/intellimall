package com.example.fypproject.intelimall.models;

public class UpdateUserModal {
    String name;
    String email_address;
    String phone;
    String address;

    public UpdateUserModal() {
    }

    public UpdateUserModal(String name, String email_address, String phone, String address) {
        this.name = name;
        this.email_address = email_address;
        this.phone = phone;
        this.address = address;
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
}
