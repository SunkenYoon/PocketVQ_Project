package com.example.ysg.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class Vocab extends Activity {
    ListView listview ;
    ListAdapter adapter1;
    ListAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocab);
        adapter1 = new ListAdapter();
        adapter2= new ListAdapter();
        final Button hidebutton = (Button)findViewById(R.id.hidebutton);
        final Button testbutton = (Button)findViewById(R.id.testbutton);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter1);
        listview.setAdapter(adapter2);
        Excel();
        hidebutton.setOnClickListener(new Button.OnClickListener(){
            int check = 0;
            @Override
            public void onClick(View v) {
                switch(check){
                    case 0:
                        hidebutton.setText("뜻 보이기");
                        HExcel();
                        check = 1;
                        break;
                    case 1:
                        hidebutton.setText("뜻 가리기");
                        Excel();
                        check = 0;
                        break;


                }
            }
        });
        testbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Test1.class);
                startActivity(intent);


            }
        });
    }
    public void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        adapter1.clear();
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("first.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            int MaxColumn = 2, RowStart = 0, RowEnd = sheet.getColumn(MaxColumn - 1).length - 1, ColumnStart = 0, ColumnEnd = sheet.getRow(2).length - 1;
            for (int row = RowStart; row <= RowEnd; row++) {
                String excelload1 = sheet.getCell(ColumnStart, row).getContents();
                String excelload2 = sheet.getCell(ColumnEnd, row).getContents();
                adapter1.addItem(excelload1, excelload2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            listview.setAdapter(adapter1);
            workbook.close();
        }


    }
    public void HExcel() {
        Workbook workbook = null;
        Sheet sheet = null;
        adapter2.clear();
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("first.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            int MaxColumn = 2, RowStart = 0, RowEnd = sheet.getColumn(MaxColumn - 1).length - 1, ColumnStart = 0, ColumnEnd = sheet.getRow(2).length - 1;
            for (int row = RowStart; row <= RowEnd; row++) {
                String excelload1 = sheet.getCell(ColumnStart, row).getContents();
                adapter2.addItem(excelload1, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            listview.setAdapter(adapter2);
            workbook.close();
        }

    }
    }
