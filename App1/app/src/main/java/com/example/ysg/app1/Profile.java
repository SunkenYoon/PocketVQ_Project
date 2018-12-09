package com.example.ysg.app1;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.graphics.Bitmap;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Profile extends AppCompatActivity {
    private Uri mImageCaptureUri;
    private String absolutePath;
    ImageView user_image;
    Bitmap bitmap;
    Bitmap bitmaps;
    Bitmap photo;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Uri FilePathUri;
    Typeface myfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        user_image = (ImageView)findViewById(R.id.user_image);
        Button changeName = (Button)findViewById(R.id.changeName);
        Button gallery_pic = (Button)findViewById(R.id.gallery_pic);
        Button submit = (Button)findViewById(R.id.pic_submit);
        TextView profileword = (TextView)findViewById(R.id.profileword);
        user_image.setImageResource(R.drawable.defaults);
        myfont=Typeface.createFromAsset(this.getAssets(),"font/font4.ttf");
        submit.setTypeface(myfont);
        profileword.setTypeface(myfont);
        pref = getSharedPreferences("IsFirst" , 0);
        editor = pref.edit();
        int permissonCheck= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        String image =  pref.getString("image", "");
        final Bitmap bitmap = StringToBitMap(image);
        user_image.setImageBitmap(bitmap);
        if(permissonCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "권한을 승인해주세요.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
        changeName.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("닉네임을 설정하세요!");
                builder.setMessage("ㅇㅇ");
                final EditText namevalue = new EditText(Profile.this);
                builder.setView(namevalue);
                builder.setPositiveButton("제출", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("name", namevalue.getText().toString());
                        dialog.dismiss();
                        editor.commit();
                    }
                });
                builder.show();

            }
        });
        gallery_pic.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), 7);
            }
        });
        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(photo==null){
                    finish();
                }
                else {
                    String image = BitMapToString(photo);
                    editor.putString("image", image.toString());
                    editor.putString("uri", FilePathUri.toString());
                    editor.commit();
                    System.exit(0);

                }
            }
        });

    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                bitmaps = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(FilePathUri, "image/*");
                cropIntent.putExtra("crop", "true");
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("outputX", 150);
                cropIntent.putExtra("outputY", 150);
                cropIntent.putExtra("return-data", true);
                startActivityForResult(cropIntent, 2);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == 2 ){
            final Bundle extras = data.getExtras();
            String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/app1"+System.currentTimeMillis()+".jpg";
            if(extras!=null){
                photo = extras.getParcelable("data");
                user_image.setImageBitmap(photo);
                storeCropImage(photo,filePath);
            }
            File f = new File(FilePathUri.getPath());
            if(f.exists())
                f.delete();

        }
        else
            Toast.makeText(this,"취소되었습니다.",Toast.LENGTH_SHORT);
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
    private void storeCropImage(Bitmap bitmap,String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/app1";
        File directory = new File(dirPath);
        if(!directory.exists())
            directory.mkdir();
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;
        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
