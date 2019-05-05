package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Product_details_plus extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;
    String TAG = "chitietdienthoai";
    boolean exists;


    int id = 0;
    String TenChitiet = "";
    Double GiaChitiet = 0.0;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
          AnhXa();
          ActionToolbar();
          GetInformation();
          CatchEventSpinner();
         EvenButton ();


    }

    private void AnhXa() {
        toolbarChitiet = findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = findViewById(R.id.imgchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spiner);
        btndatmua = findViewById(R.id.buttonmuahang);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetInformation() {
        Intent intent = getIntent();
        Integer position = intent.getIntExtra("details",-1);

        id = MainActivity.arrayProduct.get(position).getId();
        TenChitiet = MainActivity.arrayProduct.get(position).getName();
        GiaChitiet = MainActivity.arrayProduct.get(position).getPrice();
        HinhanhChitiet = MainActivity.arrayProduct.get(position).getImage();
        MotaChitiet = MainActivity.arrayProduct.get(position).getDescription();

        txtten.setText(product.arrayListProduct.get(position).getName());
        txtgia.setText(String.valueOf(product.arrayListProduct.get(position).getPrice()));
        txtmota.setText(product.arrayListProduct.get(position).getDescription());
        Picasso.with(getApplicationContext()).load(product.arrayListProduct.get(position).getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);


    }

    private void CatchEventSpinner(){
        Integer[] soluong = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayadapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayadapter);
    }


    private void EvenButton (){
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.arrayCart.size()>=0)  // neu gio hang co san pham
                {
                    //   int sl = Integer.parseInt(spinner.getSelectItem().toString());
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString()); //so luong sp

                    for(int i = 0;i<MainActivity.arrayCart.size();i++)
                    {
                        if(MainActivity.arrayCart.get(i).getId()==Idsanpham)    // neu la dien thoai
                        {
                            MainActivity.arrayCart.get(i).setAmount(MainActivity.arrayCart.get(i).getAmount()+sl); //set lai so luong dt
                            if(MainActivity.arrayCart.get(i).getAmount()>=10){
                                MainActivity.arrayCart.get(i).setAmount(10);
                            }
                            MainActivity.arrayCart.get(i).setPrice( GiaChitiet*(MainActivity.arrayCart.get(i).getAmount())); // set lai gia
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        Double Giamoi = soluong*GiaChitiet;
                        MainActivity.arrayCart.add(new Cart_Model(id,TenChitiet,soluong,HinhanhChitiet,Giamoi));
                        Log.d(TAG, "onClick: chay");
                    }
                }
                else{

                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    Double Giamoi = soluong*GiaChitiet;
                    MainActivity.arrayCart.add(new Cart_Model(id,TenChitiet,soluong,HinhanhChitiet,Giamoi));
                }


                Intent intent = new Intent(getApplicationContext(),Cart.class);
                startActivity(intent);
            }
        });
    }


    }
