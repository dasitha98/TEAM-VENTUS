package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_notes extends AppCompatActivity {

    private EditText title,desc;
    private Button buttonAdd;
    private DbHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        title = findViewById(R.id.addTitle);
        desc = findViewById(R.id.addDescription);
        buttonAdd = findViewById(R.id.buttonAdd);
        context = this;
        dbHandler = new DbHandler(context);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usertitle = title.getText().toString();
                String userDescription = desc.getText().toString();
                long started = System.currentTimeMillis();

                Model model = new Model(usertitle,userDescription,started,0);
                dbHandler.addNotes(model);

                startActivity(new Intent(context,MainActivity3.class));
            }
        });

    }
}