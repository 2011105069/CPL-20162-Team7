package com.example.administrator.jjikmeok;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SubActivity_03 extends AppCompatActivity {

    //public int networkType;

    private Socket socket;
    BufferedReader socket_in;
    PrintWriter socket_out;
    EditText input;
    EditText input2;
    EditText input3;
    EditText input4;
    Button button;
    TextView output;
    String data;
    String data2;
    String data3;
    String data4;

    Button btn_out;
    RadioGroup rg;

    String sex;

    int networkType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_03);


        input = (EditText) findViewById(R.id.editText_loginid); //id받은 값을 input으로
        input2 = (EditText) findViewById(R.id.editText_loginpw);
        input3 = (EditText) findViewById(R.id.editText_joinage);


        rg= (RadioGroup) findViewById(R.id.radioGroup);
        btn_out = (Button) findViewById(R.id.button6);
        btn_out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                String str_Qtype = rd.getText().toString();
                Toast.makeText(getApplicationContext(), str_Qtype+" 선택됨", Toast.LENGTH_LONG).show();
            }
        });


        button = (Button) findViewById(R.id.button6); //button-> 이건 어떤 버튼 눌렀을 때, 보낸다는 것인듯?

        Log.w("dick1", "123");

        button.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {

                Log.w("dick2", "456");

                String data = input.getText().toString(); //ID
                                                            //USER_NAME (USER)
                                                            //SEX (M/F)
                String data2 = input2.getText().toString(); //PW - NO SEND!
                String data3 = input3.getText().toString(); //AGE (1992)
                                                            //GROUP_ID (0)
                                                            // +_
                Log.w("dick", " " + data);

                //★socket part

                if(data != null) {
                    //socket_out.println("REGISTER_"+data+"_usrname_"+sex+"_"+data3+"_0_");
                    MyThread mth = new MyThread(data,sex,data3);
                    mth.start();
                }

                //send form!!
                //REGISTER_id_username_sex(M/F)_age(1992)_0_

                finish();
            }
        });


        //★socket part

    }

    //★socket part

/*
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public void onClick_01(View v)
    {


        EditText text_id = (EditText) findViewById(R.id.editText_loginid);
        EditText text_pw = (EditText) findViewById(R.id.editText_loginpw);

        String id = text_id.getText().toString();
        String pw = text_pw.getText().toString();

        Intent intent_01 = new Intent(getApplicationContext(), SubActivity_01.class);

        intent_01.putExtra("가입 아이디", id);
        intent_01.putExtra("가입 패스워드", pw);

        Toast toast_01 = Toast.makeText(this, "가입되었습니다", Toast.LENGTH_SHORT);
        toast_01.show();

        finish();
    }

    public void onClick_02(View v)
    {
        finish();
    }


    public void onClick_88(View v)
    {
        sex="M";
    }

    public void onClick_99(View v)
    {
        sex="F";
    }


    public class MyThread extends Thread implements Runnable {
        public String data;
        public String sex;
        public String data3;

        public MyThread(String st1, String str2, String st3) {
            data = st1;
            sex = str2;
            data3 = st3;
        }
        public void run() {
            try {
                socket = new Socket("192.168.0.2", 11111); //this IP is cmd's ipconfig ip

                socket_out = new PrintWriter(socket.getOutputStream(), true);

                socket_out.println("APP_");
                InputStream receiver = socket.getInputStream();
                byte[] temp = new byte[100];
                int n2 = receiver.read(temp);
                String response1 = new String(temp);
                Log.w("here response",response1);

                socket_out.println("REGISTER_"+data+"_usrname_"+sex+"_"+data3+"_0_");

                socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //FileInputStream input = new FileInputStream(f);
            Log.w("here","4");

        }

    }
}
