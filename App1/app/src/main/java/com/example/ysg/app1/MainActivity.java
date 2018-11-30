package com.example.ysg.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButtonClick(View view){
        Button button = (Button) findViewById(R.id.button);
        switch(view.getId()){
            case R.id.button:
                Intent intent = new Intent(getApplicationContext(), VocabList.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(getApplicationContext(), Shared.class);
                startActivity(intent2);
                break;
            case R.id.button3:
                Intent intent3 = new Intent(getApplicationContext(), Dic.class);
                startActivity(intent3);
                break;
        }
    }
}
