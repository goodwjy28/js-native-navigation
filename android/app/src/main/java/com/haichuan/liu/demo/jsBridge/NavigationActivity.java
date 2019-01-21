package com.haichuan.liu.demo.jsBridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.haichuan.liu.demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class NavigationActivity extends AppCompatActivity implements NativeNavigationInterface{

    public static final String TAG = "NavigationActivity";

    public static final String ROUTER_BUNDLE = "Router";
    public static final String ROUTER_BUNDLE_PATH = "RouterPath";
    public static final String ROUTER_BUNDLE_TYPE = "RouterType";
    public static final String ROUTER_BUNDLE_PARAM = "RouterParam";
    public static final String ROUTER_BACK_INFO = "RouterBackInfo";

    public static final int ROUTER_REQUEST_CODE = 10001;
    public static final int ROUTER_RESPOND_CODE = 10002;

    private WebView mMainWebView;
    private String routerPath;
    private String routerType;
    private String routerParamJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mMainWebView = findViewById(R.id.mainWebView);

        WebSettings webSettings = mMainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        NativeNavigationInterface nni = this;
        WeakReference<NativeNavigationInterface> weakReference = new WeakReference<>(nni);
        mMainWebView.addJavascriptInterface(new NativeNavigationJSImpl(weakReference), "NativeNavigator");

        Bundle routerBundle = getIntent().getBundleExtra(ROUTER_BUNDLE);
        this.routerPath = routerBundle.getString(ROUTER_BUNDLE_PATH);
        this.routerType = routerBundle.getString(ROUTER_BUNDLE_TYPE);
        this.routerParamJson = routerBundle.getString(ROUTER_BUNDLE_PARAM);
        if ("path".equals(this.routerType)) {
            String url = "http://192.168.1.108:8080" + this.routerPath;
            mMainWebView.loadUrl(url);
        } else if ("url".equals(this.routerType)) {
            String url = this.routerPath;
            mMainWebView.loadUrl(url);
        } else {
            Log.d(TAG, "跳转类型错误！");
        }
    }

    @Override
    public String getRouterParamJson() {
        return this.routerParamJson;
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
        if (requestCode == ROUTER_REQUEST_CODE && resultCode == ROUTER_RESPOND_CODE) {
            String backInfo = data.getStringExtra(ROUTER_BACK_INFO);
            try {
                JSONObject jsonObject = new JSONObject(backInfo);
                JsInterfaceUtils.evaluateJs(this.mMainWebView, "$jsBridge.noticeGoBack", null, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
