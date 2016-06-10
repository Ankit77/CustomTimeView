package com.example.customtimeview;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


/**
 * <p>Author: P.T</p>
 * <p>Version: 1.0</p>
 * <p>Since: 20-5-2016</p>
 * <p>Purpose: Creating Custom Webview and Chromium tab</p>
 * <p>It is providing enable properties to set url, progressbar, javascript enabled,
 * BuiltInZoomControls, DisplayZoomControls, LoadWithOverviewMode, setUseWideViewPort, And Chromium tab </p>
 */
public class FragmentWebView extends Fragment {
    private View view;
    public  WebView webView;
    public  CustomTabsIntent.Builder builder;
    private ProgressBar pb_progress;
    private LinearLayout layout;

    private boolean showProgress;
    private boolean setWebViewClient;
    private boolean setJavaScriptEnabled;
    private boolean setBuiltInZoomControls;
    private boolean setDisplayZoomControls;
    private boolean setLoadWithOverviewMode;
    private boolean setUseWideViewPort;
    private boolean setChromiumTab;

    private String url;

    public void setUrl(String url) {
        Log.e("setUrl","on create called");
        this.url = url;

        if (setChromiumTab == false) {
            setWebViewClientEnabled(this.url);
        } else {
            setChromiumTabEnabled(this.url);
        }

    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("onCreate","on create called");
        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        webView = new WebView(getActivity());
        LinearLayout.LayoutParams webviewparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(webviewparams);
        pb_progress = new ProgressBar(getActivity(), null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams pbparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pb_progress.setLayoutParams(pbparams);
        layout.addView(pb_progress);
        layout.addView(webView);
        view = layout;
        if (setChromiumTab == false) {
            setWebViewClientEnabled(this.url);
        } else {
            setChromiumTabEnabled(this.url);
        }

        return view;
    }


    /**
     * Used to enable Chromium Tab
     *
     * @param url
     */
    private void setChromiumTabEnabled(String url) {
        Log.e("setChromiumTabEnabled","setChromiumTabEnabled called");
        builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setToolbarColor(Color.BLUE);
        builder.build().launchUrl(getActivity(), Uri.parse(url));

    }

    /**
     * Used to enable WebView
     *
     * @param url
     */
    private void setWebViewClientEnabled(String url) {
        Log.e("setWebViewClientEnabled", String.valueOf(setWebViewClient));
        webView.setWebChromeClient(new WebChromeClient());

        if (showProgress == false) {
                pb_progress.setVisibility(View.GONE);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                    pb_progress.setProgress(newProgress * 10);

            }
        });
        if (setWebViewClient == true) {
            webView.setWebViewClient(new MyBrowser());
        }
        if (setJavaScriptEnabled == true) {

            webView.getSettings().setJavaScriptEnabled(true);
        }
        if (setBuiltInZoomControls == true) {
            webView.getSettings().setBuiltInZoomControls(true);
        }
        if (setDisplayZoomControls == true) {
            webView.getSettings().setDisplayZoomControls(false);
        }
        if (setLoadWithOverviewMode == true) {
            webView.getSettings().setLoadWithOverviewMode(true);
        }
        if (setUseWideViewPort == true) {
            webView.getSettings().setUseWideViewPort(true);
        }
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.loadUrl(url);

            pb_progress.setProgress(0);

    }

    /**
     * Used for inflating( to get) attrs (custom Property) from user
     *
     * @param activity
     * @param attrs
     * @param savedInstanceState
     */
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        Log.e("onInflate", "onInflate called");
        TypedArray typedArray = activity.obtainStyledAttributes(attrs, R.styleable.FragmentWebView);

        url = typedArray.getString(R.styleable.FragmentWebView_url);
        showProgress = typedArray.getBoolean(R.styleable.FragmentWebView_showProgress, true);
        setWebViewClient = typedArray.getBoolean(R.styleable.FragmentWebView_setWebViewClient, true);
        setJavaScriptEnabled = typedArray.getBoolean(R.styleable.FragmentWebView_isJaveScriptEnabled, true);
        setBuiltInZoomControls = typedArray.getBoolean(R.styleable.FragmentWebView_setBuiltInZoomControls, true);
        setDisplayZoomControls = typedArray.getBoolean(R.styleable.FragmentWebView_setDisplayZoomControls, false);
        setLoadWithOverviewMode = typedArray.getBoolean(R.styleable.FragmentWebView_setLoadWithOverviewMode, true);
        setUseWideViewPort = typedArray.getBoolean(R.styleable.FragmentWebView_setUseWideViewPort, true);
        setChromiumTab = typedArray.getBoolean(R.styleable.FragmentWebView_setChromiumTab, false);

    }


    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

                pb_progress.setVisibility(View.GONE);
                FragmentWebView.this.pb_progress.setProgress(100);

            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Toast.makeText(getContext(), "Oh no! " + request, Toast.LENGTH_SHORT).show();
            super.onReceivedError(view, request, error);
        }
    }
}
