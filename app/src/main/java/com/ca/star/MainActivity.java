package com.ca.star;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
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
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;
import java.util.List;


public class MainActivity extends AutoLayoutActivity  {
    @SuppressLint("JavascriptInterface")

    private Button scanButton;

    private static final String APP_CACAHE_DIRNAME = "/webcache";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        String result = data.getStringExtra("scan_result");
                        Log.i("我的信息", "resultresult:" + result);
                        if (result.contains("http")||result.contains("www")){
                            Intent intent = new Intent(MainActivity.this,ScanResultActivity.class);
                            intent.putExtra("url",result);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
//                        ToastUtil.createNormal(this, App.res.getString(R.string.scan_fial));
                    }
                } else if (resultCode == RESULT_CANCELED) {
//                    ToastUtil.createNormal(this, App.res.getString(R.string.scan_fial));
                }
                break;
            default:
                break;
        }
    }

}
