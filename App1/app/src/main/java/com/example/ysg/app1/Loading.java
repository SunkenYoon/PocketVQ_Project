package com.example.ysg.app1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;


public class Loading extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}

