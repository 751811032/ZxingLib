package com.ca.star;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by TC855 on 2016/11/30.
 */
public class ScanResultActivity extends AutoLayoutActivity implements View.OnClickListener{

    private Button menuButton;
    private Button backButton;
    private TextView title;
    private WebView webview;
    private ProgressBar pg1;
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanresult);
        Log.i("我的信息", "onCreate:");
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        title = (TextView) findViewById(R.id.title_title);
        pg1 = (ProgressBar) findViewById(R.id.progressBar1);
        webview = (WebView) findViewById(R.id.webView);
        menuButton = (Button) findViewById(R.id.right_button);
        backButton = (Button) findViewById(R.id.left_button);
        menuButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        Log.i("我的信息", "onCreate111:");
//        setTitle("关于");
        showBackwardView(true);
        showForwardView(false);
        initWebView();
    }

    protected void showBackwardView(boolean show) {
        if (backButton != null) {
            if (show) {
                backButton.setVisibility(View.VISIBLE);
            } else {
                backButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     * @param show  true则显示
     */
    protected void showForwardView(boolean show) {
        if (menuButton != null) {
            if (show) {
                menuButton.setVisibility(View.VISIBLE);
            } else {
                menuButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    @Override
    public void onClick(View v) {
        Log.i("我的信息", "v.getid:" + v.getId());
        switch (v.getId()) {
            case R.id.left_button:
                this.finish();
                break;
            default:
                break;
        }
    }

    private void setTitle(String Stitle){
        Log.i("我的信息", "Stitle:" + Stitle);
        title.setText(Stitle);
    }

    /**
     * handler处理消息机制
     */
    protected Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Log.i("我的信息", "handleMessage");
                    webview.loadUrl("file:///android_asset/404/index.html");
                    break;
            }
        }
    };

    private void initWebView(){
        pg1 = (ProgressBar) findViewById(R.id.progressBar1);
        webview = (WebView) findViewById(R.id.webView);
        // 开启 DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("aaa", "shouldOverrideUrlLoading=" + url);
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i("我的信息", "onReceivedError");
                view.stopLoading();
                view.clearView();
                Message msg = handler.obtainMessage();//发送通知，加入线程
                msg.what = 1;//通知加载自定义404页面
                handler.sendMessage(msg);//通知发送！
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.i("我的信息", "onReceivedHttpError");
                view.stopLoading();
                view.clearView();
                Message msg = handler.obtainMessage();//发送通知，加入线程
                msg.what = 1;//通知加载自定义404页面
                handler.sendMessage(msg);//通知发送！
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(""+title);
            }
        });

        webview.getSettings().setJavaScriptEnabled(true);// 可用JS
        webview.loadUrl(url);

        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
    }
}
