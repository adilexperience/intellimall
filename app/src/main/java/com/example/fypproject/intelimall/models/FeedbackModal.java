package com.example.fypproject.intelimall.models;

public class FeedbackModal {
    int id, user_id, order_id;
    double rating;
    String last_activity_at, status, comment;

    public FeedbackModal() {
    }

    public FeedbackModal(int id, int user_id, int order_id, double rating, String last_activity_at, String status, String comment) {
        this.id = id;
        this.user_id = user_id;
        this.order_id = order_id;
        this.rating = rating;
        this.last_activity_at = last_activity_at;
        this.status = status;
        this.comment = comment;
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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLast_activity_at() {
        return last_activity_at;
    }

    public void setLast_activity_at(String last_activity_at) {
        this.last_activity_at = last_activity_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
