package com.example.rahipart2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class join extends AppCompatActivity
{
    private Button CreateAccountButton;
    private EditText Username, Email, Password;
    ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        CreateAccountButton = (Button) findViewById(R.id.join_btn_register);
        Username = (EditText) findViewById(R.id.join_username);
        Email = (EditText) findViewById(R.id.join_email);
        Password = (EditText) findViewById(R.id.join_password);
      //  ConfirmPassword = (EditText) findViewById(R.id.confirm_join_password);
        loadingBar = new ProgressDialog(this);




        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               CreateAccount();
            }
        });

    }

    private void CreateAccount()
    {
    String username = Username.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
       // String confirmpassword = ConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please write the username", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write the email", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write the password", Toast.LENGTH_SHORT).show();
        }

        /*else if (TextUtils.isEmpty(confirmpassword))
        {
            Toast.makeText(this, "Please confirm the password", Toast.LENGTH_SHORT).show();
        }*/

       /* else if (confirmpassword != password)
        {
            Toast.makeText(this, "Confirm password must be same as Password", Toast.LENGTH_SHORT).show();
        } */

        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validatatecredentials(username,email,password );
        }

    }

    private void Validatatecredentials(final String username, final String email, final String password/*, final String confirmpassword*/)
    {
       final DatabaseReference RootRef;
       RootRef = FirebaseDatabase.getInstance().getReference();

       RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {
           if(!(dataSnapshot.child("Users").child(email).exists()))
           {
            /*if (!(dataSnapshot.child("Users").child(username).child(email).exists()))
            {*/
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("username",username);
                userdataMap.put("email",email);
                userdataMap.put("password",password);
              /*  userdataMap.put("confirmpassword",confirmpassword);*/

                RootRef.child("Users").child(email).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(join.this, "Congratulations your account has been created", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                    Intent intent = new Intent(join.this, login.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(join.this, "Network error: Please try again", Toast.LENGTH_SHORT).show();
                                }
                                }
                        }) ;         //   }
           /* else
            {
                Toast.makeText(join.this, "This" + email + "already exists", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Toast.makeText(join.this, "Please enter another email", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(join.this, MainActivity.class);
                startActivity(intent);
            }*/
           }

           else
           {
               Toast.makeText(join.this, "This" + email + "already exists", Toast.LENGTH_SHORT).show();
               loadingBar.dismiss();
               Toast.makeText(join.this, "Please enter another unique email", Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(join.this, MainActivity.class);
               startActivity(intent);

           }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }


}
