package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Scanpage extends AppCompatActivity {
    Button btn,btno;
    String tollid,userid;
    Intent i;
    SharedPreferences gtre;
    IntentIntegrator qrscan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanpage);
        btn = findViewById(R.id.button5);
        btno=findViewById(R.id.button50);
        i=getIntent();
        userid=i.getStringExtra("id");
        gtre=getSharedPreferences("eds",MODE_PRIVATE);
        qrscan = new IntentIntegrator(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrscan.initiateScan();

            }
        });
        btno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iose=new Intent(getApplicationContext(),Paymentpage.class);
                iose.putExtra("tollid",tollid);
                iose.putExtra("userid",userid);
                startActivity(iose);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                tollid=result.getContents();
                Toast.makeText(Scanpage.this,result.getContents(),Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


