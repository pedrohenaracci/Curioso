package com.curioso.curiosoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.curioso.curiosoapp.Model.News;
import com.curioso.curiosoapp.util.FavoritosParse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WebViewActivity extends AppCompatActivity {

    private ImageButton favbtn;
    private boolean pass = true;
    private String url;
    private String img;
    private String title;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("title");

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl(url);

        myWebView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = myWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
    }







}
