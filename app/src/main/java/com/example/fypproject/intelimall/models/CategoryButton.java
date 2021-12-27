package com.example.fypproject.intelimall.models;

public class CategoryButton {
    int id;
    int title;
    String value;
    boolean isSelected;

    public CategoryButton() {
    }

    public CategoryButton(int id, int title, String value, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
