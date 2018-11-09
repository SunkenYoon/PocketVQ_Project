package com.example.ysg.app1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import java.util.List;


public class Shared extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared);
        ListView listview ;
        ListAdapter adapter;
        adapter = new ListAdapter() ;
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        adapter.addItem(
                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(
                "Ind", "Assignment Ind Black 36dp") ;
    }
}
