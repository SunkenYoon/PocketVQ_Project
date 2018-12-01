package com.example.ysg.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.os.Handler;
import android.widget.Toast;

public class Test1 extends Activity {
    int ran=0;
    int count=0;
    int correct=0;
    int timer_sec=0;
    String day;
    int check[] = new int[40];
    String word[] = new String[40];
    String mean[] = new String[40];
    List<String> rword = new ArrayList<String>();
    List<String> rmean = new ArrayList<String>();
    TimerTask second;
    TextView time;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        final EditText useranswer = (EditText)findViewById(R.id.useranswer);
        final TextView answercount = (TextView)findViewById(R.id.answercount);
        final Button answer = (Button)findViewById(R.id.answer);
        final Button retest = (Button)findViewById(R.id.retest);
        final Button switcher = (Button)findViewById(R.id.switcher);
        final TextView result = (TextView)findViewById(R.id.result);
        final TextView show = (TextView)findViewById(R.id.show);
        retest.setEnabled(false);
        Intent intent = getIntent();
        day =intent.getStringExtra("day");
        Excel();


        for(int j=0;j<40;j++){
            check[j]=j;
        }
        answer.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(useranswer.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(),"패스는 안돼요ㅠㅠ", Toast.LENGTH_SHORT).show();
                }
                else{
                    second.cancel();
                    switcher.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"정답!",Toast.LENGTH_SHORT).show();
                     if(mean[ran].contains(useranswer.getText().toString())){
                         correct++;
                         answercount.setText(""+correct);
                     }
                     else
                     {
                         Toast.makeText(getApplicationContext(),"오답ㅠㅠ",Toast.LENGTH_SHORT).show();
                         rword.add(word[ran]);
                         rmean.add(mean[ran]);
                     }
            }
            }
        });
        retest.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Test2.class);
                int length = rword.size();
                String rrword[]=new String[length];
                String rrmean[]=new String[length];
                for(int i=0;i<length;i++){
                    rrword[i]=rword.get(i);
                    rrmean[i]=rmean.get(i);
                }
                intent.putExtra("word",rrword);
                intent.putExtra("mean",rrmean);
                startActivity(intent);
            }
        });
        switcher.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                useranswer.setText(null);
                testStart();
                switcher.setEnabled(false);
                Random random = new Random();
                do {
                    ran = random.nextInt(40);
                    show.setText(word[ran]);
                } while (check[ran] == 100);
                count++;
                check[ran] = 100;
                if (count == 5) {
                    retest.setEnabled(true);
                    switcher.setEnabled(false);
                    result.setText("테스트 종료!");
                }
            }
        });
        }
    public void testStart() {
        time = (TextView) findViewById(R.id.timer);
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
    public void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open(day);
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            int MaxColumn = 2, RowStart = 0, RowEnd = sheet.getColumn(MaxColumn - 1).length-1, ColumnStart = 0, ColumnEnd = sheet.getRow(2).length-1;
            for (int row = RowStart; row <= RowEnd; row++) {
                word[row] = sheet.getCell(ColumnStart, row).getContents();
                mean[row] = sheet.getCell(ColumnEnd, row).getContents();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

}