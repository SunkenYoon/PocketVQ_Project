package com.example.ysg.app1;

import android.app.Activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.TextView;


public class Loading extends Activity{
    TextView mytv;
    Typeface myfont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        mytv=(TextView)findViewById(R.id.textView);
        myfont=Typeface.createFromAsset(this.getAssets(),"font/font.ttf");
        mytv.setTypeface(myfont);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}

