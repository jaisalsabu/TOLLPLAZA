package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText ed7,ed8;
    Button btb3;
    TextView txt;
    SharedPreferences sh;
    String a,b,c,d,e,f,g,h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed7=findViewById(R.id.editText7);
        txt=findViewById(R.id.textView4);
        ed8=findViewById(R.id.editText8);
        btb3=findViewById(R.id.button3);
        btb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/Tollplaza/tolluserlogin.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains("success")) {
                                        Intent in = new Intent(Login.this, optionspage.class);
                                        startActivity(in);
                                    }
                                    else
                                    {
                                        ed8.setError("incorrect credentials");
                                    }

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject json_obj = jsonArray.getJSONObject(i);
                                            a=json_obj.getString("id");
                                            b=json_obj.getString("Name");
                                            c=json_obj.getString("Uid");
                                            d=json_obj.getString("Email");
                                            e=json_obj.getString("Phone");
                                            f=json_obj.getString("Address");
                                            g=json_obj.getString("walletbalance");
                                            h=json_obj.getString("password");
                                            sh=getSharedPreferences("data",MODE_PRIVATE);
                                            SharedPreferences.Editor ee=sh.edit();
                                            ee.putString("Userid",c);
                                            ee.putString("balance",g);
                                            ee.apply();



                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
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
//Adding parameters to request
                            params.put("phone", ed7.getText().toString());
                            params.put("pass",ed8.getText().toString());
//returning parameter
                            return params;
                        }

                    };

//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                    requestQueue.add(stringRequest);
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ires=new Intent(getApplicationContext(),Userreg.class);
                startActivity(ires);
            }
        });
    }
}
