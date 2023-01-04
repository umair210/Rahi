package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahipart2.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class editprofile extends AppCompatActivity {

    private ImageView profilepic;
    private EditText bioupdate;
    private TextView close_botn, update_btn, changepic_btn;
    private StorageTask uploadTask;
  //  private Profile display_pic;

    private Uri imageUri;
   //to store image url
    private String  myUrl="";
    private StorageReference storageprofilepic;
    private String checker="";
    Profile profile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        storageprofilepic = FirebaseStorage.getInstance().getReference().child("Profile pics");
    profilepic= (ImageView) findViewById(R.id.setting_profileimage);
    bioupdate =(EditText) findViewById(R.id.edit_bio);
    close_botn =(TextView) findViewById(R.id.close_btn);
    update_btn =(TextView) findViewById(R.id.update_btn);
    changepic_btn= (TextView) findViewById(R.id.change_profilepic_btn);
//display_pic =new Profile();
profile= new Profile();




    close_botn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    update_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
        if (checker.equals("clicked"))
        {
            userinfoSaved();
        }
        else
        {
            updatonlyuserinfo();
        }
        }
    });

    //open gallery to get the pic
        changepic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //this clicked value will save userinfoSaved
              checker="clicked" ;

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(editprofile.this);
            }
        });



        userinfoDisplay(profilepic,bioupdate,changepic_btn, update_btn);

    }



    private void updatonlyuserinfo()
    {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");

        //using hashmap because of lot of data
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("bioofuser",bioupdate);

        ref.child(Prevalent.currentonlineUsers.getEmail()).updateChildren(userMap);


        startActivity(new Intent(editprofile.this, home.class));

        Toast.makeText(editprofile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            imageUri = result.getUri();
            
            profilepic.setImageURI(imageUri);
           //Picasso.get().load(Prevalent.currentonlineUsers.getImage()).into(profilephotoofuser);
          //  Picasso.get().load(imageUri).into(profile.profilephotoofuser);


           // Toast.makeText(this, "hellloooooo", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Error, Try Again..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(editprofile.this,editprofile.class));
            finish();
        }


    }

    private void userinfoSaved()
    {
      if(TextUtils.isEmpty(bioupdate.getText().toString()))
      {
          Toast.makeText(this, "Enter bio", Toast.LENGTH_SHORT).show();
      }
      else if(checker.equals("clicked"))
      {
          uploadImage();
      }

    }

    //upload image
    private void uploadImage()
    {
    final ProgressDialog progressDialog =new ProgressDialog(this);
    progressDialog.setTitle("Updated profile");
    progressDialog.setMessage("Please while we update your info");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();

    if(imageUri !=null)
    {
        //upload new photo instead of older one
        final StorageReference finalRef = storageprofilepic
                .child(Prevalent.currentonlineUsers.getEmail() + ".jpg" );

        uploadTask =finalRef.putFile(imageUri);
//get the url in database
        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception
            {
            if (!task.isSuccessful())
            {
                throw task.getException();
            }
                return finalRef.getDownloadUrl();
            }
        })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                      if(task.isSuccessful())
                      {
                          Uri downloadUri = task.getResult();
                          myUrl= downloadUri.toString();

                          DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");

                          //using hashmap because of lot of data
                          HashMap<String, Object> userMap = new HashMap<>();
                          userMap.put("image", myUrl);
                          userMap.put("bioofuser",bioupdate.getText().toString());

                          ref.child(Prevalent.currentonlineUsers.getEmail()).updateChildren(userMap);

                          progressDialog.dismiss();

                          startActivity(new Intent(editprofile.this, home.class));

                          Toast.makeText(editprofile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                          
                          finish();
                      }
                      else
                      {
                          progressDialog.dismiss();
                          Toast.makeText(editprofile.this, "Error", Toast.LENGTH_SHORT).show();
                      }
                    }
                });

    }
    else
    {
        Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
    }
    }

    private void userinfoDisplay(final ImageView profilepic, final EditText bioupdate, TextView changepic_btn, TextView update_btn)
    {
        DatabaseReference   UserRef = FirebaseDatabase.getInstance().getReference().child(Prevalent.currentonlineUsers.getEmail());
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    if(snapshot.child("image").exists())
                    {
                        String image =snapshot.child("image").getValue().toString();
                        String email =snapshot.child("email").getValue().toString();
                        String password =snapshot.child("password").getValue().toString();
                        String username =snapshot.child("username").getValue().toString();
                        String writebio =snapshot.child("bioofuser").getValue().toString();

                        Picasso.get().load(image).into(profilepic);
                        bioupdate.setText(writebio);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
