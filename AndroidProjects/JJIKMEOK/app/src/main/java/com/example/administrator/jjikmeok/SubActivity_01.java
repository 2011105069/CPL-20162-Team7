package com.example.administrator.jjikmeok;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static android.R.attr.id;


public class SubActivity_01 extends AppCompatActivity {

    public String user_id;
    public String user_pw;

    private Socket socket;
    public BufferedReader socket_in;
    public PrintWriter socket_out;


    EditText input;
    EditText input2;
    String data;
    String data2;

    TextView output;

    int networkType;
    public Thread worker;
    public MyThread mth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_01);

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected() || mobile.isConnected()) {
            Log.w("here" , "network 연결 되었습니다.");
            networkType = 0;
            setContentView(R.layout.activity_sub_01);
        } else {
            Log.w("here" , "network 연결을 다시 한번 확인해주세요");
            networkType = 1;
            Log.w("here","networkType is "+networkType);
        }

    }

    //★socket part
/*
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void onClick_login(View v)
    {
        EditText text_id = (EditText) findViewById(R.id.editText_loginid);
        EditText text_pw = (EditText) findViewById(R.id.editText_loginpw);

        user_id = text_id.getText().toString();
        user_pw = text_pw.getText().toString();

        if(networkType == 0) {
            if (user_id != null) {
                mth = new MyThread(user_id);
                mth.start();
                Log.w("here", "trying println");
            }
        }
        Log.w("here", "id sending done");

        Intent intent_01 = new Intent(getApplicationContext(), SubActivity_02.class);

        intent_01.putExtra("USER_ID", user_id);
        intent_01.putExtra("USER_PW", user_pw);

        startActivity(intent_01);

    }

    public void onClick_03(View v)
    {
        Intent intent_03 = new Intent(getApplicationContext(), SubActivity_03.class);
        startActivity(intent_03);
    }


    public class MyThread extends Thread implements Runnable {
        public String uid;

        public MyThread(String usid) {
            uid=usid;
        }
        public void run() {
            try {
                Log.w("here","0");
                socket = new Socket("192.168.0.2", 11111); //this IP is cmd's ipconfig ip
                Log.w("here","1");

                socket_out = new PrintWriter(socket.getOutputStream(), true);

                socket_out.println("APP_");
                InputStream receiver = socket.getInputStream();
                byte[] temp = new byte[100];
                int n2 = receiver.read(temp);
                String response1 = new String(temp);
                Log.w("here response",response1);


                socket_out.println("LOGIN_"+user_id+"_");
                Log.w("here","2");
                //socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.w("here","3");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.w("here","4");
            }
            //FileInputStream input = new FileInputStream(f);
            Log.w("here","4");

        }

    }

}
