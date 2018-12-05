package com.example.ysg.apitest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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


import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";
    String user;
    String userName;
    String image;
    Bitmap userImage;
    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";
    // Creating button.
    Button UploadButton;

    // Creating EditText.
    EditText userWordE;
    EditText userMeanE;
    String userWord;
    String userMean;

    // Creating URI.
    Uri FilePathUri;
    String user_uri;
    SharedPreferences pref;

    // Creating StorageReference and DatabaseReference object.
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("IsFirst", 0);
        UploadButton = (Button) findViewById(R.id.button);
        userWordE = (EditText) findViewById(R.id.edittext);
        progressDialog = new ProgressDialog(MainActivity.this);

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageFileToFirebaseStorage();
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
        progressDialog.setTitle("Image is Uploading...");
        // Showing progressDialog.
        progressDialog.show();
        progressDialog.dismiss();
        databaseReference.child(userWordE.getText().toString()).setValue(userWordE.getText().toString());
        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

    }
}