package com.example.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by anirban on 5/1/16.
 */
public class BranchListResponse {
    private int id;
    private String branch_name;
    private String branch_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_image() {
        return branch_image;
    }

    public void setBranch_image(String branch_image) {
        this.branch_image = branch_image;
    }

    public static ArrayList<BranchListResponse> toList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<BranchListResponse>>() {
        }.getType());
    }
}
