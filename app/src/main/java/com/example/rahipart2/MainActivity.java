package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rahipart2.Model.Users;
import com.example.rahipart2.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    private Button joinnowbutton, loginbutton;

    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinnowbutton =(Button) findViewById(R.id.join_btn);
        loginbutton =(Button) findViewById(R.id.login_btn);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             Intent intent = new Intent(MainActivity.this, login.class);
             startActivity(intent);

            }
        });

        joinnowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             Intent intent = new Intent(MainActivity.this,join.class);
             startActivity(intent);
            }
        });



        String UserNameKey = Paper.book().read(Prevalent.Usernamekey);
        String UserPasswordKey = Paper.book().read(Prevalent.Userpasswordkey);

        if (UserNameKey !=""  && UserPasswordKey !="")
        {
            if (!TextUtils.isEmpty(UserNameKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserNameKey, UserPasswordKey);

                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("Please wait....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


    }

    private void AllowAccess(final String loginusername,final String loginpassword)
    {
        final DatabaseReference Dataref;
        Dataref = FirebaseDatabase.getInstance().getReference();

        Dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("Users").child(loginusername).exists())
                {
                    Users userdata= dataSnapshot.child("Users").child(loginusername).getValue(Users.class);

                    if(userdata.getEmail().equals(loginusername))
                    {
                        if(userdata.getPassword().equals(loginpassword))
                        {
                            Toast.makeText(MainActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, home.class);
                           // Prevalent.currentonlineUsers = userdata;
                            startActivity(intent);

                        }
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "This user doesn't exist...", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Please create new account...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
