package com.example.ysg.app1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import java.util.regex.Pattern;

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
    TextView showanswer;
    Button switcher;
    Button answer;
    Button retest;
    String num;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        Typeface myfont1=Typeface.createFromAsset(this.getAssets(),"font/font7.ttf");
        Typeface myfont2=Typeface.createFromAsset(this.getAssets(),"font/font4.ttf");
        final EditText useranswer = (EditText)findViewById(R.id.useranswer);
        final TextView answercount = (TextView)findViewById(R.id.answercount);
        final TextView left = (TextView)findViewById(R.id.textView5);
        answer = (Button)findViewById(R.id.answer);
        retest = (Button)findViewById(R.id.retest);
        switcher = (Button)findViewById(R.id.switcher);
        final TextView show = (TextView)findViewById(R.id.show);
        time = (TextView) findViewById(R.id.timer);
        final TextView text = (TextView)findViewById(R.id.textView2);
        showanswer =(TextView)findViewById(R.id.showanswer);
        retest.setVisibility(View.INVISIBLE);
        answercount.setTypeface(myfont2);
        left.setTypeface(myfont2);
        retest.setTypeface(myfont2);
        show.setTypeface(myfont1);
        time.setTypeface(myfont2);
        text.setTypeface(myfont2);
        answer.setTypeface(myfont2);
        switcher.setTypeface(myfont2);
        showanswer.setTypeface(myfont2);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("문제 수를 설정하세요!");
        builder.setMessage("");
        final EditText askNum = new EditText(Test1.this);
        builder.setView(askNum);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (askNum.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "1이상의 숫자를 입력하세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    num = askNum.getText().toString();
                    if (isNumber(num) == false) {
                        Toast.makeText(getApplicationContext(), "1이상의 숫자를 입력하세요.", Toast.LENGTH_LONG).show();
                        finish();
                    } else if (Integer.parseInt(num) > 0)
                        dialog.dismiss();
                    else {
                        Toast.makeText(getApplicationContext(), "1이상의 숫자를 입력하세요.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

        });
        builder.show();
        retest.setEnabled(false);
        answer.setEnabled(false);
        switcher.setText("시험 시작!");
        Intent intent = getIntent();
        day =intent.getStringExtra("day");
        Excel();

        for(int j=0;j<40;j++){
            check[j]=j;
        }
        answer.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switcher.setText("다음 문제");
                if(useranswer.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(),"패스는 안돼요ㅠㅠ", Toast.LENGTH_SHORT).show();
                }
                else{
                    second.cancel();
                    switcher.setEnabled(true);
                    answer.setEnabled(false);
                     if(mean[ran].contains(useranswer.getText().toString())){
                         Toast.makeText(getApplicationContext(),"정답!",Toast.LENGTH_SHORT).show();
                         correct++;
                         answercount.setText(""+correct);
                         showanswer.setText(mean[ran]);
                     }
                     else
                     {
                         Toast.makeText(getApplicationContext(),"오답ㅠㅠ",Toast.LENGTH_SHORT).show();
                         rword.add(word[ran]);
                         rmean.add(mean[ran]);
                         showanswer.setText(mean[ran]);
                     }
                    if (count == Integer.parseInt(num)) {
                        retest.setEnabled(true);
                        retest.setVisibility(View.VISIBLE);
                        switcher.setEnabled(false);
                        answer.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"테스트 종료!",Toast.LENGTH_LONG).show();
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
                switcher.setText("다음 문제");
                useranswer.setText(null);
                showanswer.setText(null);
                testStart();
                switcher.setEnabled(false);
                answer.setEnabled(true);
                Random random = new Random();
                do {
                    ran = random.nextInt(40);
                    show.setText(word[ran]);
                } while (check[ran] == 100);
                count++;
                check[ran] = 100;
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
                    Toast.makeText(getApplicationContext(),"오답ㅠㅠ",Toast.LENGTH_SHORT).show();
                    rword.add(word[ran]);
                    rmean.add(mean[ran]);
                    showanswer.setText(mean[ran]);
                    if (count == Integer.parseInt(num)) {
                        retest.setEnabled(true);
                        retest.setVisibility(View.VISIBLE);
                        switcher.setEnabled(false);
                        answer.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"테스트 종료!",Toast.LENGTH_LONG).show();
                    }
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
    public static boolean isNumber(String str) {
        boolean check = true;
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))) {
                check = false;
                break;
            }
        }
        return check;
    }
}