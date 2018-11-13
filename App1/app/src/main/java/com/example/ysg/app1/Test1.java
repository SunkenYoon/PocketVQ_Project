package com.example.ysg.app1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Test1 extends Activity {
    String word[] = new String[61];
    String mean[] = new String[61];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        final Button switcher = (Button)findViewById(R.id.switcher);
        final TextView show = (TextView)findViewById(R.id.show);
        Excel();
        switcher.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Random random = new Random();
                show.setText(word[random.nextInt(62)]);
            }
        });
    }

    public void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("first.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            int MaxColumn = 2, RowStart = 0, RowEnd = sheet.getColumn(MaxColumn - 1).length - 1, ColumnStart = 0, ColumnEnd = sheet.getRow(2).length - 1;
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