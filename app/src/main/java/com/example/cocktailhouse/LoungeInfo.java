package com.example.cocktailhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoungeInfo extends AppCompatActivity {
    RecyclerView recyclerView;
    Intent intent = getIntent();
//    Bundle args = intent.getBundleExtra("BUNDLE");
//    Bundle Menu = getIntent("menu");
    String LID = intent.getStringExtra("lid");
    LoungeDataClass ldata;
    RAdapter adapter;
    String menuUrl = "https://winbattleuc.in/fetchmenu.php";


    public static ArrayList<LoungeDataClass> menu = new ArrayList<>();

    LoungeDataClass menuData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_info);

        recyclerView = findViewById(R.id.recyclerview);

         adapter = new RAdapter(menu ,this);
        recyclerView.setAdapter(adapter);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);



    }
    private void getMenuData(){
        StringRequest request = new StringRequest(Request.Method.POST, menuUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                menu.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray jsonMenuArray = jsonObject.getJSONArray("menu");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonMenuArray.length(); i++) {
                            JSONObject object = jsonMenuArray.getJSONObject(i);

                            String sno = object.getString("sno");
                            String lname = object.getString("lname");
                            String lid = object.getString("lid");
                            int price = object.getInt("price");
                            ldata  = new LoungeDataClass(sno, lname, lid ,price);
                            menu.add(ldata);
                            adapter.notifyDataSetChanged();
//                            progressBar.setVisibility(View.GONE);
                        }

                    }

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoungeInfo.this, error.getMessage()  , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LoungeInfo.this);
        requestQueue.add(request);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}