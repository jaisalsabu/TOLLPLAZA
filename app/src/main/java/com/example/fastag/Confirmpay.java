package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Confirmpay extends AppCompatActivity {
TextView tge,tgge;
Intent uy;
String uid,a;
Button thherkk,tharkki;
SharedPreferences vfres,fresl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmpay);
        tge=findViewById(R.id.textView7);
        tgge=findViewById(R.id.textView8);
        thherkk=findViewById(R.id.button9);
        tharkki=findViewById(R.id.button10);
        vfres=getSharedPreferences("data",MODE_PRIVATE);
        fresl=getSharedPreferences("bill",MODE_PRIVATE);
        uy=getIntent();
        tge.setText(uy.getStringExtra("Vno"));
        tgge.setText(vfres.getString("Amount","******"));
        uid=vfres.getString("user","*****");
        tharkki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/Tollplaza/bill.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("success"))
                                {
                                    Intent iuytr=new Intent(getApplicationContext(),Main2Activity.class);
                                    startActivity(iuytr);
                                }
                                {
                                    Toast.makeText(Confirmpay.this,"Payment not succesfull",Toast.LENGTH_LONG).show();
                                }

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_obj = jsonArray.getJSONObject(i);
                                        a=json_obj.getString("Pass");
                                        SharedPreferences.Editor ee=fresl.edit();
                                        ee.putString("billid",a);
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
                        params.put("vehicle",uy.getStringExtra("Vno"));
//returning parameter
                        return params;
                    }

                };

//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(Confirmpay.this);
                requestQueue.add(stringRequest);
            }
        });
        thherkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://hastalavistaresto.000webhostapp.com/Tollplaza/passid.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//If we are getting success from server
                                if (response.equals("Success")) {
                                    Toast.makeText(Confirmpay.this,"Payment Succesful", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(Confirmpay.this,"Unsuccessfull attempt",Toast.LENGTH_SHORT).show();
                                }
                                try {
                                    JSONArray jsonArray=new JSONArray(response);
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject json_obj = jsonArray.getJSONObject(i);

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

                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
//Adding parameters to request

                        params.put("uid",uid);
                        params.put("amount",vfres.getString("Amount",null));
                        params.put("vno",uy.getStringExtra("Vno"));

//returning parameter
                        return params;
                    }
                };

//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(Confirmpay.this);
                requestQueue.add(stringRequest);

            }
        });

    }
}
