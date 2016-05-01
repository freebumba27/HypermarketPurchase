package com.example.utils;

import android.net.Uri;

/**
 * Created by anirban on 5/1/16.
 */
public class URLConst {
    public static final String BASE_URL = "http://63.142.250.90:8080/html/hypermarket/api/";


    public static String getBranchList() {
        return Uri.parse(BASE_URL).buildUpon()
                .appendPath("branch_list.php")
                .toString();
    }
}
