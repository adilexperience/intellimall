package com.example.fypproject.intelimall.models;

public class FeedbackRequestModal {
    int id;
    String last_activity_at, status, rating, comment;

    public FeedbackRequestModal() {
    }

    public FeedbackRequestModal(int id, String last_activity_at, String status, String rating, String comment) {
        this.id = id;
        this.last_activity_at = last_activity_at;
        this.status = status;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
