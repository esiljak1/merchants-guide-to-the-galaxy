package com.esiljak.models;

public class SellingItem {
    private String name;
    private float price;

    public SellingItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public SellingItem(String name, float price, int quantity) {
        this.name = name;
        this.price = price / quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public float getPrice(int quantity) {
        return price / quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
