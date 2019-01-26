package com.appkwan.webdroidwebapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Calendar;

public class WebViewClient {

    Context context;
    WebView mwebView;

    WebViewClient(Context context, WebView mwebview){
        this.context = context;
        mwebView = mwebview;
    }

    public void improveWebViewPerformance(final ProgressBar mLoadingSpinner){
        mwebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest (final WebView view, String url) {
                if (url.contains(".css")) {
                    return getCssWebResourceResponseFromAsset();
                } else {
                    return super.shouldInterceptRequest(view, url);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(url));
                    context.startActivity(intent);
                    return true;
                }if (url.startsWith("mailto:")) {
                    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                    context.startActivity(i);
                    return  true;
                }
                if(url.contains(context.getString(R.string.your_website_url))) {
                    view.loadUrl(url);
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(i);
                    return true;
                }


                if(url.contains("https://www.google.com/maps/"))
                {
                    Uri IntentUri = Uri.parse(url);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(mapIntent);
                    }
                    return true;
                }

                if(url.startsWith("https://www.google.com/calendar")){

                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("allDay", false);
                    intent.putExtra("rrule", "FREQ=YEARLY");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                    view.loadUrl(url);
                    return true;
                }


                return false;
            }

            @SuppressWarnings("deprecation")
            private WebResourceResponse getCssWebResourceResponseFromString() {
                return getUtf8EncodedCssWebResourceResponse(new StringBufferInputStream("body { background-color: #F781F3; }"));
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return getUtf8EncodedCssWebResourceResponse(context.getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
                return new WebResourceResponse("text/css", "UTF-8", data);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                mLoadingSpinner.setVisibility(View.VISIBLE);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                mLoadingSpinner.setVisibility(View.GONE);
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                //  Log.e(TAG," Error occure while loading the web page at Url"+ failingUrl+"." +description);
                view.loadUrl("about:blank");
                showInternetConnectionOutAlertDialog();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

    }

    private void showInternetConnectionOutAlertDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_network_error);
        dialog.show();
    }

}
