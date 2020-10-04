package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class DeleteCard extends AppCompatActivity {

    TextInputLayout name,cardNo,expDate,cvc;
    Button deleteBtn, editBtn;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_card);

        name = findViewById(R.id.name);
        cardNo = findViewById(R.id.cardNumber);
        expDate = findViewById(R.id.expiryDate);
        cvc = findViewById(R.id.cvc);

        deleteBtn = findViewById(R.id.deleteButton);
        editBtn = findViewById(R.id.editBtn);

        Intent i = getIntent();



        String t1 = i.getStringExtra("name").toString();
        String t2 = i.getStringExtra("cardNo").toString();
        String t3 = i.getStringExtra("expiryDate").toString();
        String t4 = i.getStringExtra("cvc").toString();


        name.getEditText().setText(t1);
        cardNo.getEditText().setText(t2);
        expDate.getEditText().setText(t3);
        cvc.getEditText().setText(t4);




        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Delete();

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Edit();

            }
        });
    }

    public void Delete()
    {
        try
        {
            String names = name.getEditText().getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);


//            db.delete("records","name = ?",new String[]{names});

            String sql = "delete from records where name = ?";
            SQLiteStatement statement = db.compileStatement(sql);


            statement.bindString(1,names);
            statement.execute();
            Toast.makeText(this,"Record Deleted",Toast.LENGTH_LONG).show();


            Intent intent = new Intent(DeleteCard.this,FrontAddress.class);
            startActivity(intent);


        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }


    public void Edit()
    {
        try
        {

            String name = this.name.getEditText().getText().toString();
            String cardNo = this.cardNo.getEditText().getText().toString();
            String expDate = this.expDate.getEditText().getText().toString();
            String cvc = this.cvc.getEditText().getText().toString();


            SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);


            String sql = "update records set cardNo=?,expDate=?,cvc2=? where name= ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,cardNo);
            statement.bindString(2,expDate);
            statement.bindString(3,cvc);
            statement.bindString(4,name);

            statement.execute();
            Toast.makeText(this,"Record Updateddd",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DeleteCard.this, FrontAddress.class);
            startActivity(intent);


            this.name.getEditText().setText("");
            this.cardNo.getEditText().setText("");
            this.expDate.getEditText().setText("");
            this.cvc.getEditText().setText("");



        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DeleteCard.this, FrontAddress.class);
            startActivity(intent);
        }

    }

}