package com.haichuan.liu.demo.jsBridge;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lhc on 2019/1/3.
 */

public class NativeRequestJSImpl {

    private static final String TAG = "NativeRequestJSImpl";
    private WebView mWebView;
    public NativeRequestJSImpl(WebView webView) {
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void getRequest(String url, String paramsJson, final String success, final String fail) {
        StringBuilder urlStringBuilder = new StringBuilder(url);
        try {
            JSONObject jsonObject = new JSONObject(paramsJson);
            Iterator<String> iterator = jsonObject.keys();
            urlStringBuilder.append("?");
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = jsonObject.get(key);
                urlStringBuilder.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    urlStringBuilder.append("&");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlStringBuilder.toString())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("code", "request_io_error");
                    jsonObject.put("message", e.getMessage());
                    JsInterfaceUtils.evaluateJs(mWebView, fail, null, jsonObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        final JSONObject jsonObject = new JSONObject(result);
                        JsInterfaceUtils.evaluateJs(mWebView, success, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d(TAG, s);
                            }
                        }, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("code", "json_error");
                            jsonObject.put("message", e.getMessage());
                            JsInterfaceUtils.evaluateJs(mWebView, fail, null, jsonObject);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("code", "request_io_error");
                        jsonObject.put("message", "未知错误");
                        JsInterfaceUtils.evaluateJs(mWebView, fail, null, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
