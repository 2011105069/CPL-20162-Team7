package com.example.administrator.jjikmeok;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.github.mikephil.charting.utils.MPPointD;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = Environment.getExternalStorageDirectory().getPath();
        Log.w("here",path);
        MyThread mth = new MyThread();
        mth.start();
    }

    public void onClick_01(View v)
    {
        Intent intent_01 = new Intent(getApplicationContext(), SubActivity_01.class);
        startActivity(intent_01);
    }


    public class MyThread extends Thread implements Runnable {

        public MyThread() {

        }
        public void run() {
            try {
                Log.w("here","0");
                Socket socket = new Socket("192.168.0.2", 11111); //this IP is cmd's ipconfig ip
                Log.w("here","1");
                PrintWriter socket_out = new PrintWriter(socket.getOutputStream(), true);

                socket_out.println("APP_");

                InputStream receiver = socket.getInputStream();
                byte[] temp = new byte[100];
                int n2 = receiver.read(temp);
                String response1 = new String(temp);
                Log.w("here response",response1);

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
