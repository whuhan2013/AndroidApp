package com.zj.dogapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.image.sdk.IncSDK;

/**
 * Created by jjx on 2016/5/9.
 */
public class SplashActivity extends AppCompatActivity{
    String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //createFiles();

        Intent intent=new Intent(SplashActivity.this,StartActivity.class);
        startActivity(intent);

    }

    private void createFiles() {
        filepath = Environment.getExternalStorageDirectory().getPath()+ "/Samples";
        IncSDK.copyFilesFassets("samples", filepath, getApplicationContext());

    }
}
