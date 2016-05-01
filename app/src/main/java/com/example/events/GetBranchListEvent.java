package com.example.events;

import android.util.Log;

import com.example.models.BranchListResponse;

import java.util.ArrayList;


/**
 * Created by AJ on 21/5/15.
 */
public class GetBranchListEvent {
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

        ArrayList<BranchListResponse> orderListResponses;

        public Success(ArrayList<BranchListResponse> orderListResponses) {
            this.orderListResponses = orderListResponses;
        }

        public ArrayList<BranchListResponse> getOrderListResponses() {
            return orderListResponses;
        }

    }
}