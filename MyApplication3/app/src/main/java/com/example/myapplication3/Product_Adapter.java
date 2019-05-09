package com.example.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class Product_Adapter extends  RecyclerView.Adapter<Product_Adapter.ItemHolder>  {

    ArrayList<Product_Model> arraysanpham;
    Context context;
    String TAG ="ProductAdapter";
    private static ClickListener clickListener;

    public Product_Adapter(ArrayList<Product_Model> arraysanpham, Context context) {
        Log.d(TAG, "Product_Adapter: ");
        this.arraysanpham = arraysanpham;
        this.context = context;
    }



    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");
        final Product_Model sanpham = arraysanpham.get(i);
        itemHolder.txttensanpham.setText(sanpham.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.txtgiasanpham.setText("Giá: "+ decimalFormat.format(sanpham.getPrice())+ " vnđ");
        Picasso.with(context).load(sanpham.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(itemHolder.imghinhsanpham );
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imghinhsanpham;
        public TextView txttensanpham, txtgiasanpham;
        LinearLayout line;

        public ItemHolder(final View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            imghinhsanpham = itemView.findViewById(R.id.imgviewsanpham);
            txttensanpham = itemView.findViewById(R.id.textviewsanpham);
            txtgiasanpham = itemView.findViewById(R.id.textviewgiasanpham);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: đã c=kick vào đây");
            clickListener.onItemClick(getAdapterPosition(), v);
        }



    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Product_Adapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}
