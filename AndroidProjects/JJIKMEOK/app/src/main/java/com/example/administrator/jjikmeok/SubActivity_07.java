package com.example.administrator.jjikmeok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SubActivity_07 extends AppCompatActivity {

    public String user_id;
    public String user_pw;

    private Socket socket;
    BufferedReader socket_in;
    PrintWriter socket_out;
    public String socket_data;
    TextView output;
    public String[] cc;

    public String temp_socket_data;


    // this activity truly rely on connection to server.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_07);

        Intent intent_01 = getIntent();

        user_id = intent_01.getStringExtra("USER_ID");
        user_pw = intent_01.getStringExtra("USER_PW");


        MyThread mth = new MyThread();
        mth.start();;

        synchronized(mth){
            try{
                System.out.println("Waiting for b to complete...");
                mth.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            cc = mth.getValue();

        }
        Log.w("here", "111111111111");

        HorizontalBarChart bchart = (HorizontalBarChart) findViewById(R.id.chart1);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        Double[] nut = new Double[9];
        int i;
        for(i=0; i<9;i++) {
            nut[i]=0.0;
        }

        String[] tmp = new String[9];
        Log.w("here","2222222222222222222");

        int a=0, b=0;

        // 84 is 84 meals in a month 3*28.
        for(a=0; a<84; a++) {
            tmp = cc[a].split("_");
//            Log.w("here",String.valueOf(tmp[0]));
//            Log.w("here",String.valueOf(tmp[1]));
//            Log.w("here",String.valueOf(tmp[2]));
//            Log.w("here",String.valueOf(tmp[3]));
//            Log.w("here",String.valueOf(tmp[4]));
//            Log.w("here",String.valueOf(tmp[5]));
//            Log.w("here",String.valueOf(tmp[6]));
//            Log.w("here",String.valueOf(tmp[7]));
//            Log.w("here",String.valueOf(tmp[8]));
            //Log.w("here ",String.valueOf(Double.parseDouble(tmp[0])));
            for(b=0; b<9;b++) {
                //Log.w("here ",String.valueOf(Double.parseDouble(tmp[b])));
                nut[b] += Double.parseDouble(tmp[b]);
            }
        }
        Log.w("here", "333333333333333333333");
        for(b=0; b<9;b++) {
            nut[b]=nut[b]/86;
        }

        for(b=0; b<9;b++) {
            Log.w("here",String.valueOf(nut[b]));
        }

        Log.w("here", "44444444444444444444444");


        // this data is average amount of required nutrition for a day with a man 20's
        double[] aa = new double[9];
        aa[0]=2600;
        aa[1]=455.0;
        aa[2]=455.0;
        aa[3]=72.0;
        aa[4]=25.0;
        aa[5]=100.0;
        aa[6]=750.0;
        aa[7]=1500.0;
        aa[8]=10.0;

        // showing percentage of nutrition information.
        for(b=0; b<9;b++) {
            aa[b]=(nut[b]/aa[b]) *100;
            //Log.w("here",String.valueOf(aa[b]));
        }

        for (i = (int) 0; i < 9; i++) {
            //float val = (float) (Math.random());
            float val =(float) aa[8-i];
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals1,"");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        bchart.setTouchEnabled(false);
        bchart.setData(data);
    }
    public void onClick_11(View v)
    {
        Intent intent_01 = new Intent(getApplicationContext(), SubActivity_05.class);

        intent_01.putExtra("USER_ID", user_id);
        intent_01.putExtra("USER_PW", user_pw);


        startActivity(intent_01);

    }

    public void onClick_12(View v)
    {
        Intent intent_02 = new Intent(getApplicationContext(), SubActivity_06.class);

        intent_02.putExtra("USER_ID", user_id);
        intent_02.putExtra("USER_PW", user_pw);


        startActivity(intent_02);

    }

    public class MyThread extends Thread implements Runnable {
        public String[] xx;

        public void run() { synchronized (this) {
            try {
                socket = new Socket("192.168.0.2", 11111); //this IP is cmd's ipconfig ip

                socket_out = new PrintWriter(socket.getOutputStream(), true);

                socket_out.println("APP_");
                InputStream receiver = socket.getInputStream();
                byte[] temp = new byte[100];
                int n2 = receiver.read(temp);
                String response1 = new String(temp);
                Log.w("here ok response", response1);

                socket_out.println("HISTORY_" + user_id + "_");

                //socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //socket_out.println("KO_\n");

                Log.w("here", "trying to read len");
                byte[] temp3 = new byte[100];
                int nn = receiver.read(temp3);
                Log.w("here nn", String.valueOf(nn));
                String aa = new String(temp3);
                String[] ss = aa.split("_");
                Log.w("hereqq", ss[0]);
                int slen = Integer.parseInt(ss[0]);
                Log.w("here ll", String.valueOf(slen));

                socket_out.println("Length ok_\n");

                Log.w("here 22", String.valueOf(slen));

                n2 = 0;
                String response2 = "";
                byte[] temp2 = new byte[1024];
                int tt =0;
                while (n2 < slen) {
                    tt = receiver.read(temp2);
                    if (tt != -1) {
                        n2 += tt;
                        response2 = response2 + new String(temp2);
                        Log.w("here  what i got", response2);
                        System.out.println("hiiiiiiiiiiiii");
                        System.out.println(response2);
                    } else {
                        Log.w("here  break string", String.valueOf(n2));
                        break;
                    }
                }
                Log.w("here string", response2);
                Log.w("here hh", String.valueOf(response2.length()));
                Log.w("here n2", String.valueOf(n2));
                Log.w("here slen", String.valueOf(slen));

                xx = response2.split("#");

                socket.close();
                notify();
            } catch (IOException e) {
                Log.w("here socket exception ", "socket exceptino occur");
                e.printStackTrace();
            }
        }
        }
        public String[] getValue(){
            return xx;
        }
    };



}
