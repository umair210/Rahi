package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class ongoing_trip extends AppCompatActivity
{

    private androidx.appcompat.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_trip);

        toolbar =(Toolbar) findViewById(R.id.mytoolbar);
        tabLayout=(TabLayout) findViewById(R.id.tablayout);
        viewPager =(ViewPager) findViewById(R.id.myViewPager);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);





   //Bottom Navigation
        BottomNavigationView bottomNavigationView =findViewById(R.id.nvg_view);

        //home button selection
        bottomNavigationView.setSelectedItemId(R.id.ongoingtrip_profile);
        //performing selected item
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.search_profile:
                        startActivity(new Intent(getApplicationContext()
                                ,Search.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ongoingtrip_profile:
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
//ending of bottom navigationbar

    }

    //method to pass fragments to viewpageradapter

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ItinearyFragment(),"Itineary");
        viewPagerAdapter.addFragment(new MapFragment(),"Map");
        viewPager.setAdapter(viewPagerAdapter);
    }


}
