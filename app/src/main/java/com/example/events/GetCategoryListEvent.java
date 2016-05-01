package com.example.events;

import android.util.Log;

import com.example.models.CategoryListResponse;

import java.util.ArrayList;


/**
 * Created by AJ on 21/5/15.
 */
public class GetCategoryListEvent {
    public static class Fail {
        Exception ex;

        public Fail(Exception ex) {
            this.ex = ex;
        }

        public Exception getEx() {
            Log.d("TAG", "Error");

            return ex;
        }
    }

    public static class Success {

        ArrayList<CategoryListResponse> categoryListResponses;

        public Success(ArrayList<CategoryListResponse> categoryListResponses) {
            this.categoryListResponses = categoryListResponses;
        }

        public ArrayList<CategoryListResponse> getCategoryListResponses() {
            return categoryListResponses;
        }

    }
}