package com.example.firebase_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Productadapter extends RecyclerView.Adapter<Productadapter.CustomViewHolder>  {
    private ArrayList<Productinformation> arrayList;

    private Context context;

    public Productadapter(ArrayList<Productinformation> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        CustomViewHolder holder= new CustomViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImv())
                .into(holder.iv_image);
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_useremail.setText(arrayList.get(position).getUseremail());
        holder.tv_money.setText(arrayList.get(position).getMoney());
        holder.tv_category.setText(arrayList.get(position).getCategory());

        final String imv= arrayList.get(position).getImv();
        final String name= (String) holder.tv_name.getText();
        final String money= (String) holder.tv_money.getText();
        final String text=arrayList.get(position).getText();
        final String state=arrayList.get(position).getState();
        final String useremail=arrayList.get(position).getUseremail();
        final String date=arrayList.get(position).getDate();
        final String category= arrayList.get(position).getCategory();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Productlook.class);
                intent.putExtra("imv", imv);
                intent.putExtra("name",name);
                intent.putExtra("money",money);
                intent.putExtra("text",text);
                intent.putExtra("state",state);
                intent.putExtra("useremail",useremail);
                intent.putExtra("date",date);
                intent.putExtra("category",category);
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        //?????? ?????????
        return (arrayList !=null ? arrayList.size():0);
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;//?????????
        TextView tv_name;//???????????????
        TextView tv_useremail;//?????????
        TextView tv_money;//??????
        TextView tv_category;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_image=itemView.findViewById(R.id.iv_image1);
            this.tv_name=itemView.findViewById(R.id.tv_name1);
            this.tv_useremail=itemView.findViewById(R.id.tv_useremail1);
            this.tv_money=itemView.findViewById(R.id.tv_money1);
            this.tv_category=itemView.findViewById(R.id.tv_category);
        }
    }

}
