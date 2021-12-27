package com.example.fypproject.intelimall.models;

import java.util.List;

public class PlaceOrderModal {
    String user_id, price, date;
    List<CartItemModal> cart;

    public PlaceOrderModal() {
    }

    public PlaceOrderModal(String user_id, String price, String date, List<CartItemModal> cart) {
        this.user_id = user_id;
        this.price = price;
        this.date = date;
        this.cart = cart;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CartItemModal> getCart() {
        return cart;
    }

    public void setCart(List<CartItemModal> cart) {
        this.cart = cart;
    }
}
