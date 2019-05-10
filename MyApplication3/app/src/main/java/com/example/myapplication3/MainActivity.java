package com.example.myapplication3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    RecyclerView recyclerViewManHinhChinh;
    ArrayList<Loaisp> mangloaisp;
    public static ArrayList<Product_Model> arrayProduct;
    Product_Adapter product_Adapter;



    LoaispAdapter loaispAdapter;


    ViewFlipper viewFlipper;
    static Button btnaccount;
    private   int id=0, ID=0, IDsanpham=0;
    private   String tenloaisp="", tensanpham ="";
    private   String hinhanhloaisp="", hinhanhsanpham ="";
    private   String motasanpham = "";
    private  Integer giasanpham;
    public static Boolean logOut = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        ActionBar();
        ActionViewFlipper();
        GetDuLieusp();
        Dieuhuongsp();
        btnaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(logOut==false) {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, TramAnhDetails.class);
                    startActivity(intent);
                }
            }
        });
        //  Intent intent1 = getIntent();
        //   String value = intent1.getStringExtra("key01");
        //  Log.d(TAG, "onCreate: " + Login.enduser.getUsername() );
        if(Login.enduser!= null) {
            btnaccount.setText(Login.enduser.getUsername());
            logOut = true;
        }

        GETNewProduct();


    }
    private void Anhxa(){
        //navigation bar
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        btnaccount = findViewById(R.id.buttonaccount);
        //new product
        recyclerViewManHinhChinh = findViewById(R.id.recycerview);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        // quang cao
        viewFlipper = findViewById(R.id.viewflipper);
        //mang category
        mangloaisp = new ArrayList<>();
        loaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        listViewManHinhChinh.setAdapter(loaispAdapter);
        //mang product
        arrayProduct = new ArrayList<>();

        product_Adapter = new Product_Adapter(arrayProduct, getApplicationContext());

        recyclerViewManHinhChinh.setAdapter(product_Adapter);
        //mang cart


    }
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionViewFlipper(){
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/qcao/04_04_2019_21_02_58_Realme-3-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/29_03_2019_10_01_26_Oppo-f11pro-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/21_04_2019_16_24_01_Galaxy-A10---normal-sale-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/06_04_2019_12_50_24_j4-j6-plus-800-300.png");

        for(int i =0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
       viewFlipper.setOutAnimation(animation_slide_out);
    }
    private void GetDuLieusp(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://shop-service.j.layershift.co.uk/api/category/view/all",  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response != null){
                    Log.d(TAG, "onResponse: " + response);
                    for(int i=0;i<response.length();i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("name");
                            hinhanhloaisp = jsonObject.getString("image");

                            mangloaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();


                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                    loaispAdapter.notifyDataSetChanged();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Log.d(TAG, "onCreateOptionsMenu: ");
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
    
    private void Dieuhuongsp(){
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, product.class);
                intent.putExtra("idproduct",String.valueOf(mangloaisp.get(position).getId()));

                startActivity(intent);
            }
        });

        product_Adapter.setOnItemClickListener(new Product_Adapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(MainActivity.this, Product_details.class);
                intent.putExtra("details", arrayProduct.get(position).getId());
                Log.d(TAG, "onItemClick: "+ arrayProduct.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }

    private void GETNewProduct() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://shop-service.j.layershift.co.uk/api/product/view/top",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Integer id;
                        String name, category,description,details, image;
                        long price;

                        for(int i = 0;i<response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                name = jsonObject.getString("name");
                                category = jsonObject.getString("category");
                                description = jsonObject.getString("description");
                                details = jsonObject.getString("detail");
                                price = jsonObject.getLong("price");
                                image = jsonObject.getString("image");
                                image=image.replaceAll("/","");
                                image=image.substring(2,image.length()-2);
                                category = jsonObject.getString("category");
                                arrayProduct.add(new Product_Model(id,name,details,description,image,price,category));
                                product_Adapter.notifyDataSetChanged();

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


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }

}
