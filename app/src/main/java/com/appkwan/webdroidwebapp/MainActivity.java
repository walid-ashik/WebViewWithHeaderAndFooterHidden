package com.appkwan.webdroidwebapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;
    private WebView mWebView;
    private ProgressBar mLoadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingSpinner = findViewById(R.id.progressBar);
        initWebView();
        initBottomNavigationView();

        //...initially load the home website
        mWebView.loadUrl("https://" + getString(R.string.your_website_url));
        WebViewClient webViewClient = new WebViewClient(this, mWebView);
        webViewClient.improveWebViewPerformance(mLoadingSpinner);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {

                switch (position){
                    case 0:
                        mWebView.loadUrl("https://" + getString(R.string.your_website_url));
                        break;
                    case 1:
                        mWebView.loadUrl("https://unsplash.com/t/travel");
                        break;
                    case 2:
                        mWebView.loadUrl("https://unsplash.com/t/fashion");
                        break;
                    case 3:
                        mWebView.loadUrl("https://unsplash.com/t/food-drink");
                        break;
                    case 4:
                        mWebView.loadUrl("https://unsplash.com/collections");
                        break;
                }


            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });

    }

    private void initWebView() {
        mWebView = findViewById(R.id.webview);
    }

    private void initBottomNavigationView() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_travel_white_24dp, "Travel"))
                .addItem(new BottomNavigationItem(R.drawable.ic_fashion_white_24dp, "Fashion"))
                .addItem(new BottomNavigationItem(R.drawable.ic_food_white_24dp, "Food"))
                .addItem(new BottomNavigationItem(R.drawable.ic_more_white_24dp, "More"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
    }
}
