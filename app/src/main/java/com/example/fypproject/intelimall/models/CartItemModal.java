package com.example.fypproject.intelimall.models;

public class CartItemModal {
    int id, product_id, quantity, user_id;
    String last_updated_at;
    ProductModal product;

    public CartItemModal() {
    }

    public CartItemModal(int id, int product_id, int quantity, int user_id, String last_updated_at, ProductModal product) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.user_id = user_id;
        this.last_updated_at = last_updated_at;
        this.product = product;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(String last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public ProductModal getProduct() {
        return product;
    }

    public void setProduct(ProductModal product) {
        this.product = product;
    }
}
