package com.haichuan.liu.demo.jsBridge;

import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by lhc on 2019/1/3.
 */

public class JsInterfaceUtils {

    private static final String TAG = "JsInterfaceUtils";

    public static void evaluateJs(final WebView webView, String methodName, final ValueCallback<String> resultCallback, Object ...args) {
        if (webView == null) {
            throw new IllegalArgumentException("webView 不能为 null！");
        } else if (methodName == null || "".equals(methodName)) {
            throw new IllegalArgumentException("methodName 不能为 null 或空字符串！");
        }
        final StringBuilder jsScript = new StringBuilder("javascript:");
        jsScript.append(methodName).append("(");
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof String) {
                jsScript.append("'");
                jsScript.append(arg);
                jsScript.append("'");
            } else {
                jsScript.append(arg);
            }
            if (i < args.length - 1) {
                jsScript.append(",");
            }
        }
        jsScript.append(")");
        Log.d(TAG, "jsScript:" + jsScript.toString());
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript(jsScript.toString(), resultCallback);
            }
        });
    }

}
