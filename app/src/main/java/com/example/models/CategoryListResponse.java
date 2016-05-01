package com.example.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by anirban on 5/1/16.
 */
public class CategoryListResponse {
    private int id;
    private String category_name;
    private String category_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public static ArrayList<CategoryListResponse> toList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<CategoryListResponse>>() {
        }.getType());
    }
}
