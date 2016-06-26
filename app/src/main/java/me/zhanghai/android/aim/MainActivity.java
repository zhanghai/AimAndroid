/*
 * Copyright (c) 2016 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.aim;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        mWebView.loadUrl("http://qscdev.zhanghai.me:3000");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        // NOTE: This gives double tap zooming.
        webSettings.setUseWideViewPort(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
    }

    private class ChromeClient extends WebChromeClient {

        // NOTE: WebView can be trying to show an AlertDialog after the activity is finished, which
        // will result in a WindowManager$BadTokenException.
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return MainActivity.this.isFinishing() || super.onJsAlert(view, url, message,
                    result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return MainActivity.this.isFinishing() || super.onJsConfirm(view, url, message,
                    result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                  JsPromptResult result) {
            return MainActivity.this.isFinishing() || super.onJsPrompt(view, url, message,
                    defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return MainActivity.this.isFinishing() || super.onJsBeforeUnload(view, url, message,
                    result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            setTitle(title);
        }
    }
}
