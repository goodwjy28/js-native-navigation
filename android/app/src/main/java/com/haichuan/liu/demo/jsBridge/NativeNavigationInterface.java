package com.haichuan.liu.demo.jsBridge;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by lhc on 2019/1/21.
 */

public interface NativeNavigationInterface {

    WebView getMainWebView();
    String getRouterParamJson();
    AppCompatActivity getActivityContext();
}
