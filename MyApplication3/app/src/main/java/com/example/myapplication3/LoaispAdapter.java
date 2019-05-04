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

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arraylistloaisp ;
    Context context;
    private TextView txttenloaisp;
    private ImageView imgloaisp;
    String TAG ="Loaisp";

    public LoaispAdapter(ArrayList<Loaisp> arraylistloaisp, Context context) {
        this.arraylistloaisp = arraylistloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylistloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

  /*  public class ViewHolder {
        TextView txttenloaisp;
        ImageView imgloaisp;
    }
*/
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d(TAG, "getView: Kich vao loai san pham");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_listview_loaisp, null);
        txttenloaisp = view.findViewById(R.id.textviewloaisp);
        imgloaisp = view.findViewById(R.id.imageviewloaisp);

        Loaisp loaisp = (Loaisp) getItem(position);
        txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp())
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(imgloaisp);
        return view;
    }
}
