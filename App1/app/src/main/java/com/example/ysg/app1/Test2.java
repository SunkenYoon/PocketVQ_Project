package com.example.ysg.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.widget.Toast;

public class Test2 extends Activity {
    int ran=0;
    int count=0;
    int correct=0;
    int timer_sec=0;
    TimerTask second;
    TextView time;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        Intent intent = getIntent();
        final String word[]=intent.getStringArrayExtra("word");
        final String mean[]=intent.getStringArrayExtra("mean");
        final int wordcount = word.length;
        final int check[] = new int[wordcount];
        final EditText useranswer = (EditText)findViewById(R.id.ruseranswer);
        final TextView answercount = (TextView)findViewById(R.id.ranswercount);
        final Button answer = (Button)findViewById(R.id.ranswer);
        final Button switcher = (Button)findViewById(R.id.rswitcher);
        final TextView show = (TextView)findViewById(R.id.rshow);

        for(int j=0;j<wordcount;j++){
            check[j]=j;
        }
        answer.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(useranswer.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(),"패스는 안돼요ㅠㅠ", Toast.LENGTH_LONG).show();
                }
                else{
                    second.cancel();
                    switcher.setEnabled(true);
                    if(mean[ran].contains(useranswer.getText().toString())){
                        correct++;
                        answercount.setText(""+correct);
                    }
                    if(count==word.length)
                        switcher.setEnabled(false);
                }
            }
        });
        switcher.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                useranswer.setText(null);
                testStart();
                switcher.setEnabled(false);
                Random random = new Random();
                do{
                    ran = random.nextInt(wordcount);
                    show.setText(word[ran]);}while(check[ran]==100);
                count++;
                check[ran]=100;
            }
        });
    }
    public void testStart() {
        time = (TextView) findViewById(R.id.rtimer);
        timer_sec = 10;
        second = new TimerTask() {
            @Override
            public void run() {
                Update();
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 1000);
    }
    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                time.setText(timer_sec + "초");
                timer_sec--;
                if(timer_sec == 0){
                    second.cancel();
                }
            }
        };
        handler.post(updater);
    }
}
