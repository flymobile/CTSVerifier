package com.fxly.ctsverifier;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;


public class MainActivity extends AppCompatActivity {
    TextView is_inset_earset, is_connect_wifi, device_model, device_platform;
    private SnackBar mSnackBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        webView.loadUrl("http://www.google.com");

        if (!isTaskRoot()) {
            finish();
        }
        setContentView(R.layout.activity_main);
//        setTitle(getString(R.string.app_name, Version.getVersionName(this)));
        setTitle(getString(R.string.app_name) + " V" + Version.getVersionName(this));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        is_inset_earset=(TextView)findViewById(R.id.is_inset_earset);
        is_connect_wifi = (TextView) findViewById(R.id.is_connect_wifi);
        device_model = (TextView) findViewById(R.id.device_build_version);
        device_platform = (TextView) findViewById(R.id.device_platform);
        device_platform.setText(Build.HARDWARE);
        setSupportActionBar(toolbar);
        AudioManager localAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        if(localAudioManager.isWiredHeadsetOn()){
            is_inset_earset.setText("pass");
        }else {
            is_inset_earset.setText("fail");
        }
        if (wifiNetworkInfo.isConnected()) {
            is_connect_wifi.setText("pass");
        } else {
            is_connect_wifi.setText("fail");
        }
        device_model.setText(Build.MODEL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action"+android.os.Build.VERSION.SDK_INT, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl("www.google.com");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Snackbar.make(getApplication(), "Replace with your own action"+android.os.Build.VERSION.SDK_INT, Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            mSnackBar = new SnackBar.Builder(this)
                    .withMessage("提示信息")
                    .withDuration(SnackBar.LONG_SNACK)
                    .show();
            return true;
        }
        if (id == R.id.view) {
            new SnackBar.Builder(this)
                    .withMessageId(R.string.app_name)
                    .withActionMessage("Action") // OR
                    .withDuration(SnackBar.SHORT_SNACK)
                    .show();
            return true;
        }
        if (id == R.id.clear) {
            return true;
        }
        if (id == R.id.export) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //是否连接WIFI
    public boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }
}
