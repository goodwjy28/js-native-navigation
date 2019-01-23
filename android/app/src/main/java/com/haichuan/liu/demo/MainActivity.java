package com.haichuan.liu.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haichuan.liu.demo.jsBridge.JsInterfaceUtils;
import com.haichuan.liu.demo.jsBridge.NativeNavigationInterface;
import com.haichuan.liu.demo.jsBridge.NativeNavigationJSImpl;
import com.haichuan.liu.demo.jsBridge.NativeRequestJSImpl;
import com.haichuan.liu.demo.jsBridge.NativeRouter;
import com.haichuan.liu.demo.jsBridge.NativeStoreJSImpl;
import com.haichuan.liu.demo.jsBridge.NativeUIJSImpl;
import com.haichuan.liu.demo.jsBridge.NavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements NativeNavigationInterface, View.OnClickListener{

    private WebView mMainWebView;
    private TextView mLeftTextView1, mLeftTextView2, mRightTextView1, mRightTextView2, mTitleTextView;
    private ImageView mLeftImageView1, mLeftImageView2, mRightImageView1, mRightImageView2;
    private String mLeftItem1Callback, mLeftItem2Callback, mRightItem1Callback, mRightItem2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainWebView = findViewById(R.id.mainWebView);
        getNavigationBarPart();

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

        mMainWebView.loadUrl(NativeRouter.ROUTER_BASE_URL);
    }

    private void getNavigationBarPart() {
        mTitleTextView = findViewById(R.id.title_view);
        mLeftTextView1 = findViewById(R.id.left_text_view1);
        mLeftTextView2 = findViewById(R.id.left_text_view2);
        mLeftImageView1 = findViewById(R.id.left_image_view1);
        mLeftImageView2 = findViewById(R.id.left_image_view2);
        mRightTextView1 = findViewById(R.id.right_text_view1);
        mRightTextView2 = findViewById(R.id.right_text_view2);
        mRightImageView1 = findViewById(R.id.right_image_view1);
        mRightImageView2 = findViewById(R.id.right_image_view2);
        mLeftTextView1.setOnClickListener(this);
        mLeftTextView2.setOnClickListener(this);
        mLeftImageView1.setOnClickListener(this);
        mLeftImageView2.setOnClickListener(this);
        mRightTextView1.setOnClickListener(this);
        mRightTextView2.setOnClickListener(this);
        mRightImageView1.setOnClickListener(this);
        mRightImageView2.setOnClickListener(this);
    }

    private void visibilityGoneRightItems() {
        mRightTextView2.setVisibility(View.GONE);
        mRightImageView2.setVisibility(View.GONE);
        mRightImageView1.setVisibility(View.GONE);
        mRightTextView1.setVisibility(View.GONE);
    }

    private void visibilityGoneLeftItems() {
        mLeftTextView2.setVisibility(View.GONE);
        mLeftImageView2.setVisibility(View.GONE);
        mLeftImageView1.setVisibility(View.GONE);
        mLeftTextView1.setVisibility(View.GONE);
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
    public void setBarTitle(String title) {
        this.mTitleTextView.setText(title);
    }

    @Override
    public void setBarRightButton(JSONObject itemInfo, String callback) {
        visibilityGoneRightItems();
        mRightItem1Callback = callback;
        String imageUrl = null, titleStr = null;
        try {
            imageUrl = itemInfo.getString("image");
            titleStr = itemInfo.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (imageUrl != null && !"".equals(imageUrl)) {
            mRightImageView1.setVisibility(View.VISIBLE);
            Glide.with(this).load(imageUrl).into(mRightImageView1);
        } else if (titleStr != null) {
            mRightTextView1.setVisibility(View.VISIBLE);
            mRightTextView1.setText(titleStr);
        }
    }

    @Override
    public void setBarDoubleRightButton(JSONObject itemInfo1, String callback1, JSONObject itemInfo2, String callback2) {
        visibilityGoneRightItems();
        mRightItem1Callback = callback1;
        mRightItem2Callback = callback2;
        String imageUrl1 = null, imageUrl2 = null;
        String titleText1 = null, titleText2 = null;
        try {
            imageUrl1 = itemInfo1.getString("image");
            titleText1 = itemInfo1.getString("title");
            imageUrl2 = itemInfo2.getString("image");
            titleText2 = itemInfo2.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (imageUrl1 != null && !"".equals(imageUrl1)) {
            mRightImageView1.setVisibility(View.VISIBLE);
            Glide.with(this).load(imageUrl1).into(mRightImageView1);
        } else if (titleText1 != null) {
            mRightTextView1.setVisibility(View.VISIBLE);
            mRightTextView1.setText(titleText1);
        }

        if (imageUrl2 != null && !"".equals(imageUrl2)) {
            mRightImageView2.setVisibility(View.VISIBLE);
            Glide.with(this).load(imageUrl2).into(mRightImageView2);
        } else if (titleText2 != null) {
            mRightTextView2.setVisibility(View.VISIBLE);
            mRightTextView2.setText(titleText2);
        }
    }

    @Override
    public void setBarLeftButton(JSONObject itemInfo, String callback) {
        visibilityGoneLeftItems();
        mLeftItem1Callback = callback;
        String imageUrl = null, titleStr = null;
        try {
            imageUrl = itemInfo.getString("image");
            titleStr = itemInfo.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (imageUrl != null && !"".equals(imageUrl)) {
            mLeftImageView1.setVisibility(View.VISIBLE);
            Glide.with(this).load(imageUrl).into(mLeftImageView1);
        } else if (titleStr != null) {
            mLeftTextView1.setVisibility(View.VISIBLE);
            mLeftTextView1.setText(titleStr);
        }
    }

    @Override
    public void setBarDoubleLeftButton(JSONObject itemInfo1, String callback1, JSONObject itemInfo2, String callback2) {
        visibilityGoneLeftItems();
        mLeftItem1Callback = callback1;
        mLeftItem2Callback = callback2;
        String imageUrl1 = null, imageUrl2 = null;
        String titleText1 = null, titleText2 = null;
        try {
            imageUrl1 = itemInfo1.getString("image");
            titleText1 = itemInfo1.getString("title");
            imageUrl2 = itemInfo2.getString("image");
            titleText2 = itemInfo2.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (imageUrl1 != null && !"".equals(imageUrl1)) {
            mLeftImageView1.setVisibility(View.VISIBLE);
            Glide.with(this).load(imageUrl1).into(mLeftImageView1);
        } else if (titleText1 != null) {
            mLeftTextView1.setVisibility(View.VISIBLE);
            mLeftTextView1.setText(titleText1);
        }

        if (imageUrl2 != null && !"".equals(imageUrl2)) {
            mLeftImageView2.setVisibility(View.VISIBLE);
            Glide.with(this).load(imageUrl2).into(mLeftImageView2);
        } else if (titleText2 != null) {
            mLeftTextView2.setVisibility(View.VISIBLE);
            mLeftTextView2.setText(titleText2);
        }
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

    @Override
    public void onClick(View view) {
        String callback = null;
        switch (view.getId()) {
            case R.id.left_text_view1:
                callback = mLeftItem1Callback;
                break;
            case R.id.left_text_view2:
                callback = mLeftItem2Callback;
                break;
            case R.id.left_image_view1:
                callback = mLeftItem1Callback;
                break;
            case R.id.left_image_view2:
                callback = mLeftItem2Callback;
                break;
            case R.id.right_text_view1:
                callback = mRightItem1Callback;
                break;
            case R.id.right_text_view2:
                callback = mRightItem2Callback;
                break;
            case R.id.right_image_view1:
                callback = mRightItem1Callback;
                break;
            case R.id.right_image_view2:
                callback = mRightItem2Callback;
                break;
        }
        JsInterfaceUtils.evaluateJs(this.mMainWebView, callback, null);
    }
}
