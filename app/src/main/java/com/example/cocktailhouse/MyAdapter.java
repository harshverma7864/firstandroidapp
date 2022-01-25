package com.example.cocktailhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<DataClass> dataClassList;


    public MyAdapter(Context context, List<DataClass> dataClassList) {
        this.context = context;
        this.dataClassList = dataClassList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listitem = layoutInflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.lname.setText(dataClassList.get(position).getlname());
        holder.location.setText(dataClassList.get(position).getLocation());
        holder.rating.setText(dataClassList.get(position).getrating());
        String urltoImage = dataClassList.get(position).getimageurl();
        String loungeId = dataClassList.get(position).getLid();


        Glide.with(context)
                .load(urltoImage)
                .into(holder.imageView);

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "HO GYA RECYCLERVIEW SE " + loungeId, Toast.LENGTH_SHORT).show();
//                        Intent intent =  new Intent(context, LoungeInfo.class);
//                        intent.putExtra("lid",loungeId);
//                        context.startActivity(intent);


            }
        });


    }


    @Override
    public int getItemCount() {
        return dataClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lname, location, rating;
        ImageView imageView;
        View parentView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            this.lname = itemView.findViewById(R.id.textView5);
            this.location = itemView.findViewById(R.id.location);
            this.rating = itemView.findViewById(R.id.textView7);
            this.imageView = itemView.findViewById(R.id.imageView4);
            TextView tv = this.lname;
            this.parentView = itemView;
        }
    }


}


//    public void set(final String s)
//    {
//        final int[] i = new int[1];
//        i[0] = 0;
//        final int length = s.length();
//        final Handler handler = new Handler()
//        {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                char c= s.charAt(i[0]);
//                tv.append(String.valueOf(c));
//                i[0]++;
//            }
//        };
//        final Timer timer = new Timer();
//        TimerTask taskEverySplitSecond = new TimerTask() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0);
//                if (i[0] == length - 1) {
//                    timer.cancel();
//                }
//            }
//        };
//        timer.schedule(taskEverySplitSecond, 1, 500);
//    }
//}