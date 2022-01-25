package com.example.cocktailhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RAdapter extends RecyclerView.Adapter<RAdapter.viewHolder> {

    ArrayList<LoungeDataClass> list;
    Context context;


    public RAdapter(ArrayList<LoungeDataClass> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.foor_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        LoungeDataClass loungeDataClass = list.get(position);

        holder.textView.setText(loungeDataClass.getfname());

        holder.textView.setOnClickListener((v)->{
            Toast.makeText(context ,"Item is clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
      return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.rtview);

        }


    }
}
