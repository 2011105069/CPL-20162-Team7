package com.example.administrator.jjikmeok;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class SubActivity_04 extends AppCompatActivity implements OnClickListener {

    public String user_id;
    public String user_pw;

    private Socket socket;
    BufferedReader socket_in;
    PrintWriter socket_out;
    TextView output;
    String data;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    public Uri mImageCaptureUri;
    private ImageView mPhotoImageView;
    private Button mButton;

    private String selectedImagePath;
    private static final int SELECT_PICTURE = 1;

    private EditText mEditInit;
    private TextView mtext;

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_04);

        mButton = (Button) findViewById(R.id.button);
        mPhotoImageView = (ImageView) findViewById(R.id.image);

        mButton.setOnClickListener(this);

        Intent intent_01 = getIntent();

        user_id = intent_01.getStringExtra("USER_ID");
        user_pw = intent_01.getStringExtra("USER_PW");


        mEditInit = (EditText) findViewById(R.id.editText);
        mEditInit.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mEditInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditInit.setText("");
            }

        });

        mtext = (TextView) findViewById(R.id.textView);
        mtext.setVisibility(View.GONE);

    }


    /**
     * 카메라에서 이미지 가져오기
     */
    private void doTakePhotoAction()
    {
    /*
     * 참고 해볼곳
     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
     * http://www.damonkohler.com/2009/02/android-recipes.html
     * http://www.firstclown.us/tag/android/
     */

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction()
    {
        // 앨범 호출
        /*Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);*/

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PICK_FROM_ALBUM);
        Log.w("here","intent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK)
        {
            return;
        }
        switch(requestCode)
        {

            case CROP_FROM_CAMERA:
            {
                Log.w("here","Crop_from_camerA");
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    mPhotoImageView.setImageBitmap(photo);
                }

                break;
            }

            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                Log.w("here","album");

                if (resultCode == RESULT_OK) {
                    if (requestCode == PICK_FROM_ALBUM) {
                        Uri selectedImageUri = data.getData();
                        mImageCaptureUri = selectedImageUri;

                        Log.w("here", "uri path : " + selectedImageUri.getPath());
                        Log.w("here", "uri string : " + selectedImageUri.toString());

                    }
                }

                //★socket part
                mtext.setVisibility(View.VISIBLE);
                String plate = mEditInit.getText().toString();
                MyThread r = new MyThread(mImageCaptureUri, plate);
                r.start();

                synchronized(r){
                    try{
                        System.out.println("Waiting for b to complete...");
                        r.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    String tmp = r.getValue();
                    String[] ee = tmp.split(("-"));
                    mtext.setText("food : " +ee[0] + "\nrate : " + ee[1]);
                }

                break;
            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.


                Log.w("here","Pick from camera");
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                //받아오는 이미지 size
                intent.putExtra("outputX", 1000);
                intent.putExtra("outputY", 1000);
                //crop 비율
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);

                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                intent.putExtra("output", mImageCaptureUri);

                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                doTakePhotoAction();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }
    public void onClick_04(View v)
    {

        finish();
    }


//★socket part

    public class MyThread extends Thread implements Runnable {

        public Uri mImageCaptureUri2;
        public String plate;
        public String res;

        public MyThread(Uri inImage, String pl) {
            // store parameter for later user
            Log.w("here","constructor");
            mImageCaptureUri2=inImage;
            plate = pl;
            Log.w("here", "loc : " + mImageCaptureUri2);
            Log.w("here", "loc : " + inImage);
        }

        public void run() {
            synchronized (this) {
                try {
                    Log.w("here", "thread in");
                    //getRealImagePath

                    Log.w("here", "1");

                    socket = new Socket("192.168.0.2", 11111); //this IP is cmd's ipconfig ip
                    Log.w("here", "2");
                    socket_out = new PrintWriter(socket.getOutputStream(), true);
                    socket_out.println("APP_");

                    InputStream receiver = socket.getInputStream();
                    byte[] temp = new byte[100];
                    int n2 = receiver.read(temp);
                    String response1 = new String(temp);
                    Log.w("here response", response1);

                    Log.w("here", "3");

                    try {
                        InputStream input = getContentResolver().openInputStream(mImageCaptureUri2);
                        byte[] buf = new byte[1024];
                        int c = 0;
                        int flen = 0;
                        while (true) {
                            c = input.read(buf);
                            if (c == -1)
                                break;
                            else
                                flen += c;
                        }
                        input.close();

                        if (flen != 0) {
                            socket_out.println("PICTURE_" + String.valueOf(flen) + "_" + user_id + "_" +plate+"_");
                        }
                        input = getContentResolver().openInputStream(mImageCaptureUri2);

                        while (input.read(buf) > 0) {

                            socket.getOutputStream().write(buf);
                            socket.getOutputStream().flush();
                        }
                        Log.w("here2", "sending done");

                        temp = new byte[100];
                        n2 = receiver.read(temp);
                        response1 = new String(temp);
                        Log.w("here response", response1);
                        String[] ss = response1.split("_");

                        //mtext.setVisibility(View.VISIBLE);

                        if (ss[0].equals("result")) {
                            Log.w("here", "equals result");
                            res = ss[1];
                            //mtext.setText(ss[1]);
                            notify();
                        }
                    } catch (Exception e) {
                        Log.w("here", "inputstream exception " + e.toString());
                    }


                    //FileInputStream input = new FileInputStream(f);
                    Log.w("here", "4");

                    socket.close();


                } catch (IOException e) {
                    Log.w("here", "9catch " + e.getMessage());
                    Log.w("here", "9catch2 " + e.toString());
                    e.printStackTrace();
                }
            }
        }
        public String getValue() {
            return res;
        }

    }

}