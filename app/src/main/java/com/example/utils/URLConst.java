package com.example.utils;

import android.net.Uri;

/**
 * Created by anirban on 5/1/16.
 */
public class URLConst {
    public static final String BASE_URL = "http://freebumba27.com/hypermarket/api/";


    public static String getBranchList() {
        return Uri.parse(BASE_URL).buildUpon()
                .appendPath("branch_list.php")
                .toString();
    }

    public static String getCategoryList() {
        return Uri.parse(BASE_URL).buildUpon()
                .appendPath("category_list.php")
                .toString();
    }
}
