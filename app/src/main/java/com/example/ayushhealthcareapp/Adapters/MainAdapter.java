package com.example.ayushhealthcareapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayushhealthcareapp.HospitalDetail;
import com.example.ayushhealthcareapp.Models.MainModel;
import com.example.ayushhealthcareapp.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewholder>{

    ArrayList<MainModel> list;
    Context context;

    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<MainModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_hospital_page, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        final MainModel model = list.get(position);
        holder.hospitalImage.setImageResource(model.getImage());
        holder.mainName.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.time.setText(model.getTime());
        holder.distanceView.setText(model.getDistance()/1000 + " Km");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HospitalDetail.class);

                Bundle bundle = new Bundle();

//                String hospitalName="kailash";
//                String hospitalAddress="delhi";
//                int open_time=7;
//                int close_time=12;
//                String phone_No="9205261029";
//                String website="google.com";

                StringBuilder services=new StringBuilder();
                for (String str: model.getServices()) {
                    services.append(str).append(",");
                }

                bundle.putString("hospitalName",model.getName());
                bundle.putString("hospitalAddress",model.getAddress());
                bundle.putString("time",model.getTime());
                bundle.putString("distance",model.getDistance()/1000+"Km");
                bundle.putString("services",services.toString());
//                bundle.putInt("open_time",open_time);
//                bundle.putInt("close_time",close_time);
                bundle.putString("phone_no",model.getPhone_no());
                bundle.putString("website",model.getWebsite_link());
                intent.putExtra("data",bundle);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView hospitalImage;
        TextView mainName, address, time;
        LinearLayout linearLayout;
        TextView distanceView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            hospitalImage = itemView.findViewById(R.id.imageView);
            mainName = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.time);
            linearLayout= itemView.findViewById(R.id.hospitalLayoutId);
            distanceView =itemView.findViewById(R.id.distance);


        }
    }
}
