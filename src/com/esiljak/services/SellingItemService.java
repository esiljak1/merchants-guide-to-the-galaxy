package com.esiljak.services;

import com.esiljak.exceptions.IllegalItemNameException;
import com.esiljak.exceptions.IllegalPriceException;
import com.esiljak.exceptions.IllegalQuantityException;

public class SellingItemService {
    public void checkPriceValidity(float price) throws IllegalPriceException {
        if (price < 0)
            throw new IllegalPriceException("Price cannot be negative");
    }

    public void checkQuantityValidity(int quantity) throws IllegalQuantityException {
        if (quantity <= 0)
            throw new IllegalQuantityException("Quantity has to be positive");
    }

    public void checkNameValidity(String name) throws IllegalItemNameException {
        if (name == null || name.trim().isEmpty())
            throw new IllegalItemNameException("Item name cannot be empty");
    }
}
