package com.example.fypproject.intelimall.models;

import java.util.List;

public class OrderModal {
    int id, user_id;
    String price, status, last_updated_at;
    List<OrderItemModal> item;

    public OrderModal() {
    }

    public OrderModal(int id, int user_id, String price, String status, String last_updated_at, List<OrderItemModal> item) {
        this.id = id;
        this.user_id = user_id;
        this.price = price;
        this.status = status;
        this.last_updated_at = last_updated_at;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(String last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public List<OrderItemModal> getItem() {
        return item;
    }

    public void setItem(List<OrderItemModal> item) {
        this.item = item;
    }
}
