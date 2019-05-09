package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class product extends AppCompatActivity {
    String TAG = "product";
    TextView tvtoolbar;
    private Toolbar toolbar;
    private ListView lvdt;
    String value;
    public static ArrayList<Product_Model> arrayListProduct;
    public static LoaiProductAdapter loaiProductAdapter;
    public static Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Anhxa();
        Actiontoolbar();


        final Intent intent = getIntent();
        value = intent.getStringExtra("idproduct");

        arrayListProduct = new ArrayList<>();
        loaiProductAdapter = new LoaiProductAdapter(arrayListProduct,getApplicationContext());
        lvdt.setAdapter(loaiProductAdapter);

        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                Intent intent = new Intent(product.this, Product_details_plus.class);
                intent.putExtra("details",id);
                startActivity(intent);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://shop-service.j.layershift.co.uk/api/product/category/"+value,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String name, category,description,details, image;
                        Double price;

                        for(int i = 0;i<response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                name = jsonObject.getString("name");
                                category = jsonObject.getString("category");
                                description = jsonObject.getString("description");
                                details = jsonObject.getString("detail");
                                price = jsonObject.getDouble("price");
                                image = jsonObject.getString("image");
                                category = jsonObject.getString("category");
                                arrayListProduct.add(new Product_Model(id,name,details,description,image,price,category));
                                loaiProductAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void Anhxa(){
        toolbar = findViewById(R.id.toolbardienthoai);
        lvdt = findViewById(R.id.listviewdienthoai);
      //  tvtoolbar = findViewById(R.id.textviewtoolbar);
    }
}
