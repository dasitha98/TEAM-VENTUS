package com.example.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, RegEmail, regPhoneno, regPassword;
    Button regBtn, btn1;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn1 = findViewById(R.id.already);
        regBtn = findViewById(R.id.signup);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        RegEmail = findViewById(R.id.email);
        regPhoneno = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        loadingBar = new ProgressDialog(this);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateName() | !validateEmail()){
                    return;
                }


                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = RegEmail.getEditText().getText().toString();
                final String phoneNo = regPhoneno.getEditText().getText().toString().trim();
                String password = regPassword.getEditText().getText().toString();


                loadingBar.setTitle("Please Wait");
                loadingBar.setMessage("You Will Registered...Wait Few Seconds");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                if (TextUtils.isEmpty(phoneNo)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {

                    reference = FirebaseDatabase.getInstance().getReference();

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (!(dataSnapshot.child("library").child(phoneNo).exists())) {

                                String name = regName.getEditText().getText().toString().trim();
                                String username = regUsername.getEditText().getText().toString().trim();
                                String email = RegEmail.getEditText().getText().toString().trim();
                                String phoneNo = regPhoneno.getEditText().getText().toString().trim();
                                String password = regPassword.getEditText().getText().toString().trim();




                                HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("name", name);
                                userdataMap.put("username", username);
                                userdataMap.put("email", email);
                                userdataMap.put("phoneNo", phoneNo);
                                userdataMap.put("password", password);

                                reference.child("library").child(phoneNo).updateChildren(userdataMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SignUp.this, Login.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Cannot Registered", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                            }
                                        });


                            } else {
                                Toast.makeText(getApplicationContext(), "This " + phoneNo + "already Exists", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }
        });


    }








    private Boolean validatePhoneNo() {
        String val = regPhoneno.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneno.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneno.setError(null);
            return true;
        }
    }




    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\s+$)" +
                ".{4,}" +
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            RegEmail.setError("password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            regName.setErrorEnabled(false);
            return false;
        } else {
            regName.setError(null);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = RegEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            RegEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            RegEmail.setError("Invalid email address");
            return false;
        } else {
            RegEmail.setError(null);
            return true;
        }
    }





}