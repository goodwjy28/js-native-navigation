package com.haichuan.liu.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.haichuan.liu.demo.jsBridge.JsInterfaceUtils;
import com.haichuan.liu.demo.jsBridge.NativeNavigationInterface;
import com.haichuan.liu.demo.jsBridge.NativeNavigationJSImpl;
import com.haichuan.liu.demo.jsBridge.NativeRequestJSImpl;
import com.haichuan.liu.demo.jsBridge.NativeStoreJSImpl;
import com.haichuan.liu.demo.jsBridge.NativeUIJSImpl;
import com.haichuan.liu.demo.jsBridge.NavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements NativeNavigationInterface {

    public WebView mMainWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainWebView = findViewById(R.id.mainWebView);

        WebSettings webSettings = mMainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mMainWebView.addJavascriptInterface(new NativeUIJSImpl(this.mMainWebView), "NativeUI");
        mMainWebView.addJavascriptInterface(new NativeStoreJSImpl(this), "NativeStore");
        mMainWebView.addJavascriptInterface(new NativeRequestJSImpl(this.mMainWebView), "NativeRequest");
        NativeNavigationInterface nni = this;
        WeakReference<NativeNavigationInterface> weakReference = new WeakReference<>(nni);
        mMainWebView.addJavascriptInterface(new NativeNavigationJSImpl(weakReference), "NativeNavigator");

        mMainWebView.loadUrl("http://192.168.1.108:8080");
    }

    @Override
    public String getRouterParamJson() {
        return "{}";
    }

    @Override
    public AppCompatActivity getActivityContext() {
        return this;
    }

    @Override
    public WebView getMainWebView() {
        return this.mMainWebView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NavigationActivity.ROUTER_REQUEST_CODE && resultCode == NavigationActivity.ROUTER_RESPOND_CODE) {
            String backInfo = data.getStringExtra(NavigationActivity.ROUTER_BACK_INFO);
            try {
                JSONObject jsonObject = new JSONObject(backInfo);
                JsInterfaceUtils.evaluateJs(this.mMainWebView, "$jsBridge.noticeGoBack", null, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
