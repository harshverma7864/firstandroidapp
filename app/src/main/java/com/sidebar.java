package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailhouse.R;

public class sidebar extends AppCompatActivity {
    TextView textView = findViewById(R.id.lounges);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(sidebar.this, "You clicked on TextView 'Click Me'.", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(sidebar.this , lounges.class);
//                startActivity(intent);
            }
        });
    }
}