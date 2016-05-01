package com.example.events;

import android.util.Log;

import com.example.models.ProductListResponse;

import java.util.ArrayList;


/**
 * Created by AJ on 21/5/15.
 */
public class ProductListEvent {
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

        ArrayList<ProductListResponse> productListResponses;

        public Success(ArrayList<ProductListResponse> productListResponses) {
            this.productListResponses = productListResponses;
        }

        public ArrayList<ProductListResponse> getProductListResponses() {
            return productListResponses;
        }

    }
}