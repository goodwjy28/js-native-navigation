package com.haichuan.liu.demo.jsBridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haichuan.liu.demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class NavigationActivity extends AppCompatActivity implements NativeNavigationInterface, View.OnClickListener{

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

    private TextView mLeftBackView, mRightTextView1, mRightTextView2, mTitleTextView;
    private ImageView mRightImageView1, mRightImageView2;
    private String mRightItem1Callback, mRightItem2Callback;

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

        getNavigationBarPart();

        Bundle routerBundle = getIntent().getBundleExtra(ROUTER_BUNDLE);
        this.routerPath = routerBundle.getString(ROUTER_BUNDLE_PATH);
        this.routerType = routerBundle.getString(ROUTER_BUNDLE_TYPE);
        this.routerParamJson = routerBundle.getString(ROUTER_BUNDLE_PARAM);
        if ("path".equals(this.routerType)) {
            String url = NativeRouter.ROUTER_BASE_URL + this.routerPath;
            mMainWebView.loadUrl(url);
        } else if ("url".equals(this.routerType)) {
            String url = this.routerPath;
            mMainWebView.loadUrl(url);
        } else {
            Log.d(TAG, "跳转类型错误！");
        }
    }

    private void getNavigationBarPart() {
        mTitleTextView = findViewById(R.id.title_view);
        mLeftBackView = findViewById(R.id.left_back_view);
        mRightTextView1 = findViewById(R.id.right_text_view1);
        mRightTextView2 = findViewById(R.id.right_text_view2);
        mRightImageView1 = findViewById(R.id.right_image_view1);
        mRightImageView2 = findViewById(R.id.right_image_view2);
        mLeftBackView.setOnClickListener(this);
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

    @Override
    public String getRouterParamJson() {
        return this.routerParamJson;
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
    public void setBarLeftButton(JSONObject itemInfo, String callback) {}

    @Override
    public void setBarDoubleLeftButton(JSONObject itemInfo1, String callback1, JSONObject itemInfo2, String callback2) {}

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

    @Override
    public void onClick(View view) {
        String callback = null;
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                return;
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
