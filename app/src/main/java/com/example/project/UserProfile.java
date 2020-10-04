package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

   TextInputLayout name, email, phoneNo, password;
   TextView fullNameLabel, usernameLabel;

   Context context;

   Button bookbtn1, supplierbtn2, deliverybtn3, notebtn4;

   String user_username, user_name, user_email, user_phoneNo, user_password;

   DatabaseReference referance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        referance = FirebaseDatabase.getInstance().getReference("library");

        bookbtn1 = findViewById(R.id.bookbtn1);
        supplierbtn2 = findViewById(R.id.supplierbtn2);
        deliverybtn3 = findViewById(R.id.deliverybtn3);
        notebtn4 = findViewById(R.id.notebtn4);

        context = this;

        name = findViewById(R.id.user_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        showAllUserData();

        bookbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity2.class));
            }
        });

        supplierbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Supplier.class));
            }
        });

        notebtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity3.class));
            }
        });

        deliverybtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity4.class));
            }
        });

    }

    private void showAllUserData() {
        Intent intent = getIntent();


         user_name = intent.getStringExtra("name");
         user_username = intent.getStringExtra("username");
         user_email = intent.getStringExtra("email");
         user_phoneNo = intent.getStringExtra("phoneNo");
         user_password = intent.getStringExtra("password");

        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);

        name.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);

    }


    public void update(View view){

        if(isNameChanged() | isPasswordChanged() | isEmailChanged()){
            Toast.makeText(this,"Data has been updated", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Data is same cannot update", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isEmailChanged() {
        if(user_email == null){
            user_email = "empty string";
        }
        if(!user_email.equals((email.getEditText().getText().toString()))){
            referance.child(user_phoneNo).child("email").setValue(email.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPasswordChanged() {

        if(user_password == null){
            user_password = "empty string";
        }
        if(!user_password.equals((password.getEditText().getText().toString()))){
            referance.child(user_phoneNo).child("password").setValue(password.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }

    }

    private boolean isNameChanged() {

        if(user_name == null){
            user_name = "empty string";
        }
        if(!user_name.equals((name.getEditText().getText().toString()))){
            referance.child(user_phoneNo).child("name").setValue(name.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }


}