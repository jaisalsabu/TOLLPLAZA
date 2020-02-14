package com.example.fastag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Paymentpage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Intent itre;
    EditText edre;
    Button btnhu;
    TextView trede,trde;
    String u,z,w,x,y,loi,lloi;
    SharedPreferences vfre;
    String[] sex = {"BUS", "CAR", "TRUCK","HEAVY"};
    Spinner gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentpage);
        itre=getIntent();
        vfre=getSharedPreferences("eds",MODE_PRIVATE);
        btnhu=findViewById(R.id.button4);
        edre=findViewById(R.id.editText9);
        trede=findViewById(R.id.textView2);
        gender = findViewById(R.id.type);
        trde=findViewById(R.id.textView3);
        trede.setText("USER ID:"+itre.getStringExtra("userid"));
        trde.setText("TOLL ID:"+itre.getStringExtra("tollid"));
        loi=itre.getStringExtra("userid");
        lloi=itre.getStringExtra("tollid");
        gender.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa);
        btnhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/Tollplaza/feeretrival.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("success")) {
                                    Toast.makeText(Paymentpage.this,response,Toast.LENGTH_LONG).show();
                                    Intent treddedd=new Intent(getApplicationContext(),Confirmpay.class);
                                    treddedd.putExtra("Vno",edre.getText().toString());
                                    startActivity(treddedd);
                                }
                                else
                                {
                                    Toast.makeText(Paymentpage.this,"Unsuccesfull attempt",Toast.LENGTH_LONG).show();
                                }

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_obj = jsonArray.getJSONObject(i);
                                        u=json_obj.getString("id");
                                        z=json_obj.getString("Tollid");
                                        w=json_obj.getString("Tollname");
                                        x=json_obj.getString("vehicletype");
                                        y=json_obj.getString("Tollfee");
                                        vfre=getSharedPreferences("data",MODE_PRIVATE);
                                        SharedPreferences.Editor ee=vfre.edit();
                                        ee.putString("Amount",y);
                                        ee.putString("user",loi);
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
                        params.put("tollid",itre.getStringExtra("tollid"));
                        params.put("vtype",gender.getSelectedItem().toString());
//returning parameter
                        return params;
                    }

                };

//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(Paymentpage.this);
                requestQueue.add(stringRequest);

            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(Paymentpage.this,"Sex not selected",Toast.LENGTH_SHORT).show();


    }


}

