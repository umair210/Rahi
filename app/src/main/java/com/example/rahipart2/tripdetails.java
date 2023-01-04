package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahipart2.Model.products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class tripdetails extends AppCompatActivity {


    private ImageView productimage;
    private Button starttrip_btn;
    private TextView days_details, location_details, itineary_details;
    private String productID= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripdetails);

        productID =getIntent().getStringExtra("pid");
        productimage =(ImageView) findViewById(R.id.trip_image);
        starttrip_btn=(Button) findViewById(R.id.starttripbtn);
        days_details=(TextView) findViewById(R.id.noofdays_details);
        location_details=(TextView) findViewById(R.id.location_details);
        itineary_details=(TextView) findViewById(R.id.itineary_details);

        getProductDetails(productID);

    }

    private void getProductDetails(String productID)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Trips");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    products products =snapshot.getValue(products.class);
                    days_details.setText(products.getNoofdays());
                    location_details.setText(products.getLocation());
                    itineary_details.setText(products.getItineary());
                    Picasso.get().load(products.getImage()).into(productimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
