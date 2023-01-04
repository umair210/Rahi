package com.example.rahipart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.paperdb.Book;

public class mytrips extends AppCompatActivity {

    private TextView Createdtrips, Completedtrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        Createdtrips =(TextView) findViewById(R.id.createdtrips);
        Completedtrips =(TextView) findViewById(R.id.completed_trips);

        Createdtrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mytrips.this, Create_trip.class);
                startActivity(intent);
            }
        });

        Completedtrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mytrips.this, completed_trips.class);
                startActivity(intent);
            }
        });

    }
}
