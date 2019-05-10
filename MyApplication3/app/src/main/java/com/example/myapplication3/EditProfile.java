package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    EditText edtfull, edtemail, edtphone, edtadress;
    Button btn_done;
    String TAG ="EditProfile";
    ImageView img;
    Integer id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edtfull = findViewById(R.id.edittextfullname);
        edtemail = findViewById(R.id.edittextviewemail);
        edtphone = findViewById(R.id.edittextviewphone);
        edtadress = findViewById(R.id.edittextadress);
        btn_done = findViewById(R.id.button_edit_save);
        img = findViewById(R.id.imageView_avatar);


        String fullname="", email="", phone="", address="";
        String avatar="";


        edtfull.setText(Login.enduser.getFullname());
        edtemail.setText(Login.enduser.getEmail());
        edtphone.setText(String.valueOf(Login.enduser.getPhone()));
        edtadress.setText(Login.enduser.getAdress());
        Picasso.with(getApplicationContext()).load(Login.enduser.getAvatar())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(img);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Profile();

            }
        });

    }

    private void Edit_Profile() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();


            jsonBody.put("fullname",edtfull.getText().toString().trim() );
            jsonBody.put("avatar", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUxP7GMhwCX70huFuOwnSNWYoDl6MhjNgV5hyZuNun5D2XEjhuIA");
            jsonBody.put("email", edtemail.getText().toString().trim());
            jsonBody.put("phone", edtphone.getText().toString().trim());
            jsonBody.put("address", edtadress.getText().toString().trim());
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://shop-service.j.layershift.co.uk/api/account/edit",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: " + response);
                            if (response.length()>0){
                                Toast.makeText(getApplicationContext(),"Thông tin đã chỉnh sửa thành công!",Toast.LENGTH_SHORT).show();
                                Login.enduser.setFullname(edtfull.getText().toString().trim());
                                Login.enduser.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUxP7GMhwCX70huFuOwnSNWYoDl6MhjNgV5hyZuNun5D2XEjhuIA");
                                Login.enduser.setAdress(edtadress.getText().toString().trim());
                                Login.enduser.setPhone(Long.parseLong(edtphone.getText().toString().trim()));
                                Login.enduser.setEmail(edtemail.getText().toString().trim());


                                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Chỉnh sửa thông tin không thành công", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Chỉnh sửa thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            })

            {


                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Login.token);
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }

