package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class reg2 extends AppCompatActivity {
    EditText ed5,ed6;
    Button btb2;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);
        i=getIntent();
        ed5=findViewById(R.id.editText5);
        ed6=findViewById(R.id.editText6);
        btb2=findViewById(R.id.button2);
        btb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(ed5.getText().toString().isEmpty() || ed6.getText().toString().isEmpty()) && (ed5.getText().toString().equals(ed6.getText().toString()))) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/Tollplaza/tolluserreg.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//If we are getting success from server
                                    Toast.makeText(reg2.this, response, Toast.LENGTH_LONG).show();
                                    if (response.equals("Success")) {

                                        new SweetAlertDialog(reg2.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Registration Success")
                                                .setContentText("Login to Dashboard!")
                                                .setConfirmText("Yes,Login")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog
                                                                .setTitleText("Logining...!")

                                                                .setConfirmText("OK")

                                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                    @Override
                                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                        Intent in = new Intent(reg2.this, Login.class);
                                                                        startActivity(in);
                                                                    }
                                                                })
                                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                    }
                                                })
                                                .show();


//
                                    }


                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                                }

                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();


                            params.put("sname", i.getStringExtra("name"));
                            params.put("semail", i.getStringExtra("email"));
                            params.put("sphone", i.getStringExtra("phone"));
                            params.put("sadd", i.getStringExtra("address"));
                            params.put("spassword", ed5.getText().toString());

// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                            return params;
                        }

                    };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(reg2.this);
                    requestQueue.add(stringRequest);

                }
                else
                {
                    Toast.makeText(reg2.this,"Unsuccesful attempt",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
