package com.haichuan.liu.demo.jsBridge;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.haichuan.liu.demo.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by lhc on 2019/1/18.
 */

public class NativeNavigationJSImpl {

    private NativeNavigationInterface mActivity;

    public NativeNavigationJSImpl(WeakReference<NativeNavigationInterface> weakReference) {
        this.mActivity = weakReference.get();
    }

    @JavascriptInterface
     public void push(String path, String type, String paramJson) {
        Class destClass;
        if ("native".equals(type)) {
            destClass = NativeRouter.push(path);
        } else {
            destClass = NavigationActivity.class;
        }
        if (destClass != null) {
            Intent intent = new Intent(this.mActivity.getActivityContext(), destClass);
            Bundle bundle = new Bundle();
            bundle.putString(NavigationActivity.ROUTER_BUNDLE_PATH, path);
            bundle.putString(NavigationActivity.ROUTER_BUNDLE_TYPE, type);
            bundle.putString(NavigationActivity.ROUTER_BUNDLE_PARAM, paramJson);
            intent.putExtra(NavigationActivity.ROUTER_BUNDLE, bundle);
            this.mActivity.getActivityContext().startActivityForResult(intent, NavigationActivity.ROUTER_REQUEST_CODE);
        }
    }

    @JavascriptInterface
    public void getRouteContext(String callback) {
        String paramJson = this.mActivity.getRouterParamJson();
        JSONObject jsonObject = new JSONObject();
        if (paramJson != null && !"".equals(paramJson)) {
            try {
                jsonObject = new JSONObject(paramJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JsInterfaceUtils.evaluateJs(this.mActivity.getMainWebView(), callback, null, jsonObject);
    }

    @JavascriptInterface
    public void goBack(String paramJson) {
        Intent intent = new Intent();
        intent.putExtra(NavigationActivity.ROUTER_BACK_INFO, paramJson);
        this.mActivity.getActivityContext().setResult(NavigationActivity.ROUTER_RESPOND_CODE, intent);
        this.mActivity.getActivityContext().finish();
    }

    @JavascriptInterface
    public void goBackRoot() {
        // MainActivity 的启动模式使用
        Intent intent = new Intent(this.mActivity.getActivityContext(), MainActivity.class);
        this.mActivity.getActivityContext().startActivity(intent);
    }

    // 导航栏设置
    @JavascriptInterface
    public void setBarTitle(String title) {
        this.mActivity.setBarTitle(title);
    }

    @JavascriptInterface
    public void setBarRightButton(String itemInfo, final String callback) {
        try {
            final JSONObject object = new JSONObject(itemInfo);
            this.mActivity.getActivityContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.setBarRightButton(object, callback);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void setBarDoubleRightButton(String itemInfo1, final String callback1, String itemInfo2, final String callback2) {
        try {
            final JSONObject object1 = new JSONObject(itemInfo1);
            final JSONObject object2 = new JSONObject(itemInfo2);
            this.mActivity.getActivityContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.setBarDoubleRightButton(object1, callback1, object2, callback2);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @JavascriptInterface
    public void setBarLeftButton(String itemInfo,final String callback) {
        try {
            final JSONObject object = new JSONObject(itemInfo);
            this.mActivity.getActivityContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.setBarLeftButton(object, callback);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void setBarDoubleLeftButton(String itemInfo1, final String callback1, String itemInfo2, final String callback2) {
        try {
            final JSONObject object1 = new JSONObject(itemInfo1);
            final JSONObject object2 = new JSONObject(itemInfo2);
            this.mActivity.getActivityContext().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.setBarDoubleLeftButton(object1, callback1, object2, callback2);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
