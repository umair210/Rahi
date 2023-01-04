package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rahipart2.Model.products;
import com.example.rahipart2.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class home extends AppCompatActivity {


private DatabaseReference ProductRef;
private RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    ProductRef= FirebaseDatabase.getInstance().getReference().child("Trips");


        BottomNavigationView bottomNavigationView =findViewById(R.id.nvg_view);

        //home button selection
        bottomNavigationView.setSelectedItemId(R.id.home_profile);
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

        recyclerView =findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //query to retrieve all the data
        FirebaseRecyclerOptions<products> options=
                new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(ProductRef,products.class)
                .build();


        FirebaseRecyclerAdapter<products, ProductViewHolder> adapter =
        new FirebaseRecyclerAdapter<products, ProductViewHolder>(options)  //options from line 86
        {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homescreen_triplayout, parent,false);
                //to return to Productviewholder class
                ProductViewHolder holder =new ProductViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final products products)
            {
             //displaying all the data using holder object

                productViewHolder.textnoofdays.setText("Days :" +products.getNoofdays());
                productViewHolder.textlocationoftrip .setText("Location" + products.getLocation());
                Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(home.this,tripdetails.class);
        intent.putExtra("pid",products.getPid());
        startActivity(intent);
    }
});

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();




    }
}
