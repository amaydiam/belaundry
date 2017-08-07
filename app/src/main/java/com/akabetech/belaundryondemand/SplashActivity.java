package com.akabetech.belaundryondemand;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.animation.Animation;
import com.akabetech.belaundryondemand.core.UIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_LOCATION = 1001;
    @BindView(R.id.beasli_logo_imv)
    ImageView beasliImv;

    //direct object runnable
    private Runnable splashOutTask = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private Runnable playAnimationTask = new Runnable() {
        @Override
        public void run() {
            playAnimation();
        }
    };
    private Runnable forceExit = new Runnable() {
        @Override
        public void run() {
            SplashActivity.this.finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(splashOutTask, UIConstants.SPLASH_TIME_OUT);
                beasliImv.post(playAnimationTask);
            } else {
                Toast.makeText(this, "We need access to your location, this app will now close.",
                        Toast.LENGTH_LONG).show();

                new Handler().postDelayed(forceExit, UIConstants.SPLASH_TIME_OUT);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_PERMISSION_LOCATION);
        } else {
            new Handler().postDelayed(splashOutTask, UIConstants.SPLASH_TIME_OUT);
            beasliImv.post(playAnimationTask);
        }

    }


    private void playAnimation() {

        Animator animator = Animation.showDelayedAnimation(beasliImv);
        if (animator != null) {
            animator.start();
        }
    }
}
