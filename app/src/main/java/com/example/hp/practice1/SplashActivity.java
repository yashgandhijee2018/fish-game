package com.example.hp.practice1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ekalips.fancybuttonproj.FancyButton;

public class SplashActivity extends AppCompatActivity {
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void fun(View view)
    {
        Intent i=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(i);
    }
}
