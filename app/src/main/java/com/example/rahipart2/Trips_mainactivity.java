package com.example.rahipart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Trips_mainactivity extends AppCompatActivity {

    private TextView Createtrip, Mytrip, Savedtrip, Bookedtrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_mainactivity);


        Createtrip=(TextView) findViewById(R.id.trip_createtrip);
        Mytrip=(TextView) findViewById(R.id.trip_mytrip);
        Savedtrip=(TextView) findViewById(R.id.trip_savedtrip);
        Bookedtrip=(TextView) findViewById(R.id.trip_bookedtrip);

        Createtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Trips_mainactivity.this, Create_trip.class);
                startActivity(intent);
            }
        });

        Mytrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            Intent intent = new Intent(Trips_mainactivity.this, mytrips.class);
            startActivity(intent);
            }
        });

        Bookedtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Trips_mainactivity.this, bookedtrip.class);
                startActivity(intent);
            }
        });


        Savedtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Trips_mainactivity.this, savedtrips.class);
                startActivity(intent);
            }
        });

    }
}
