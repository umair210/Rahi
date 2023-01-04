package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    //Bottom Navigation Code
        BottomNavigationView bottomNavigationView =findViewById(R.id.nvg_view);

        //home button selection
        bottomNavigationView.setSelectedItemId(R.id.search_profile);
        //performing selected item
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.search_profile:
                        return true;

                    case R.id.ongoingtrip_profile:
                        startActivity(new Intent(getApplicationContext()
                                ,ongoing_trip.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home_profile:
                        startActivity(new Intent(getApplicationContext()
                                ,home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.person_profile:
                        startActivity(new Intent(getApplicationContext()
                                ,Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }
}
