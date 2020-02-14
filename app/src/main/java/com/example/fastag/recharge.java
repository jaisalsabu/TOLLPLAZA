package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class recharge extends AppCompatActivity {
EditText edo1,edo2;
Button bte;
ImageButton btnn;
TextView tfre;
String g;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        edo1=findViewById(R.id.editText10);
        edo2=findViewById(R.id.editText11);
        tfre=findViewById(R.id.textView9);
        bte=findViewById(R.id.button6);
        btnn=findViewById(R.id.imageButton2);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        tfre.setText("Balance:"+sharedPreferences.getString("balance","*****"));
        edo1.setText(sharedPreferences.getString("Userid","*****"));
        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/Tollplaza/balupdate.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_obj = jsonArray.getJSONObject(i);
                                        g=json_obj.getString("walletbalance");
                                        tfre.setText("Balance:"+g);
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
                        params.put("uid",sharedPreferences.getString("Userid","*****"));
//returning parameter
                        return params;
                    }

                };

//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(recharge.this);
                requestQueue.add(stringRequest);
            }
        });
        bte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPayment();
            }
        });
    }
    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Toll Plaza");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(edo2.getText().toString())*100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "7034308174");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
    public void onPaymentSuccess(final String s) {
        if (edo2.getText().toString().isEmpty()) {
            edo2.setError("empty");
        } else {


            StringRequest stringRequest;
            stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/Tollplaza/tollrecharge.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(recharge.this, response, Toast.LENGTH_LONG).show();
                            if (response.contains("success")) {
                                Intent in = new Intent(getApplicationContext(), optionspage.class);
                                startActivity(in);

                            }else
                            {
                                Toast.makeText(recharge.this,"WRONG",Toast.LENGTH_LONG).show();
                            }
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
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

                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
//Adding parameters to request
                    params.put("tid",sharedPreferences.getString("Userid","******"));
                    params.put("amount", edo2.getText().toString());

//returning parameter
                    return params;
                }

            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(recharge.this);
            requestQueue.add(stringRequest);

        }

    }

    }



