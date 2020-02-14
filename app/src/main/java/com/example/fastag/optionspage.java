package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class optionspage extends AppCompatActivity {
Button btc,btd;
SharedPreferences sharedPreferences;
String idsesd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionspage);
        btc=findViewById(R.id.button7);
        btd=findViewById(R.id.button8);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        idsesd=sharedPreferences.getString("Userid","****");
        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iroewe=new Intent(getApplicationContext(),recharge.class);
                startActivity(iroewe);
            }
        });
        btd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ioere=new Intent(getApplicationContext(),Scanpage.class);
                ioere.putExtra("id",idsesd);
                startActivity(ioere);
            }
        });
    }
}
