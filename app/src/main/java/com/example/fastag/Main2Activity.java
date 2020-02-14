package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

public class Main2Activity extends AppCompatActivity {
Intent iuy;
String redde;
ImageView img;
Bitmap myBitmap;
SharedPreferences edsse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iuy=getIntent();
        img=findViewById(R.id.imageView6);
        edsse=getSharedPreferences("bill",MODE_PRIVATE);
        redde=edsse.getString("billid","*****");
        myBitmap= QRCode.from(redde).bitmap();
        img.setImageBitmap(myBitmap);
        Toast.makeText(Main2Activity.this,redde,Toast.LENGTH_LONG).show();
    }
}
