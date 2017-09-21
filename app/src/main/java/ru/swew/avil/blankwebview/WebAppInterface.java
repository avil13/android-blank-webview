package ru.swew.avil.blankwebview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.webkit.JavascriptInterface;

import java.io.IOException;


public class WebAppInterface {
    Context context;
    MediaPlayer mp;

    WebAppInterface(Context context) {
        this.context = context;
        mp = new MediaPlayer();
    }

    /**
     * Далее идут методы, которые появятся в JavaScript объекте
     **/
    @JavascriptInterface
    public void sendSms(String phoneNumber, String message) {
        // Code
    }

    @JavascriptInterface
    public void audio(String url) {
        try {
            AssetFileDescriptor soundClick = context.getAssets().openFd(url);
            mp.reset();
            mp.setDataSource(soundClick.getFileDescriptor(), soundClick.getStartOffset(), soundClick.getLength());
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void putStore(String key, String message) {
        SharedPreferences preferences = context.getSharedPreferences("ru.swew.webview", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, message);
        editor.commit();
    }

    @JavascriptInterface
    public String getStore(String key) {
        SharedPreferences preferences = context.getSharedPreferences("ru.swew.webview", context.MODE_PRIVATE);
        String message = preferences.getString(key, "");
        return message;
    }

}
