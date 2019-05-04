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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {
    EditText edtuser, edtpass;
    Button btnlogin, btnReg;
    String TAG = "Login";
    String user ="", pass="", temp;
    public static TramAnh enduser;

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
                //    Log.d(TAG, "onClick: " + enduser.getUsername());

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

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shop-service.j.layershift.co.uk/api/login",
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse: " + response);
                    if (response.length()>0) {
                        String ten = "";
                        Integer id = 0;
                        JSONObject jo;
                        String fullname;
                        String email;
                        String adress;
                        long phone = 0;
                        String avatar;

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ten = jsonObject.getString("username");
                            id = jsonObject.getInt("id");
                            jo =  jsonObject.getJSONObject("profile");

                            fullname = jo.getString("fullname");

                            email = jo.getString("email");

                            phone = jo.getLong("phone");

                            adress = jo.getString("address");

                            avatar = jo.getString("avatar");


                            enduser = new TramAnh(id,ten,fullname,avatar,email,phone,adress);
                            Log.d(TAG, "onResponse: " + enduser.getAvatar());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(Login.this, MainActivity.class);

                        //   intent.putExtra("key01",enduser.getUsername());
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
}
