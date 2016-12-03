package com.example.administrator.jjikmeok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.net.URI;

import static android.R.attr.id;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class SubActivity_02 extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_iMAGE = 2;

    private URI mImageCaptureUri;
    private ImageView iv_IserPhoto;
    private int id_view;
    private String absolutePath;

    public String user_id;
    public String user_pw;

    int networkType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_02);


        TextView textView_id = (TextView) findViewById(R.id.textView_id);
        TextView textView_pw = (TextView) findViewById(R.id.textView_pw);



        Intent intent_01 = getIntent();

        user_id = intent_01.getStringExtra("USER_ID");
        user_pw = intent_01.getStringExtra("USER_PW");

        textView_id.setText(String.valueOf(user_id));
        textView_pw.setText(String.valueOf(user_pw));

    }



    public void doTakeAlbumAction()
    {
        //앨범에서 이미지 가져오기 함수라고 함 이미지 크게 들어가면 error
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //nope
    public ImageView mImageView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
    //==

    public void onClick_album(View v) {

        doTakeAlbumAction();
    }


    public void onClick_05(View v)
    {
        Intent intent_01 = new Intent(getApplicationContext(), SubActivity_04.class);

        intent_01.putExtra("USER_ID", user_id);
        intent_01.putExtra("USER_PW", user_pw);

        startActivity(intent_01);
    }

    public void onClick_10(View v)
    {

        Intent intent_04 = new Intent(getApplicationContext(), SubActivity_05.class);

        intent_04.putExtra("USER_ID", user_id);
        intent_04.putExtra("USER_PW", user_pw);

        startActivity(intent_04);
    }

    //basic camera call
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public void onClick_99(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


    }


}
