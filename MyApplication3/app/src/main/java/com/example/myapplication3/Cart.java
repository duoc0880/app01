package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Cart extends AppCompatActivity {
    String TAG = "GiohangAdapter";
    ListView lvgiohang;
    TextView txtthongbao;
    static  TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar toolbargiohang;

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvgiohang = (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoanlohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);

        cartAdapter = new CartAdapter(getApplicationContext(), MainActivity.arrayCart);
        lvgiohang.setAdapter(cartAdapter);
        ActionToolbar();
        CheckData();
        EvenButton();

    }

    private void ActionToolbar(){
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CheckData() {
        if (MainActivity.arrayCart.size() <= 0) {
            //   giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }

    }

    public static void EvenUtil(){
        long tongtien = 0;
        for(int i =0; i<MainActivity.arrayCart.size();i++){
            tongtien +=MainActivity.arrayCart.get(i).getPrice();
        }
        DecimalFormat decimaFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimaFormat.format(tongtien)+ "D");
    }

    private void EvenButton(){
        btntieptucmua.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }

        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(MainActivity.arrayCart.size()>0){
                    Intent intent = new Intent(getApplicationContext(), ThongtinKhachhang.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Gio hang cua ban chua co san pham",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}
