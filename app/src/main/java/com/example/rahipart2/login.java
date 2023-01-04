package com.example.rahipart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rahipart2.Model.Users;
import com.example.rahipart2.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    private Button LoginButton;
    private EditText LoginUsername, LoginPassword;
    private  ProgressDialog loadingBar;
    private String parentdb = "Users" ;
    private CheckBox chkboxrememberme;
    //private Button LoginSkipbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        LoginUsername =(EditText) findViewById(R.id.username);
        LoginPassword = (EditText)findViewById(R.id.password);
        loadingBar = new ProgressDialog(this);
        chkboxrememberme =(CheckBox) findViewById(R.id.remember_chk) ;
       // LoginSkipbtn=(Button) findViewById(R.id.login_skip_btn);

        Paper.init(this);




        LoginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
             Loginuser();
             }
         });
    }

    private void Loginuser()
    {
        String loginusername = LoginUsername.getText().toString();

        String loginpassword = LoginPassword.getText().toString();


        if (TextUtils.isEmpty(loginusername))
        {
            Toast.makeText(this, "Please write the username", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(loginpassword))
        {
            Toast.makeText(this, "Please write the password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Signing in");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateLoginCredentials(loginusername,loginpassword);
        }

    }

    private void ValidateLoginCredentials(final String loginusername, final String loginpassword)
    {
        if (chkboxrememberme.isChecked())
        {
            Paper.book().write(Prevalent.Usernamekey, loginusername);
            Paper.book().write(Prevalent.Userpasswordkey, loginpassword);
        }

    final DatabaseReference Dataref;
    Dataref = FirebaseDatabase.getInstance().getReference();

    Dataref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            if(dataSnapshot.child(parentdb).child(loginusername).exists())
            {
                Users userdata= dataSnapshot.child(parentdb).child(loginusername).getValue(Users.class);

                if(userdata.getEmail().equals(loginusername))
                {
                    if(userdata.getPassword().equals(loginpassword))
                    {
                        Toast.makeText(login.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                        Intent intent = new Intent(login.this, home.class);
                        Prevalent.currentonlineUsers = userdata;
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }

            }
            else
            {
                Toast.makeText(login.this, "This user doesn't exist...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Toast.makeText(login.this, "Please create new account...", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
}
