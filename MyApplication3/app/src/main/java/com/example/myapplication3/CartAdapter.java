package com.example.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.myapplication3.Cart;

public class CartAdapter extends BaseAdapter {
    String TAG = "GiohangAdapter";
    TextView txttengiohang, txtgiagiohang, txtsl;
    ImageView imggiohang;
    Button btnminus, btnvalues, btnplus, btnxoaproduct;

    Context context;
    ArrayList<Cart_Model> arraygiohang;
    public CartAdapter(Context context, ArrayList<Cart_Model> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

 /*   public class ViewHolder{
        TextView txttengiohang, txtgiagiohang;
        ImageView imggiohang;
        Button btnminus, btnvalues, btnplus;
    }
*/

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final int soluonghientai[] = new int[100];
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_giohang, null);
        Log.d(TAG, "getView: ");
        txttengiohang = view.findViewById(R.id.textviewtenlohang);
        txtgiagiohang = view.findViewById(R.id.textviewgiagiohang);
        imggiohang = view.findViewById(R.id.imgviewlohang);
        btnminus = view.findViewById(R.id.buttonminus);
        btnvalues = view.findViewById(R.id.buttonvalues);
       // txtsl = view.findViewById(R.id.textviewsoluong);
        btnplus = view.findViewById(R.id.buttonplus);
        btnxoaproduct = view.findViewById(R.id.buttonxoa);

        final Cart_Model giohang = arraygiohang.get(position);
        Log.d(TAG, "getView: 1");
        txttengiohang.setText(giohang.getProduct());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        txtgiagiohang.setText(decimalFormat.format(giohang.getPrice())+ "vnđ");

        btnvalues.setText(String.valueOf(giohang.getAmount()));
        Picasso.with(context).load(giohang.getImg())
               .placeholder(R.drawable.noimage)
               .error(R.drawable.error)
               .into(imggiohang);
        Log.d(TAG, "getView:2 ");

        btnxoaproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("id_product",giohang.getId());

                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shop-service.j.layershift.co.uk/api/cart/delete",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d(TAG, "onResponse: " + response);

                                        Intent intent = new Intent(context,Cart.class);
                                        context.startActivity(intent);

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
            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {
                    int slht = giohang.getAmount();
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("id_product",giohang.getId() );
                    jsonBody.put("quantity",slht+1 );
                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://shop-service.j.layershift.co.uk/api/cart/update",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d(TAG, "onResponse: " + response);

                                    Intent intent = new Intent(context,Cart.class);
                                    context.startActivity(intent);

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
            }
        });

       btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {
                    int slht = giohang.getAmount();
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("id_product",giohang.getId() );
                    jsonBody.put("quantity",slht-1 );
                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://shop-service.j.layershift.co.uk/api/cart/update",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d(TAG, "onResponse: " + response);

                                    Intent intent = new Intent(context,Cart.class);
                                    context.startActivity(intent);

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
            }
        });



        return view;
    }

}
