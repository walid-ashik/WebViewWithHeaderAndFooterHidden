package com.appkwan.webdroidwebapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.ll_transparent_bg)
    LinearLayout llTransparentBg;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.webview)
    WebView mWebView;

    @BindView(R.id.progressBar)
    AVLoadingIndicatorView mLoadingSpinner;

    @BindView(R.id.sheet_bottom_layout)
    RelativeLayout mSheetBottomLayout;

    private BottomSheetBehavior mBottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomSheet = BottomSheetBehavior.from(mSheetBottomLayout);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        initWebView();
        initBottomNavigationView();
        bottomNavigationBar.setTabSelectedListener(this);

        //...initially load the home website
        mWebView.loadUrl("https://" + getString(R.string.your_website_url));
        mWebView.clearHistory();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        WebViewClient webViewClient = new WebViewClient(this, mWebView, swipeRefreshLayout);
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

                mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomNavigationBar.clearAll();
                        initBottomNavigationView();
                    }
                }, 1000);
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
            R.id.bottom_sheet_spoon2,
            R.id.bottom_sheet_call,
            R.id.bottom_sheet_mail,
            R.id.bottom_sheet_fb,
            R.id.bottom_sheet_twitter,
            R.id.bottom_sheet_instagram
    })
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

            case R.id.bottom_sheet_call:
                //...TODO: replace with bottom hidden
                makeCall(getResources().getString(R.string.contact_no));
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            case R.id.bottom_sheet_mail:
                //...TODO: replace with bottom hidden
                sendEmail(getResources().getString(R.string.email));
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;
            case R.id.bottom_sheet_fb:
                //...TODO: replace with bottom hidden
                sendToFbPage(getResources().getString(R.string.fb_address));
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;
            case R.id.bottom_sheet_twitter:
                //...TODO: replace with bottom hidden
                sendToTwitter(getResources().getString(R.string.twitter_username));
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;
            case R.id.bottom_sheet_instagram:
                //...TODO: replace with bottom hidden
                sendToInstagram(getResources().getString(R.string.instagram_address));
                mWebView.loadUrl("https://www.uplabs.com/ashawon");
                break;

            default:
                break;

        }
    }

    private void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From " + getString(R.string.app_name) + " app | ..");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void sendToFbPage(String fbUrl) {
        Uri uri = Uri.parse(fbUrl);
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + fbUrl);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void sendToTwitter(String twiiterUsername) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twiiterUsername)));
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twiiterUsername)));
        }
    }

    private void sendToInstagram(String username) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://instagram.com/_u/" + username));
            intent.setPackage("com.instagram.android");
            startActivity(intent);
        }
        catch (android.content.ActivityNotFoundException anfe)
        {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/" + username)));
        }

    }

}
