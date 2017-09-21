package ru.swew.avil.blankwebview;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;

/**
 * Created by avil on 21.09.17.
 */

public class WebAppInterface {
    Context context;

    WebAppInterface(Context context) {
        this.context = context;
    }

    /**
     * Далее идут методы, которые появятся в JavaScript
     **/
    @JavascriptInterface
    public void sendSms(String phoneNumber, String message) {
        // Code
    }

    @JavascriptInterface
    public void put(String key, String message) {
        SharedPreferences preferences = context.getSharedPreferences("ru.swew.webview", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, message);
        editor.commit();
    }

    @JavascriptInterface
    public String get(String key) {
        SharedPreferences preferences = context.getSharedPreferences("ru.swew.webview", context.MODE_PRIVATE);
        String message = preferences.getString(key, "");
        return message;
    }


}
