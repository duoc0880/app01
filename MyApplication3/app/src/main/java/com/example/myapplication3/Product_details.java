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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Product_details extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;
    String TAG = "ProductDetails";
    boolean exists;
    int id_product = 0;
    int quantity = 0;



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
        Integer idDetails = intent.getIntExtra("details", -1);
        Log.d(TAG, "GetInformation: " + idDetails);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        HashMap<String, String> params = new HashMap<String, String>();
       // params.put("token", "AbCdEfGh123456");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,"http://shop-service.j.layershift.co.uk/api/product/view/" + idDetails, new JSONObject(params) ,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String TenChitiet = "";
                        Double GiaChitiet = 0.0;
                        String HinhanhChitiet = "";
                        String MotaChitiet = "";

                        long price =0;
                        try {
                            id_product = response.getInt("id");
                            TenChitiet = response.getString("name");
                            MotaChitiet = response.getString("detail");
                            HinhanhChitiet = response.getString("image");
                            price = response.getLong("price");
                            quantity = response.getInt("quantity");

                               txtten.setText(TenChitiet);
                               txtgia.setText(String.valueOf(price) + " vnđ");
                               txtmota.setText(MotaChitiet);
                               Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                                       .placeholder(R.drawable.noimage)
                                       .error(R.drawable.error)
                                       .into(imgChitiet);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(req);

        //   txtten.setText(MainActivity.arrayProduct.get(idDetails).getName());
        //   txtgia.setText(String.valueOf(MainActivity.arrayProduct.get(idDetails).getPrice()) + " vnđ");
        //   txtmota.setText(MainActivity.arrayProduct.get(idDetails).getDescription());
        //   Picasso.with(getApplicationContext()).load(MainActivity.arrayProduct.get(idDetails).getImage())
        //           .placeholder(R.drawable.noimage)
        //           .error(R.drawable.error)
        //           .into(imgChitiet);


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
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString()); //so luong sp

                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("id_product",id_product );
                        jsonBody.put("quantity",sl );
                        final String mRequestBody = jsonBody.toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shop-service.j.layershift.co.uk/api/cart/add",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG, "onResponse: " + Login.token);
                                        Log.d(TAG, "onResponse: " + response);

                                        if (response.length()>0) {

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

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


                    Intent intent = new Intent(getApplicationContext(),Cart.class);
                    intent.putExtra("IdProductInCart",id_product);
                    startActivity(intent);
            }
        });
    }
    protected void onPause(){
        super.onPause();
        finish();
    }

    }
