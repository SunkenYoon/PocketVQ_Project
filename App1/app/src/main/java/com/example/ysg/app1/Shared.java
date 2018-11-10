package com.example.ysg.app1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class Shared extends Activity {
    ListView listview ;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared);
        adapter = new ListAdapter() ;
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        Excel();
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
                String excelload1 = sheet.getCell(ColumnStart, row).getContents();
                String excelload2 = sheet.getCell(ColumnEnd, row).getContents();
                adapter.addItem(excelload1, excelload2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            listview.setAdapter(adapter);
            workbook.close();
        }
    }
}
