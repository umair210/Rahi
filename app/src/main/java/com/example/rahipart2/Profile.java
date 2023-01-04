package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahipart2.Prevalent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity
{

     private TextView nameoftheperson;
     private Button edit_profile,trips;
     private ImageView profilephotoofuser;
     private TextView bioadd;
     private TextView logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout=(TextView) findViewById(R.id.logout);
        bioadd= (TextView) findViewById(R.id.bio_title);
        nameoftheperson =(TextView) findViewById(R.id.nameofperson);
        profilephotoofuser =(ImageView) findViewById(R.id.profilepic);
        trips=(Button) findViewById(R.id.trips);

        trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Create_trip.class);
                startActivity(intent);
            }
        });

        nameoftheperson.setText(Prevalent.currentonlineUsers.getUsername());
        // Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
        Picasso.get().load(Prevalent.currentonlineUsers.getImage()).placeholder(R.drawable.ic_person_black_24dp).into(profilephotoofuser);
        bioadd.setText(Prevalent.currentonlineUsers.getBioofuser());

        //Picasso.get().load(Prevalent.currentonlineUsers.getImage()).placeholder(R.drawable.ic_person_black_24dp);

        edit_profile= (Button) findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, editprofile.class);
                startActivity(intent);
            }
        });
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
    }
});
    //Bottom Navigation
        BottomNavigationView bottomNavigationView =findViewById(R.id.nvg_view);

        //home button selection
        bottomNavigationView.setSelectedItemId(R.id.person_profile);
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
                        return true;
                }
                return false;
            }
        });
//ending of bottom navigation

    }
    //method to pass fragments to viewpageradapter


}
