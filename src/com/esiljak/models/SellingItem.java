package com.esiljak.models;

import com.esiljak.exceptions.IllegalPriceException;
import com.esiljak.exceptions.IllegalQuantityException;

public class SellingItem {
    private String name;
    private float price;

    private void checkPriceValidity(float price) throws IllegalPriceException {
        if (price < 0)
            throw new IllegalPriceException("Price cannot be negative");
    }

    private void checkQuantityValidity(int quantity) throws IllegalQuantityException {
        if (quantity <= 0)
            throw new IllegalQuantityException("Quantity has to be positive");
    }

    public SellingItem(String name, float price) throws IllegalPriceException, IllegalQuantityException {
        this(name, price, 1);
    }

    public SellingItem(String name, float price, int quantity) throws IllegalPriceException, IllegalQuantityException {
        checkPriceValidity(price);
        checkQuantityValidity(quantity);
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
        return quantity > 0 ? price * quantity : 0;
    }

    public void setPrice(float price) throws IllegalPriceException {
        checkPriceValidity(price);
        this.price = price;
    }
}
