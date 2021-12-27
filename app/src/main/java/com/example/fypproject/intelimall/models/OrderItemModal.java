package com.example.fypproject.intelimall.models;

public class OrderItemModal {
    int id, quantity, product_id, order_id;
    String price, added_at;
    ProductModal products;

    public OrderItemModal() {
    }

    public OrderItemModal(int id, int quantity, int product_id, int order_id, String price, String added_at, ProductModal products) {
        this.id = id;
        this.quantity = quantity;
        this.product_id = product_id;
        this.order_id = order_id;
        this.price = price;
        this.added_at = added_at;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public ProductModal getProducts() {
        return products;
    }

    public void setProducts(ProductModal products) {
        this.products = products;
    }
}
