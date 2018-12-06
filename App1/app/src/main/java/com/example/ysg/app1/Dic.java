package com.example.ysg.app1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Dic extends AppCompatActivity {
    String a,b,c,d,usertext;
    TextView show;
    EditText get;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dic);
        show = (TextView)findViewById(R.id.translated_text);
        get = (EditText)findViewById(R.id.get_text);
        submit = (Button)findViewById(R.id.translateButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertext = get.getText().toString();
                dicThread thread = new dicThread();
                thread.start();
            }
        });
    }

private class dicThread extends Thread{
        private static final String TAG = "dicThread";
        public dicThread(){

    }
    @Override
    public void run() {
        super.run();
        String clientId = "jUT91XLvYsQNRGchtXzn";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "20GjrRJYdx";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(usertext, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=en&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            a=response.toString();
            JSONObject object = new JSONObject(a);
            b=object.getString("message");
            object = new JSONObject(b);
            c=object.getString("result");
            object = new JSONObject(c);
            d = object.getString("translatedText");

        } catch (Exception e) {
            System.out.println(e);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show.setText(d);
            }
        });
    }
}
}

