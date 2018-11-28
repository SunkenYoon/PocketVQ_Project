package com.example.ysg.app1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.os.Handler;
import android.widget.Toast;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;

public class Test1 extends Activity {
    int ran=0;
    int count=0;
    int correct=0;
    int timer_sec=0;
    int check[] = new int[61];
    String word[] = new String[61];
    String mean[] = new String[61];
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
        final Button switcher = (Button)findViewById(R.id.switcher);
        final TextView show = (TextView)findViewById(R.id.show);
        Excel();


        for(int j=0;j<61;j++){
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
                    ran = random.nextInt(61);
                    show.setText(word[ran]);}while(check[ran]==100);
                count++;
                check[ran]=100;
                if(count==20)
                    show.setTextColor(RED);
            }
        });
        }
    public void testStart() {
        time = (TextView) findViewById(R.id.timer);
        timer_sec = 10;
        count = 0;
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
            InputStream inputStream = getBaseContext().getResources().getAssets().open("first.xls");
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