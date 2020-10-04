package com.example.project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    Button sign, login;
    TextInputLayout phoneNo,password;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        phoneNo = findViewById(R.id.phoneNo);
        password = findViewById(R.id.password);

        sign = findViewById(R.id.signup);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });





        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validatePhoneNo() | !ValidatePassword()) {
                    return;
                }
                    final String userEnteredPhoneNo = phoneNo.getEditText().getText().toString().trim();
                    final String userEnteredPassword = password.getEditText().getText().toString().trim();

                  //  rootNode = FirebaseDatabase.getInstance();
                  //  reference = rootNode.getReference("library");

                  //  Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

                Query checkUser = FirebaseDatabase.getInstance().getReference("library").orderByChild("phoneNo").equalTo(userEnteredPhoneNo);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()) {

                                phoneNo.setError(null);
                                phoneNo.setErrorEnabled(false);

                                String passwordFormDB = snapshot.child(userEnteredPhoneNo).child("password").getValue(String.class);

                                if(passwordFormDB == null){
                                    passwordFormDB = "empty string";
                                }
                                if(passwordFormDB.equals(userEnteredPassword)){

                                    phoneNo.setError(null);
                                    phoneNo.setErrorEnabled(false);

                                    String nameFromDB = snapshot.child(userEnteredPhoneNo).child("name").getValue(String.class);
                                    String usernameFromDB = snapshot.child(userEnteredPhoneNo).child("username").getValue(String.class);
                                    String phoneNoFromDB = snapshot.child(userEnteredPhoneNo).child("phoneNo").getValue(String.class);
                                    String emailFromDB = snapshot.child(userEnteredPhoneNo).child("email").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(),UserProfile.class);

                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("username", usernameFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("phoneNo", phoneNoFromDB);
                                    intent.putExtra("password", passwordFormDB);

                                    startActivity(intent);

                                }else{
                                    password.setError("Wrong password");
                                    password.requestFocus();
                                }

                            }
                            else{
                                phoneNo.setError("No user exist");
                                phoneNo.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });
                }



        });



    }

    private Boolean validatePhoneNo() {
        String val = phoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            phoneNo.setError("Field cannot be empty");
            return false;
        } else {
            phoneNo.setError(null);
            return true;
        }
    }


    private Boolean ValidatePassword(){
        String val = password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }





}