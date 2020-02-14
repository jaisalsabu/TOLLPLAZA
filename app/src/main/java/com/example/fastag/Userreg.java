package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Userreg extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4;
    Button btb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userreg);
        ed1=findViewById(R.id.editText);
        ed2=findViewById(R.id.editText2);
        ed3=findViewById(R.id.editText3);
        ed4=findViewById(R.id.editText4);
        btb=findViewById(R.id.button);
        btb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),reg2.class);
                i.putExtra("name",ed1.getText().toString());
                i.putExtra("email",ed2.getText().toString());
                i.putExtra("phone",ed3.getText().toString());
                i.putExtra("address",ed4.getText().toString());
                startActivity(i);
            }
        });
    }
}
