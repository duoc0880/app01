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


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://shop-service.j.layershift.co.uk/api/account/register" ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        if(response.length()>0) {
                            try {
                                Integer id_account=0;
                                String success="";
                                JSONObject jsonObject = new JSONObject(response);
                                success = jsonObject.getString("message");
                                id_account = jsonObject.getInt("id_account");
                                Login.enduser = new TramAnh(id_account,username);
                                Log.d(TAG, "onResponse: " + Login.enduser.getUsername());
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(Register.this, "Đăng ký không thành công, Vui lòng thử lại!", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(Register.this, "Tài khoảng đã tồn tại, Bạn vui lòng đăng ký tài khoảng khác!",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("username", username);
                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
