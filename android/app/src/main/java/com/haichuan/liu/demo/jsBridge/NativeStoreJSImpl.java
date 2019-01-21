package com.haichuan.liu.demo.jsBridge;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;

import com.haichuan.liu.demo.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lhc on 2019/1/2.
 */

public class NativeStoreJSImpl {

    private static final String TAG = "NativeStoreJSImpl";

    private MainActivity mActivity;

    public NativeStoreJSImpl(MainActivity activity) {
        this.mActivity = activity;
    }

    @JavascriptInterface
    public void userStore(String key, String objectJson,final String result) {
        if (key == null || "".equals(key) || objectJson == null || "".equals(objectJson)) {
            JsInterfaceUtils.evaluateJs(mActivity.getMainWebView(), result, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d(TAG, s);
                }
            }, false, "参数错误");
        } else {
            SharedPreferences sp = this.mActivity.getSharedPreferences("user", Activity.MODE_PRIVATE) ;
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectJson);
            Boolean isSuccess = editor.commit();
            JsInterfaceUtils.evaluateJs(mActivity.getMainWebView(), result, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d(TAG, s);
                }
            }, isSuccess);
        }
    }

    @JavascriptInterface
    public void userRead(String key, final String result) {
        if (key == null || "".equals(key)) {
            JsInterfaceUtils.evaluateJs(mActivity.getMainWebView(), result, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d(TAG, s);
                }
            }, false, "参数错误");
        } else {
            SharedPreferences sp = this.mActivity.getSharedPreferences("user", Activity.MODE_PRIVATE) ;
            String jsonString = sp.getString(key, "");
            try {
                final JSONObject jsonObject = new JSONObject(jsonString);
                JsInterfaceUtils.evaluateJs(mActivity.getMainWebView(), result, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d(TAG, s);
                    }
                }, true, jsonObject);
            } catch (JSONException exception) {
                JsInterfaceUtils.evaluateJs(mActivity.getMainWebView(), result, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d(TAG, s);
                    }
                }, false, "返回数据转 JsonObject 失败!");
            }

        }
    }
}
