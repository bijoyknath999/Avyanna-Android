package com.dev.avyanna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dev.avyanna.R;
import com.dev.avyanna.utils.AppUtils;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500;
    private Context mContext;
    private RelativeLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mContext = getApplicationContext();

        rootLayout = (RelativeLayout) findViewById(R.id.spalshScreen);


        //For Full SCreen
        if (Build.VERSION.SDK_INT>15)
        {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);

        }
    }


    //Delay Class
    private void initFunctionality() {
        if (AppUtils.isNetworkAvailable(mContext)) {
            rootLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext, UserSelection.class);
                    startActivity(intent);
                    finish();

                }
            }, SPLASH_DURATION);
        } else {
            //Internet Check
            AppUtils.noInternetWarning(rootLayout, mContext);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();
    }
}