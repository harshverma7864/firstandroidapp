package com.example.cocktailhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lounges;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button button;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    DataClass dataClass;
    ArrayList<LoungeDataClass> jsonMenuArray;
    FirebaseAuth auth;
    String url="https://winbattleuc.in/fetchdata.php";
    String menuUrl = "https://winbattleuc.in/fetchmenu.php";
    ProgressBar progressBar;


    public static ArrayList<DataClass> dataClassArrayList = new ArrayList<>();
    public static ArrayList<LoungeDataClass> loungeDataClassArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.listview);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        auth = FirebaseAuth.getInstance();
        myAdapter = new MyAdapter(this, dataClassArrayList);
        recyclerView.setAdapter(myAdapter);
//        toolbar.setTitle("Cocktail House");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DuoDrawerLayout drawerLayout = findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        LinearLayout ll_home = menuView.findViewById(R.id.home_n);
        LinearLayout ll_about = menuView.findViewById(R.id.about_n);
        LinearLayout ll_bookings = menuView.findViewById(R.id.bookings_n);
        LinearLayout ll_lounges = menuView.findViewById(R.id.lounges_n);
        LinearLayout ll_settings = menuView.findViewById(R.id.settings_n);
        LinearLayout ll_login = menuView.findViewById(R.id.login_n);
        LinearLayout ll_logout = menuView.findViewById(R.id.logout_n);


        ll_home.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_bookings.setOnClickListener(this);
        ll_lounges.setOnClickListener(this);
        ll_settings.setOnClickListener(this);
        ll_login.setOnClickListener(this);
        ll_logout.setOnClickListener(this);


        getData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=auth.getCurrentUser();
        if (currentUser==null)
        {
            auth.signOut();
            SendUserToLoginActivity();

        }
    }

    private void SendUserToLoginActivity() {
        Intent intent =new Intent(MainActivity.this, PhoneAuth.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    private void getData(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataClassArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray jsonMenuArray = jsonObject.getJSONArray("menu");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String sno = object.getString("sno");
                            String lname = object.getString("lname");
                            String location = object.getString("location");
                            String lid = object.getString("lid");
                            String rating = object.getString("rating");
                            String imgurl = object.getString("urltoimage");
                            dataClass  = new DataClass(sno, lname, location, lid , rating,imgurl);
                            dataClassArrayList.add(dataClass);
                            myAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                } catch (Exception e) {

                }
            }
            }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage()  , Toast.LENGTH_SHORT).show();
                    }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }
//
//    private void getMenu(){
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                dataClassArrayList.clear();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonMenuArray = jsonObject.getJSONArray("menu");
//
////                    if (success.equals("1")) {
////                        for (int i = 0; i < jsonMenuArray.length(); i++) {
////                            JSONObject object = jsonArray.getJSONObject(i);
////
////                            String sno = object.getString("sno");
////                            String lname = object.getString("lname");
////                            String location = object.getString("location");
////                            String lid = object.getString("lid");
////                            String rating = object.getString("rating");
////                            String imgurl = object.getString("urltoimage");
////                            dataClass  = new DataClass(sno, lname, location, lid , rating,imgurl);
////                            dataClassArrayList.add(dataClass);
////                            myAdapter.notifyDataSetChanged();
////                            progressBar.setVisibility(View.GONE);
////                        }
////
////                    }
//
//                } catch (Exception e) {
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, error.getMessage()  , Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(request);
//    }



    private void Logout(){
        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!=null){
            auth.signOut();
            SendUserToLoginActivity();
        }

    }




    private void toastfortest(){
        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_n:

                break;

            case R.id.about_n:
                toastfortest();
                break;

            case R.id.bookings_n:
                toastfortest();
                break;

            case R.id.lounges_n:
                toastfortest();
                break;

            case R.id.settings_n:
                toastfortest();
                break;

            case R.id.login_n:
                toastfortest();
                break;

            case R.id.logout_n:
                Logout();
                break;
        }
    }
}

