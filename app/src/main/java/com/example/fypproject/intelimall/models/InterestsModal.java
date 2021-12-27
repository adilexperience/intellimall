package com.example.fypproject.intelimall.models;

public class InterestsModal {

    int id, user_id;
    String interests;

    public InterestsModal() {
    }

    public InterestsModal(int id, int user_id, String interests) {
        this.id = id;
        this.user_id = user_id;
        this.interests = interests;
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

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
