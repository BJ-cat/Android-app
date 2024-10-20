package com.example.myapplication;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyHolder> {

    private List<CarInfo> mCarInfoList =new ArrayList<>();


    public void setCarInfoList(List<CarInfo> list){
        this.mCarInfoList =list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        CarInfo carInfo = mCarInfoList.get(position);
        holder.product_title.setText( carInfo.name+"");
        holder.product_price.setText(carInfo.description+"");
        holder.product_count.setText(carInfo.type+"");
        holder.product_date.setText(carInfo.link);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.OnClick(carInfo,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarInfoList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView product_title;
        TextView product_price;
        TextView product_count;
        TextView product_date;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //product_img =itemView.findViewById(R.id.product_img);
            product_title =itemView.findViewById(R.id.tvname);
            product_price =itemView.findViewById(R.id.tvdesc);
            product_count =itemView.findViewById(R.id.tvtype);
            product_date =itemView.findViewById(R.id.tvlink);

        }
    }

    private onItemClickListener mOnItemClickListener;


    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener{


        void OnClick(CarInfo carInfo,int position);
}
}
