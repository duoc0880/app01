package com.example.myapplication3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoaiProductAdapter extends BaseAdapter {
    ArrayList<Product_Model>  arrayLoaiproduct;
    Context context;
    String TAG ="LoaiProductAdapter";

    public LoaiProductAdapter(ArrayList<Product_Model> arrayLoaiproduct, Context context) {
        this.arrayLoaiproduct = arrayLoaiproduct;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayLoaiproduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLoaiproduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
     public class ViewHolder {
        public TextView txttendienthoai, txtgiadienthoai, txtmotadienthoai;
        public ImageView imgdienthoai;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_product, null);
            viewHolder.txttendienthoai = view.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = view.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgdienthoai = view.findViewById(R.id.imgdienthoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product_Model lisproduct_listview = arrayLoaiproduct.get(position);
        viewHolder.txttendienthoai.setText(lisproduct_listview.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá: "+ decimalFormat.format(lisproduct_listview.getPrice())+ " vnđ");
        viewHolder.txtmotadienthoai.setText(lisproduct_listview.getDescription());
        Log.d(TAG, "getView: " + lisproduct_listview.getImage());
        Picasso.with(context).load(lisproduct_listview.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgdienthoai );
        return view;
    }
}
