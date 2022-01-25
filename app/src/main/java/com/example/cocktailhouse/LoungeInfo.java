package com.example.cocktailhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LoungeInfo extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_info);

        recyclerView = findViewById(R.id.recyclerview);

        ArrayList<LoungeDataClass> list = new ArrayList<>();

        list.add(new LoungeDataClass("1","chowmein","abc",200));
        list.add(new LoungeDataClass("2","chowmein","abc",200));
        list.add(new LoungeDataClass("3","chowmein","abc",200));
        list.add(new LoungeDataClass("4","chowmein","abc",200));
        list.add(new LoungeDataClass("5","chowmein","abc",200));
        list.add(new LoungeDataClass("6","chowmein","abc",200));
        list.add(new LoungeDataClass("7","chowmein","abc",200));

        RAdapter adapter = new RAdapter(list ,this);
        recyclerView.setAdapter(adapter);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);



    }
}