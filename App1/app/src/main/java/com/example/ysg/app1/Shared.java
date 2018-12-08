package com.example.ysg.app1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class Shared extends AppCompatActivity {
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";
    String user;
    String userName;
    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";
    // Creating button.
    Button UploadButton;
    Button userlist;

    // Creating EditText.
    EditText userWordE;
    EditText userMeanE;
    String userWord;
    String userMean;
    String suserimage;
    Bitmap userimage;

    // Creating URI.
    Uri FilePathUri;
    String user_uri;
    SharedPreferences pref;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared);
        pref = getSharedPreferences("IsFirst", 0);
        Typeface myfont=Typeface.createFromAsset(this.getAssets(),"font/font4.ttf");
        UploadButton = (Button) findViewById(R.id.ButtonUploadImage);
        userlist = (Button)findViewById(R.id.userlist);
        storageReference = FirebaseStorage.getInstance().getReference();
        userWordE = (EditText) findViewById(R.id.user_word);
        userMeanE =  (EditText)findViewById(R.id.user_mean);
        TextView text = (TextView)findViewById(R.id.textview);
        text.setTypeface(myfont);
        UploadButton.setTypeface(myfont);
        userlist.setTypeface(myfont);
        userWordE.setTypeface(myfont);
        userMeanE.setTypeface(myfont);
        userName= pref.getString("name","noName");
        suserimage=pref.getString("image","");
        userimage= StringToBitMap(suserimage);
        FilePathUri = getImageUri(this,userimage);
        user_uri=FilePathUri.toString();
        progressDialog = new ProgressDialog(Shared.this);

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageFileToFirebaseStorage();
            }
        });
        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SharedList.class);
                startActivity(intent);
            }
        });
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {
        userWord = userWordE.getText().toString();
        userMean = userMeanE.getText().toString();
        User user = new User(userName,userWord,userMean,user_uri);
        progressDialog.setTitle("Image is Uploading...");
        // Showing progressDialog.
        progressDialog.show();
        progressDialog.dismiss();
        databaseReference.child(userWord).setValue(user);
        StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
        storageReference2nd.putFile(FilePathUri);
        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

    }
    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
    public static class User {
        public String userName;
        public String word;
        public String mean;
        public String url;

        public User(){

        }
        public User(String userName,String word, String mean, String url){
            this.userName=userName;
            this.word=word;
            this.mean=mean;
            this.url=url;
        }
        public String getUserName(){
            return userName;
        }
        public String getWord(){
            return word;
        }
        public String getMean(){
            return mean;
        }
        public String getUrl(){
            return url;
        }

    }



}