package com.appkwan.webdroidwebapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.appkwan.webdroidwebapp.R.id.bottom_sheet_cycle;
import static com.appkwan.webdroidwebapp.R.id.visible;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.ll_transparent_bg)
    LinearLayout llTransparentBg;

    @BindView(R.id.webview)
    WebView mWebView;

    @BindView(R.id.progressBar)
    ProgressBar mLoadingSpinner;

    @BindView(R.id.sheet_bottom_layout)
    RelativeLayout mSheetBottomLayout;

    private BottomSheetBehavior mBottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomSheet = BottomSheetBehavior.from(mSheetBottomLayout);

        initWebView();
        initBottomNavigationView();
        bottomNavigationBar.setTabSelectedListener(this);

        //...initially load the home website
        mWebView.loadUrl("https://" + getString(R.string.your_website_url));
        WebViewClient webViewClient = new WebViewClient(this, mWebView);
        webViewClient.improveWebViewPerformance(mLoadingSpinner);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        llTransparentBg.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //...sheet is in background mode
                        llTransparentBg.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //...sheet is opened completely
                        llTransparentBg.setVisibility(View.VISIBLE);
                        llTransparentBg.setClickable(false);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        //...hide bottom sheet if that is opened
        if (mBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            hideBottomSheet();
        }

    }

    private void initWebView() {
        mWebView = findViewById(R.id.webview);
    }

    private void initBottomNavigationView() {
        bottomNavigationBar
                //...TODO: replace with your URL name below
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

    @OnClick(R.id.ll_transparent_bg)
    public void onBottomSheetTransparentBgClicked(View view) {
        hideBottomSheet();
    }


    public void hideBottomSheet() {
        mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {

            case 0:
                //...TODO: replace with your URL
                mWebView.loadUrl("https://" + getString(R.string.your_website_url));
                break;

            case 1:
                //...TODO: replace with your URL in TRAVEL position
                mWebView.loadUrl("https://unsplash.com/t/travel");
                break;

            case 2:
                //...TODO: replace with your URL in FASHION position
                mWebView.loadUrl("https://unsplash.com/t/fashion");
                break;

            case 3:
                //...TODO: replace with your URL in FOOD position
                mWebView.loadUrl("https://unsplash.com/t/food-drink");
                break;

            case 4:
                Log.d(TAG, "onTabSelected: clicked!");
                bottomNavigationBar.clearAll();
                initBottomNavigationView();
                mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;

        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    //...bottom sheet layout properties
    @OnClick({R.id.bottom_sheet_face,
            R.id.bottom_sheet_home,
            R.id.bottom_sheet_cycle,
            R.id.bottom_sheet_travel,
            R.id.bottom_sheet_spoon,
            R.id.bottom_sheet_face2,
            R.id.bottom_sheet_home2,
            R.id.bottom_sheet_cycle2,
            R.id.bottom_sheet_travel2,
            R.id.bottom_sheet_spoon2})
    public void itemPicker(LinearLayout view) {

        hideBottomSheet();

        switch (view.getId()) {

            case R.id.bottom_sheet_home:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashik");
                break;

            case R.id.bottom_sheet_face:
                //...TODO: replace with bottom hidden face
                mWebView.loadUrl("https://www.uplabs.com/");
                break;

            case R.id.bottom_sheet_cycle:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            case R.id.bottom_sheet_travel:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            case R.id.bottom_sheet_spoon:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            case R.id.bottom_sheet_home2:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashik");
                break;

            case R.id.bottom_sheet_face2:
                //...TODO: replace with bottom hidden face
                mWebView.loadUrl("https://www.uplabs.com/");
                break;

            case R.id.bottom_sheet_cycle2:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            case R.id.bottom_sheet_travel2:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            case R.id.bottom_sheet_spoon2:
                //...TODO: replace with bottom hidden
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            default:
                break;

        }
    }

}
