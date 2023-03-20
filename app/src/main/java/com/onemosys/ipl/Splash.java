package com.onemosys.ipl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import com.onemosys.ipl.Helper.isUserExists;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(getColor(R.color.background));

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isUserExists.isUserExists(Splash.this)){
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    intent.putExtra("openFragment", "onBoarding");
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else{
                    Intent intent = new Intent(Splash.this, Home.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }
        }, 800);

    }

    @Override
    public void onBackPressed() {

    }
}