package com.example.myapplication3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtuser, edtpass;
    Button btnlogin, btnReg;
    String TAG = "Login";
    String user ="", pass="", temp;
    public static TramAnh enduser;
    public static String token;
    JSONObject jsonObject_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FindView();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edtuser.getText().toString().trim().length()>0 &&edtpass.getText().toString().trim().length()>0) {
                    ProgressDialog pd = new ProgressDialog(Login.this);
                    pd.setMessage("Chờ tí...");
                    pd.show();
                    LoginRequest();

                }else{
                    Toast.makeText(getApplicationContext(), "vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void FindView(){
        edtuser = findViewById(R.id.edittext_username);
        edtpass = findViewById(R.id.edittext_password);
        btnlogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegister);

    }

    private void LoginRequest() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username",edtuser.getText().toString().trim() );
            jsonBody.put("password", edtpass.getText().toString().trim());
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shop-service.j.layershift.co.uk/api/login-customer",
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse: " + response);

                    if (response.length()>0) {
                            MainActivity.logOut = true;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            token = jsonObject.getString("accessToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        JSONObject parameters = new JSONObject();

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://shop-service.j.layershift.co.uk/api/account/7", parameters,new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "onResponse: " + response);
                                Integer id;
                                String username="";

                                try {
                                    id = response.getInt("id");
                                    username = response.getString("username");
                                    jsonObject_profile = response.getJSONObject("profile");

                                    Log.d(TAG, "onResponse: " + jsonObject_profile);
                                    Integer id1;
                                    String fullname="", email="", address="";
                                    long phone=0;
                                    String avatar="";
                                    try {
                                        id1 = jsonObject_profile.getInt("id");
                                        fullname = jsonObject_profile.getString("fullname");
                                        email = jsonObject_profile.getString("email");
                                        phone =jsonObject_profile.getLong("phone");
                                        address =jsonObject_profile.getString("address");
                                        avatar = jsonObject_profile.getString("avatar");
                                      //  avatar=avatar.replaceAll("/","");
                                      //  avatar=avatar.substring(2,avatar.length()-2);
                                        Log.d(TAG, "onResponse: " + avatar);
                               //         if(username.length()==0){username=edtuser.getText().toString().trim();}
                                        enduser = new TramAnh(id,username,fullname,avatar,email,phone,address);
                                        Log.d(TAG, "onResponse: " + enduser.getAvatar());

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                   // Login.enduser = new TramAnh(id,username);
                                    Intent intent = new Intent(Login.this, MainActivity.class );
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("onErrorResponse", error.toString());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                // Basic Authentication
                                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                                headers.put("Authorization", "Bearer " + token);
                                return headers;
                            }
                        };
                        queue.add(request);


                        startActivity(intent);

                    }else{
                        Toast.makeText(Login.this,"Bạn đã nhập sai Vui lòng nhập lại!",Toast.LENGTH_SHORT).show();
                        edtuser.getText().clear();
                        edtpass.getText().clear();

                    }
                }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
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

    /*            @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                } */
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
}
