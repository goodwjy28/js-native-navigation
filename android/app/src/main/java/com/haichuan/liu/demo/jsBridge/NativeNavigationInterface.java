package com.haichuan.liu.demo.jsBridge;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import org.json.JSONObject;

/**
 * Created by lhc on 2019/1/21.
 */

public interface NativeNavigationInterface {

    WebView getMainWebView();
    String getRouterParamJson();
    AppCompatActivity getActivityContext();

    void setBarTitle(String title);
    void setBarRightButton(JSONObject itemInfo, String callback);
    void setBarDoubleRightButton(JSONObject itemInfo1, String callback1, JSONObject itemInfo2, String callback2);
    void setBarLeftButton(JSONObject itemInfo, String callback);
    void setBarDoubleLeftButton(JSONObject itemInfo1, String callback1, JSONObject itemInfo2, String callback2);
}
