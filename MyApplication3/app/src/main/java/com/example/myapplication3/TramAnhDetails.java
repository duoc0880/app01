package com.example.myapplication3;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class TramAnhDetails extends AppCompatActivity {
    String TAG = "TramAnhDetails";
    TextView tvfull, tvemail, tvphone, tvadress;
    Button btn_edit_profile;
    ImageView img_avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tram_anh_details);
        tvfull = findViewById(R.id.textviewfullname);
        tvemail = findViewById(R.id.textviewemail);
        tvphone = findViewById(R.id.textviewphone);
        tvadress = findViewById(R.id.textviewadress);
        img_avatar = findViewById(R.id.imgavatar);
        btn_edit_profile = findViewById(R.id.button_edit_profile);

        tvfull.setText(Login.enduser.getFullname());
        tvadress.setText(Login.enduser.getAdress());
        tvphone.setText("+84" + Login.enduser.getPhone());
        tvemail.setText(Login.enduser.getEmail());
       // Log.d(TAG, "onCreate: " + avatar);
        Picasso.with(getApplicationContext()).load(Login.enduser.getAvatar())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(img_avatar);

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TramAnhDetails.this, EditProfile.class);
                startActivity(intent);
            }
        });

    }
}
