package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public final class ThongtinKhachhang extends AppCompatActivity {
    String TAG="thongtinkhachhang";
    EditText edttenkhachhang,edtadress,edtsdt;
    Button btnxacnhan, btntrove = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_khachhang);

        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                    finish();
                }
            });

            //    EvenButton();

        }


        private void Anhxa(){
            edttenkhachhang = findViewById(R.id.edittexttenkhachhang);
            edtadress = findViewById(R.id.edittextadress);
            edtsdt = findViewById(R.id.edittextsodienthoai);
            btnxacnhan = findViewById(R.id.buttonxacnhan);
            btntrove = findViewById(R.id.buttontrove);
        }

        private void EvenButton(){
            btnxacnhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String ten = edttenkhachhang.getText().toString().trim();
                    final String sdt = edtsdt.getText().toString().trim();
                    final String email = edtadress.getText().toString().trim();

                }
                });
        }
}

