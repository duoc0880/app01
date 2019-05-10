package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {
    String TAG = "Cart";
    ListView lvgiohang;
    TextView txtthongbao;
    TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar toolbargiohang;

     CartAdapter cartAdapter;
    ArrayList<Cart_Model> arrayCart1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvgiohang = (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoanlohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);

        toolbargiohang = findViewById(R.id.toolbargiohang);
        arrayCart1 = new ArrayList<>();
        cartAdapter = new CartAdapter(getApplicationContext(), arrayCart1);
        LoadDataCart();
        lvgiohang.setAdapter(cartAdapter);

        ActionToolbar();
       // CheckData();
       // EvenUtil();
        EvenButton();






        lvgiohang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }

    public  void LoadDataCart() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET ,"http://shop-service.j.layershift.co.uk/api/cart/customer",parameters,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                Integer id;
                String name_product, image;
                Integer quantity;
                long amount;
                long total_price=0;
                JSONArray jr;
                try {
                    jr=response.getJSONArray("carts");
                    total_price=response.getLong("total_price");
                    txtthongbao.setVisibility(View.INVISIBLE);
                    for(int i =0;i<jr.length();i++) {
                        JSONObject jsonObject = jr.getJSONObject(i);
                        Log.d(TAG, "onResponse: 1" + jsonObject);
                        id=jsonObject.getInt("id_product");
                        Log.d(TAG, "onResponse: 2");
                        name_product=jsonObject.getString("name_product");
                        Log.d(TAG, "onResponse: 3");
                        quantity=jsonObject.getInt("quantity");
                        Log.d(TAG, "onResponse: 4");
                        amount=jsonObject.getLong("amount");
                        Log.d(TAG, "onResponse: 5");
                        image = jsonObject.getString("image");
                        arrayCart1.add(new Cart_Model(id,name_product,quantity,image,amount));
                        Log.d(TAG, "onResponse1: " + arrayCart1.get(0).product);
                        cartAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DecimalFormat decimaFormat = new DecimalFormat("###,###,###");
                txttongtien.setText(decimaFormat.format(total_price)+ " vnđ");



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

                headers.put("Authorization", "Bearer " + Login.token);
                return headers;
            }
        };
        queue.add(jor);

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
                if(arrayCart1.size()>0){
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("id_payment",1);
                        jsonBody.put("fullname",Login.enduser.getFullname());
                        jsonBody.put("phone",Login.enduser.getPhone());
                        jsonBody.put("address",Login.enduser.getAdress());
                        final String mRequestBody = jsonBody.toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shop-service.j.layershift.co.uk/api/transaction/add",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG, "onResponse: " + response);
                                        Toast.makeText(getApplicationContext(),"Giao dịch thành công!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Cart.this,MainActivity.class);
                                        startActivity(intent);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                // Basic Authentication
                                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                                headers.put("Authorization", "Bearer " + Login.token);
                                return headers;
                            }

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

                        };

                        requestQueue.add(stringRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm nào!",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    protected void onPause(){
        super.onPause();
        finish();
    }
}
