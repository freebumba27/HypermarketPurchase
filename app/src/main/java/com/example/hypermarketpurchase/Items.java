package com.example.hypermarketpurchase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Items {

    private String name;
    private double price;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static ArrayList<Items> toList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<Items>>() {
        }.getType());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
