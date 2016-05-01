package com.example.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ReuseableClass {

    //===================================================================================================================================
    //check Mobile data and wifi
    //===================================================================================================================================
    public static boolean haveNetworkConnection(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    //====================================================================================================================================
    //checking Mobile data and wifi END
    //====================================================================================================================================


    public static int dpToPx(int dp, Context con) {
        DisplayMetrics displayMetrics = con.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    //====================================================================================================================================
    // Hide KeyBoard
    //====================================================================================================================================

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //===================================================================================================================================
    //Preference variable
    //===================================================================================================================================

    //--------------------------------------------
    // method to save variable in preference
    //--------------------------------------------
    public static void saveInPreference(String name, String content, Context myActivity) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(myActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, content);
        editor.commit();
    }

    //--------------------------------------------
    // getting content from preferences
    //--------------------------------------------
    public static String getFromPreference(String variable_name, Context myActivity) {
        String preference_return;
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(myActivity);
        preference_return = preferences.getString(variable_name, "");

        return preference_return;
    }


    //===================================================================================================================================
    //Preference variable
    //===================================================================================================================================

}
