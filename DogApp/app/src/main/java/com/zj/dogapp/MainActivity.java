package com.zj.dogapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.image.sdk.IncSDK;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView resultView;
    Bitmap bmp;
    String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("test", "Main"+StartActivity.idNum);
        if(StartActivity.idNum==1) {
            createFiles();
        }
        filepath = Environment.getExternalStorageDirectory().getPath()+ "/Samples";
        resultView= (ImageView) findViewById(R.id.iv_image);
        Resources res = getResources();
        bmp = BitmapFactory.decodeResource(res, R.drawable.a);
        resultView.setImageBitmap(bmp);
    }


    public void chose(View view)
    {
        resultView.setImageDrawable(null);
        Crop.pickImage(this);
    }

    public void send(View view)
    {
        int w = bmp.getWidth(), h = bmp.getHeight();
        int[] pix = new int[w * h];
        bmp.getPixels(pix, 0, w, 0, 0, w, h);
        int result = IncSDK.RecMyImage(w, h, pix, filepath + "/");

        switch (result) {
            case 1:
                Toast.makeText(getApplicationContext(), "哈士奇", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,DogDetailActivity.class);
                intent.putExtra("dogid",1);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "柴犬", Toast.LENGTH_LONG).show();
                Intent intent2=new Intent(MainActivity.this,DogDetailActivity.class);
                intent2.putExtra("dogid",2);
                startActivity(intent2);
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "泰迪", Toast.LENGTH_LONG).show();
                Intent intent3=new Intent(MainActivity.this,DogDetailActivity.class);
                intent3.putExtra("dogid",3);
                startActivity(intent3);
                break;
            case 4:
                Toast.makeText(getApplicationContext(), "萨摩耶", Toast.LENGTH_LONG).show();
                Intent intent4=new Intent(MainActivity.this,DogDetailActivity.class);
                intent4.putExtra("dogid",4);
                startActivity(intent4);
                break;
            case 5:
                Toast.makeText(getApplicationContext(), "金毛", Toast.LENGTH_LONG).show();
                Intent intent5=new Intent(MainActivity.this,DogDetailActivity.class);
                intent5.putExtra("dogid",5);
                startActivity(intent5);
                break;

        }
    }

    private void createFiles() {
        Log.i("test","createFiles");
        //filepath = Environment.getExternalStorageDirectory().getPath()+ "/Samples";
        IncSDK.copyFilesFassets("samples", filepath, getApplicationContext());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri=Crop.getOutput(result);
            try {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
