package com.example.ysg.app1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView profile;
    TextView username;
    Button one;
    Button two;
    Button three;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Typeface myfont;
    private BackPressCloseHandler backPressCloseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        profile = (ImageView)findViewById(R.id.profile);
        username = (TextView)findViewById(R.id.username);
        TextView sheet = (TextView)findViewById(R.id.textView4);
        one = (Button)findViewById(R.id.button);
        two = (Button)findViewById(R.id.button2);
        three = (Button)findViewById(R.id.button3);
        profile.setImageResource(R.drawable.defaults);
        profile.setOnClickListener(profileListener);
        pref = getSharedPreferences("IsFirst" , 0);
        myfont=Typeface.createFromAsset(this.getAssets(),"font/font4.ttf");
        username.setTypeface(myfont);
        sheet.setTypeface(myfont);
        one.setTypeface(myfont);
        two.setTypeface(myfont);
        three.setTypeface(myfont);
        username.setText(pref.getString("name","")+"의 ");
        editor = pref.edit();
        if(pref.getString("image","")==""){
            two.setEnabled(false);
        }
        else
            two.setEnabled(true);
        backPressCloseHandler = new BackPressCloseHandler(this);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VocabList.class);
                startActivity(intent);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), Shared.class);
                startActivity(intent2);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), Dic.class);
                startActivity(intent3);
            }
        });
        boolean isFirst = pref.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            editor.putBoolean("isFirst", true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("닉네임을 설정하세요!");
            builder.setMessage("프로필을 설정해야 공유 단어장을 이용할 수 있습니다.");
            final EditText namevalue = new EditText(MainActivity.this);
            builder.setView(namevalue);
            builder.setPositiveButton("제출", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString("name", namevalue.getText().toString());
                    username.setText(namevalue.getText().toString()+"의 ");
                    dialog.dismiss();
                    editor.commit();
                }
            });
            builder.show();
        }
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
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }
    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;
        private Activity activity;
        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }
            public void onBackPressed() {
                 if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                 backKeyPressedTime = System.currentTimeMillis(); showGuide();
                 return;
            }
                 if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                     ActivityCompat.finishAffinity(activity); toast.cancel();
                 }
              }
    public void showGuide() {
        toast = Toast.makeText(activity, "뒤로가기를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT); toast.show();
         }
    }




}
