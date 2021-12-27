package com.example.fypproject.intelimall.models;

public class AddItemInCartModal {
    int product_id, quantity, user_id;
    String date;

    public AddItemInCartModal() {
    }

    public AddItemInCartModal(int product_id, int quantity, int user_id, String date) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.user_id = user_id;
        this.date = date;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
