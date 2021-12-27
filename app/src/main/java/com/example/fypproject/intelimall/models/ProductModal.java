package com.example.fypproject.intelimall.models;

public class ProductModal {
    int id;
    String title, description, image_url, price, category, last_updated_at;

    public ProductModal() {
    }

    public ProductModal(int id, String title, String description, String image_url, String price, String category, String last_updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.category = category;
        this.last_updated_at = last_updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(String last_updated_at) {
        this.last_updated_at = last_updated_at;
    }
}
