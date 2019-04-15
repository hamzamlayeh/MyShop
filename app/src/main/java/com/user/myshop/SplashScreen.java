package com.user.myshop;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.user.myshop.Utils.Constants;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    Animation animFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.translate);
        logo.setAnimation(animFadeIn);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }
}
