package com.example.ysg.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Profile extends AppCompatActivity {
    private Uri mImageCaptureUri;
    private String absolutePath;
    ImageView user_image;
    Bitmap photo;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        user_image = (ImageView)findViewById(R.id.user_image);
        Button camera = (Button)findViewById(R.id.camera);
        Button default_pic = (Button)findViewById(R.id.default_pic);
        Button gallery_pic = (Button)findViewById(R.id.gallery_pic);
        Button submit = (Button)findViewById(R.id.pic_submit);
        pref = getSharedPreferences("IsFirst" , 0);
        editor = pref.edit();
        String image =  pref.getString("image", "");
        Bitmap bitmap = StringToBitMap(image);
        user_image.setImageBitmap(bitmap);
        camera.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                File file = new File(Environment.getExternalStorageDirectory(),url);
                mImageCaptureUri = FileProvider.getUriForFile(getApplicationContext(),  "com.example.ysg.app1.provider",file);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivityForResult(intent, 0);
            }
        });
        default_pic.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        gallery_pic.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,1);
            }
        });
        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                String image = BitMapToString(photo);
                editor.putString("image",image.toString());
                editor.commit();
                startActivity(intent);
            }
        });

    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode!=RESULT_OK)
            return;
        switch(requestCode){
            case 1:
                mImageCaptureUri = data.getData();
            case 0:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");
                intent.putExtra("outputX",150);
                intent.putExtra("outputY",150);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,2);
                break;
            case 2:
                if(resultCode!=RESULT_OK)
                    return;
                final Bundle extras = data.getExtras();
                String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/app1"+System.currentTimeMillis()+".jpg";
                if(extras!=null){
                    photo = extras.getParcelable("data");
                    user_image.setImageBitmap(photo);
                    storeCropImage(photo,filePath);
                    break;
                }
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                    f.delete();

        }
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
