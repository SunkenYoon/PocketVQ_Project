package com.example.ysg.app1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtFirst;
    ImageView profile;
    String name;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        profile = (ImageView)findViewById(R.id.profile);
        profile.setImageResource(R.drawable.defaults);
        profile.setOnClickListener(profileListener);
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
        txtFirst.setText(pref.getString("name","noName"));
        String image =  pref.getString("image", "");
        Bitmap bitmap = StringToBitMap(image);
        profile.setImageBitmap(bitmap);


    }
    Button.OnClickListener profileListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
        }
    };

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
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    @Override public void onBackPressed() {
//TODO: 한번 누르면 안내, 두번 누르면 종료/
    }


}
