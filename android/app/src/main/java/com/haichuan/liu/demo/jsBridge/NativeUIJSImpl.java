package com.haichuan.liu.demo.jsBridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by lhc on 2018/12/21.
 */
// JsUiInterface, JsStoreInterface, JsRequestInterface
public class NativeUIJSImpl {

    private static final String TAG = "NativeUIJSImpl";

    private WebView mWebView;

    public NativeUIJSImpl(WebView webView) {
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void alert(String title, String message, final String affirm, final String cancel) {
        new AlertDialog.Builder(this.mWebView.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JsInterfaceUtils.evaluateJs(mWebView, affirm, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d(TAG, s);
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JsInterfaceUtils.evaluateJs(mWebView, cancel, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d(TAG, s);
                            }
                        });
                    }
                }).show();
    }

}
