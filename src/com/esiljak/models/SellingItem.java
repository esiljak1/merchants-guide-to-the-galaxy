package com.esiljak.models;

import com.esiljak.exceptions.IllegalItemNameException;
import com.esiljak.exceptions.IllegalPriceException;
import com.esiljak.exceptions.IllegalQuantityException;
import com.esiljak.services.SellingItemService;

public class SellingItem {
    private String name;
    private float price;
    private final SellingItemService service = new SellingItemService();

    public SellingItem(String name, float price) throws IllegalPriceException, IllegalQuantityException, IllegalItemNameException {
        this(name, price, 1);
    }

    public SellingItem(String name, float price, int quantity) throws IllegalPriceException, IllegalQuantityException, IllegalItemNameException {
        service.checkNameValidity(name);
        service.checkPriceValidity(price);
        service.checkQuantityValidity(quantity);
        this.name = name;
        this.price = price / quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalItemNameException {
        service.checkNameValidity(name);
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public float getPrice(int quantity) {
        return quantity > 0 ? price * quantity : 0;
    }

    public void setPrice(float price) throws IllegalPriceException {
        service.checkPriceValidity(price);
        this.price = price;
    }
}
