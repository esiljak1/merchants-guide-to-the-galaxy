package com.esiljak.models;

import com.esiljak.exceptions.IllegalItemNameException;
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

    private void checkNameValidity(String name) throws IllegalItemNameException {
        if (name == null || name.trim().isEmpty())
            throw new IllegalItemNameException("Item name cannot be empty");
    }

    public SellingItem(String name, float price) throws IllegalPriceException, IllegalQuantityException, IllegalItemNameException {
        this(name, price, 1);
    }

    public SellingItem(String name, float price, int quantity) throws IllegalPriceException, IllegalQuantityException, IllegalItemNameException {
        checkNameValidity(name);
        checkPriceValidity(price);
        checkQuantityValidity(quantity);
        this.name = name;
        this.price = price / quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalItemNameException {
        checkNameValidity(name);
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
