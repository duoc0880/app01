package com.example.myapplication3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    String TAG = "GiohangAdapter";
    TextView txttengiohang, txtgiagiohang, txtsl;
    ImageView imggiohang;
    Button btnminus, btnvalues, btnplus;

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

        txttengiohang = view.findViewById(R.id.textviewtenlohang);
        txtgiagiohang = view.findViewById(R.id.textviewgiagiohang);
        imggiohang = view.findViewById(R.id.imgviewlohang);
        btnminus = view.findViewById(R.id.buttonminus);
        btnvalues = view.findViewById(R.id.buttonvalues);
       // txtsl = view.findViewById(R.id.textviewsoluong);
        btnplus = view.findViewById(R.id.buttonplus);

        Cart_Model giohang = arraygiohang.get(position);

        txttengiohang.setText(giohang.getProduct());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        txtgiagiohang.setText(decimalFormat.format(giohang.getPrice())+ "D");

        btnvalues.setText(String.valueOf(MainActivity.arrayCart.get(position).getAmount()));
        Picasso.with(context).load(giohang.getImg())
               .placeholder(R.drawable.noimage)
               .error(R.drawable.error)
               .into(imggiohang);
        Log.d(TAG, "getView: ");
        int s1 = Integer.parseInt(btnvalues.getText().toString());
        if(s1>=10) {
            btnplus.setVisibility(View.INVISIBLE);
            btnminus.setVisibility(View.VISIBLE);
        }else if(s1<=1)
        {
            btnminus.setVisibility(View.INVISIBLE);
        }else if(s1>=1){
            btnminus.setVisibility(View.VISIBLE);
            btnplus.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "getView: "+ position + view);
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                soluonghientai[position]=(int)MainActivity.arrayCart.get(position).getAmount() +1;
                Log.d(TAG, "onClick: "+ position +" " + soluonghientai[position]+ " " + view);
             //   int s1moinhat = Integer.parseInt(txtsl.getText().toString())+1;
                int slht = MainActivity.arrayCart.get(position).getAmount();
                Double giaht = MainActivity.arrayCart.get(position).getPrice();
                MainActivity.arrayCart.get(position).getPrice();
                Double giamoinhat = soluonghientai[position]*giaht/slht ;
                MainActivity.arrayCart.get(position).setAmount(soluonghientai[position]);
                MainActivity.arrayCart.get(position).setPrice(giamoinhat);
                DecimalFormat decimaFormat = new DecimalFormat("###,###,###");
                txtgiagiohang.setText(decimaFormat.format(giamoinhat)+ "D");
                Cart.EvenUtil();
                btnvalues.setText(String.valueOf(soluonghientai[position]));

              //  Log.d(TAG, "onClick: "+ txtsl);
                if(soluonghientai[position] > 9){
                    btnplus.setVisibility(View.INVISIBLE);
                    btnminus.setVisibility(View.VISIBLE);

                }else {
                    btnminus.setVisibility(View.VISIBLE);
                    btnplus.setVisibility(View.VISIBLE);

                }
            }
        });

       btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                soluonghientai[position]=(int)MainActivity.arrayCart.get(position).getAmount() -1;
                int slht = MainActivity.arrayCart.get(position).getAmount();
                Double giaht = MainActivity.arrayCart.get(position).getPrice();
                MainActivity.arrayCart.get(position).getPrice();
                Double giamoinhat = (giaht*soluonghientai[position])/ slht;
                MainActivity.arrayCart.get(position).setAmount(soluonghientai[position]);
                MainActivity.arrayCart.get(position).setPrice(giamoinhat);
                DecimalFormat decimaFormat = new DecimalFormat("###,###,###");
                txtgiagiohang.setText(decimaFormat.format(giamoinhat)+ "D");
                Cart.EvenUtil();
                btnvalues.setText(String.valueOf(soluonghientai[position]));
                if(soluonghientai[position] < 2){
                    btnminus.setVisibility(View.INVISIBLE);
                    btnplus.setVisibility(View.VISIBLE);
                }else {
                    btnplus.setVisibility(View.VISIBLE);
                    btnminus.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    //  @Override
 /*   public View getView(int i, View view, ViewGroup parent) {
        TextView txttengiohang, txtgiagiohang;
        ImageView imggiohang;
        Button btnminus, btnvalues, btnplus;
     //   ViewHolder viewHolder = null;
    //    if(view == null)
   //     {
       //     viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            txttengiohang = view.findViewById(R.id.textviewtenlohang);
            txtgiagiohang = view.findViewById(R.id.textviewgiagiohang);
            imggiohang = view.findViewById(R.id.imgviewlohang);
            btnminus = view.findViewById(R.id.buttonminus);
            btnvalues = view.findViewById(R.id.buttonvalues);
            btnplus = view.findViewById(R.id.buttonplus);
        //    view.setTag(viewHolder);
     //   }
     //   else {
          //  viewHolder = view.getTag();
      //      viewHolder = (ViewHolder) view.getTag();
      //  }

        Giohang giohang = arraygiohang.get(i);
        txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimaFormat = new DecimalFormat("###,###,###");
        txtgiagiohang.setText(decimaFormat.format(giohang.getGiasp()+ "D"));
        Picasso.with(context).load(giohang.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imggiohang);
        btnvalues.setText(giohang.getSoluongsp());
        return view;
    }*/
}
