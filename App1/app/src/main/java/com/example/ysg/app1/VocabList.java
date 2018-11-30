package com.example.ysg.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VocabList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String list[] = new String[20];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocablist);
        for(int i=0;i<20;i++){
            list[i]="Day"+(i+1);
        }
        ListView vocablist = (ListView)findViewById(R.id.vocablist);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        vocablist.setAdapter(adapter);
        vocablist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String day = (String)parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(),Vocab.class);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });
    }
}
