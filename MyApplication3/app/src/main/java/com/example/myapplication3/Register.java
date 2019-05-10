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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText edtusername, edtpassword, edtconfirmpassword;
    Button btnReg;
    String TAG ="Register";
    public static Integer id_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FindByID();

        
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                ProgressDialog pd = new ProgressDialog(Register.this);
                pd.setMessage("Chờ tí...");
                pd.show();
                 String username = edtusername.getText().toString().trim();
                 String password = edtpassword.getText().toString().trim();
                 String xacnhanpassword = edtconfirmpassword.getText().toString().trim();
                if(username.length()>0 && password.length()>0) {
                    if( Integer.parseInt(password) ==Integer.parseInt(xacnhanpassword)) {
                        RegisterRequestVolley(username, password);
                    //    Log.d(TAG, "onClick: " + Login.enduser.getUsername());

                    }else { Toast.makeText(Register.this, "Mật khẩu bạn nhập không khớp,Vui lòng nhập đúng thông tin!",Toast.LENGTH_SHORT).show();}
                }else{
                    Toast.makeText(Register.this, "Vui lòng nhập đúng thông tin!",Toast.LENGTH_SHORT).show();
                }
                }
        });
    }


    private void FindByID() {
        edtusername = findViewById(R.id.edittext_username);
        edtpassword = findViewById(R.id.edittext_pass);
        edtconfirmpassword = findViewById(R.id.edittextxacnhanPassword);
        btnReg = findViewById(R.id.btnRegister);

    }




    private void RegisterRequestVolley(final String username, final String password) {


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();


            jsonBody.put("username",username );
            jsonBody.put("password",password );
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shop-service.j.layershift.co.uk/api/account/register",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                id_account = jsonObject.getInt("id_account");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            })

            {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

           //     @Override
           //     public Map<String, String> getHeaders() throws AuthFailureError {
           //         Map<String, String> headers = new HashMap<>();
           //         headers.put("Authorization", "Bearer " + Login.token);
           //         return headers;
           //     }

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
        //Creating a string request

    }
    protected void onPause(){
        super.onPause();
        finish();
    }
}
