package ru.swew.avil.blankwebview;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView vw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void onBackPressed() {
        vw.loadUrl("javascript: windowClose();");
        MainActivity.this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        vw.loadUrl("javascript: windowClose();");
        MainActivity.this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        vw.loadUrl("javascript: windowOpen();");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vw.loadUrl("javascript: windowClose();");
        MainActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (vw.canGoBack()) {
                vw.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        vw = (WebView) findViewById(R.id.webview);
        //** Отключили вертикальную прокрутку **/
        vw.setVerticalScrollBarEnabled(false);
        //** Отключили горизонтальную прокрутку **/
        vw.setHorizontalScrollBarEnabled(false);
        //** Включили JavaScript **/
        vw.getSettings().setJavaScriptEnabled(true);
        //** Включили localStorage и т. п. **/
        vw.getSettings().setDomStorageEnabled(true);
        //** Отключили зум, т .к. нормальные приложения подобным функционалом не обладают **/
        vw.getSettings().setSupportZoom(false);
        //** Отключили поддержку вкладок, т .к. пользователь должен сидеть в SPA приложении **/
        vw.getSettings().setSupportMultipleWindows(false);
        //** Отключение контекстных меню по долгому клику **/
        vw.setLongClickable(false);
        //** в JavaScript'е создается объект API. Это будет наш мост в мир Java. **/
        vw.addJavascriptInterface(new WebAppInterface(this), "API");
        //** загрузили нашу страничку **/
        vw.loadUrl("file:///android_asset/index.html");
        vw.setWebViewClient(new WebViewClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                vw.setWebContentsDebuggingEnabled(true);
            }
        }
    }
}
