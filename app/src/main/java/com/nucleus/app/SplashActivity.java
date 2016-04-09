package com.nucleus.app;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    //Splash Timer
    private static int SPLASH_TIME_OUT = 3000;
    private ImageView image;
    private TransitionDrawable trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For creating a full-screen activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        image = (ImageView) findViewById(R.id.image);
        Resources res = this.getResources();
        trans = (TransitionDrawable) res.getDrawable(R.drawable.transition);
        image.setImageDrawable(trans);
        trans.reverseTransition(3000);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
