package com.example.ysg.app1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class VocabList extends Activity {
    TextView text;
    ListAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String list[] = new String[20];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocablist);
        Typeface myfont=Typeface.createFromAsset(this.getAssets(),"font/font4.ttf");
        adapter = new ListAdapter1();
        text = (TextView)findViewById(R.id.text1);
        ListView vocablist = (ListView)findViewById(R.id.vocablist);
        text.setTypeface(myfont);
        for(int i=0;i<20;i++){
            list[i]="Day"+(i+1);
            adapter.addItem(list[i]);
        }
        vocablist.setAdapter(adapter);
        vocablist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Vocab.class);
                intent.putExtra("day",list[position]);
                startActivity(intent);
            }
        });
    }
}
