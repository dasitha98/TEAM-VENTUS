package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddAddress extends AppCompatActivity {

    TextInputLayout regName, regCardNo, regExpiryDate, regCVC;
    Button regButton, regToLoginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_address);

        regName = findViewById(R.id.name);
        regCardNo = findViewById(R.id.cardNumber);
        regExpiryDate = findViewById(R.id.expiryDate);
        regCVC = findViewById(R.id.cvc);
        regButton = findViewById(R.id.regButton);
        regToLoginButton= findViewById(R.id.regToLoginButton);



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insert();


            }
        });
    }


    public void insert()
    {
        try
        {
            String name = regName.getEditText().getText().toString();
            String cardNo = regCardNo.getEditText().getText().toString();
            String expiryDate = regExpiryDate.getEditText().getText().toString();
            String cvc = regCVC.getEditText().getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,cardNo VARCHAR,expiryDate VARCHAR,cvc VARCHAR)");

            String sql = "insert into records(name,cardNo,expiryDate,cvc)values(?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,name);
            statement.bindString(2,cardNo);
            statement.bindString(3,expiryDate);
            statement.bindString(4,cvc);
            statement.execute();
            Toast.makeText(AddAddress.this,"Record addded",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(AddAddress.this, FrontAddress.class);
            startActivity(intent);

            regName.getEditText().setText("");
            regCardNo.getEditText().setText("");
            regExpiryDate.getEditText().setText("");
            regCVC.getEditText().setText("");
            regName.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(AddAddress.this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }
}