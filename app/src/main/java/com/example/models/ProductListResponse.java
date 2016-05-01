package com.example.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by anirban on 5/1/16.
 */
public class ProductListResponse {
    private String id;
    private String product_name;
    private String product_image;
    private String product_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public static ArrayList<ProductListResponse> toList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<ProductListResponse>>() {
        }.getType());
    }

    public static String toStringList(ArrayList<BranchListResponse> branchListResponses) {
        return new Gson().toJson(branchListResponses, new TypeToken<ArrayList<BranchListResponse>>() {
        }.getType());
    }
}
