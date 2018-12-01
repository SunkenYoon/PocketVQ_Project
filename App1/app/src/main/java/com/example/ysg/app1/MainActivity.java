package com.example.ysg.app1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtFirst;
    String name;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtFirst = (TextView)findViewById(R.id.txtFirst);
        pref = getSharedPreferences("IsFirst" , 0);
        editor = pref.edit();
        boolean isFirst = pref.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            editor.putBoolean("isFirst", true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("제목");
            builder.setMessage("정해");
            final EditText namevalue = new EditText(MainActivity.this);
            builder.setView(namevalue);
            builder.setPositiveButton("제출", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString("name", namevalue.getText().toString());
                    dialog.dismiss();
                    name = namevalue.getText().toString();
                    editor.commit();
                }
            });
            builder.show();
        }
        txtFirst.setText(pref.getString("name","noname"));

    }

    public void onButtonClick(View view){
        Button button = (Button) findViewById(R.id.button);
        switch(view.getId()){
            case R.id.button:
                Intent intent = new Intent(getApplicationContext(), VocabList.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(getApplicationContext(), Shared.class);
                startActivity(intent2);
                break;
            case R.id.button3:
                Intent intent3 = new Intent(getApplicationContext(), Dic.class);
                startActivity(intent3);
                break;
        }
    }
}
