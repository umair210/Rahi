package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Create_trip extends AppCompatActivity
{

    private EditText  Additineray, Noofdays, Addlocation;
    private ImageView Addphotos;
    private Button Createnewtrip;
    private static final int Gallerpic =1;
    private Uri ImageUri;
    private String Itineary, noofdays, location, savecurrentdate, savecurrenttime;
    private String productRandomKey, downloadImageUrl;
    private StorageReference Productimageref;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        Additineray =(EditText) findViewById(R.id.itineray_createtrip);
        Noofdays =(EditText) findViewById(R.id.adddays_createtrip);
        Addlocation =(EditText) findViewById(R.id.location_createtrip);
        Addphotos =(ImageView) findViewById(R.id.addphotos_createtrip);
        Createnewtrip =(Button) findViewById(R.id.createnewtripbtn);
        loadingBar = new ProgressDialog(this);

        Productimageref= FirebaseStorage.getInstance().getReference().child("Trip Image");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Trips");



        Createnewtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Validateproductdata();
            }
        });

        Addphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            Opengallery();
            }
        });
    }

    private void Validateproductdata() {

        Itineary = Additineray.getText().toString();
        noofdays = Noofdays.getText().toString();
        location = Addlocation.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Add Image", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Itineary))
        {
            Toast.makeText(this, "Add Itineary", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(noofdays))
        {
            Toast.makeText(this, "Add Days", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(location))
        {
            Toast.makeText(this, "Add Location", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Storeimageinformation();
        }

    }

    private void Storeimageinformation()
    {
        loadingBar.setTitle("Add new trip");
        loadingBar.setMessage("Please wait, while we are adding the new trip");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate= new SimpleDateFormat("MMM day, yyyy");

        savecurrentdate=currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mmss a");

        savecurrenttime=currenttime.format(calendar.getTime());

        productRandomKey= savecurrentdate + savecurrenttime ;

        final StorageReference filePath = Productimageref.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message= e.toString();
                Toast.makeText(Create_trip.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Create_trip.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                     downloadImageUrl =filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                       if(task.isSuccessful())
                       {
                           downloadImageUrl =task.getResult().toString();

                           Toast.makeText(Create_trip.this, "getting product image url successfully", Toast.LENGTH_SHORT).show();

                           SaveProductinfo();

                       }
                    }
                });
            }
        });

    }

    private void SaveProductinfo()
    {

        HashMap<String, Object> productMap= new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",savecurrentdate);
        productMap.put("time",savecurrenttime);
        productMap.put("itineary",Itineary);
        productMap.put("image",downloadImageUrl);
        productMap.put("noofdays",noofdays);
        productMap.put("location",location);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(Create_trip.this, Trips_mainactivity.class);
                            startActivity(intent);


                            loadingBar.dismiss();
                            Toast.makeText(Create_trip.this, "Trip has been created", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(Create_trip.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void Opengallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallerpic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

            super.onActivityResult(requestCode, resultCode, data);

    if(requestCode==Gallerpic && resultCode==RESULT_OK && data!=null)
    {
      ImageUri= data.getData();
      Addphotos.setImageURI(ImageUri);
    }
}

}
