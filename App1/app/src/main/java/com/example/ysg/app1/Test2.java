package com.example.ysg.app1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
    int wordcount;
    String word[];
    String mean[];
    TimerTask second;
    TextView time;
    Button switcher;
    Button answer;
    Button finish;
    TextView showanswer;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        Intent intent = getIntent();
        word=intent.getStringArrayExtra("word");
        mean=intent.getStringArrayExtra("mean");
        Typeface myfont1=Typeface.createFromAsset(this.getAssets(),"font/font7.ttf");
        Typeface myfont2=Typeface.createFromAsset(this.getAssets(),"font/font4.ttf");
        wordcount = word.length;
        final int check[] = new int[wordcount];
        time = (TextView) findViewById(R.id.rtimer);
        final EditText useranswer = (EditText)findViewById(R.id.ruseranswer);
        TextView text = (TextView)findViewById(R.id.textView7);
        TextView left = (TextView)findViewById(R.id.textView8);
        final TextView answercount = (TextView)findViewById(R.id.ranswercount);
        showanswer = (TextView)findViewById(R.id.rshowanswer);
        answer = (Button)findViewById(R.id.ranswer);
        switcher = (Button)findViewById(R.id.rswitcher);
        finish = (Button)findViewById(R.id.finish);
        final TextView show = (TextView)findViewById(R.id.rshow);
        answercount.setTypeface(myfont2);
        left.setTypeface(myfont2);
        show.setTypeface(myfont1);
        time.setTypeface(myfont2);
        text.setTypeface(myfont2);
        answer.setTypeface(myfont2);
        switcher.setTypeface(myfont2);
        finish.setTypeface(myfont2);
        answer.setEnabled(false);
        finish.setVisibility(View.INVISIBLE);
        finish.setEnabled(false);
        switcher.setText("시험 시작!");
        for(int j=0;j<wordcount;j++){
            check[j]=j;
        }
        answer.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switcher.setText("다음 문제");
                if(useranswer.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(),"패스는 안돼요ㅠㅠ", Toast.LENGTH_LONG).show();
                }
                else {
                    second.cancel();
                    switcher.setEnabled(true);
                    answer.setEnabled(false);
                    if (mean[ran].contains(useranswer.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "정답!", Toast.LENGTH_SHORT).show();
                        correct++;
                        answercount.setText("" + correct);
                        showanswer.setText(mean[ran]);
                    } else {
                        Toast.makeText(getApplicationContext(), "오답ㅠㅠ", Toast.LENGTH_SHORT).show();
                        showanswer.setText(mean[ran]);
                    }
                    if (count==wordcount) {
                        switcher.setEnabled(false);
                        answer.setEnabled(false);
                        finish.setVisibility(View.VISIBLE);
                        finish.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "시험 종료!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        finish.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        switcher.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switcher.setText("다음 문제");
                showanswer.setText(null);
                useranswer.setText(null);
                testStart();
                switcher.setEnabled(false);
                answer.setEnabled(true);
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
        timer_sec = 11;
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
                timer_sec--;
                time.setText(timer_sec + "초");
                if(timer_sec == 0){
                    second.cancel();
                    switcher.setEnabled(true);
                    answer.setEnabled(false);
                    showanswer.setText(mean[ran]);
                    Toast.makeText(getApplicationContext(),"오답ㅠㅠ",Toast.LENGTH_SHORT).show();
                    if (count==wordcount) {
                        switcher.setEnabled(false);
                        answer.setEnabled(false);
                        finish.setVisibility(View.VISIBLE);
                        finish.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "시험 종료!", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };
        handler.post(updater);
    }
}
