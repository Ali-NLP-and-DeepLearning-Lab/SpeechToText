package com.example.dell.speechtotext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    private String LOG_TAG = "VoiceRecognition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAudioPermission();

        btn = (Button) findViewById(R.id.micbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permission=ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.RECORD_AUDIO);
                if(permission!=PackageManager.PERMISSION_GRANTED){
                    checkAudioPermission();
                }
                else {

                    SpeechConverterClass speechConverter = new SpeechConverterClass();
                    speechConverter.convertSpeech(MainActivity.this);

                }
            }
        });
    }

    private void checkAudioPermission() {
        String[] permission=new String[]{Manifest.permission.RECORD_AUDIO};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permission,111);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
