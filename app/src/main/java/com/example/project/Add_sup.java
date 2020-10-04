package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_sup extends AppCompatActivity {

    private EditText suppliername, supplierdescription;
    private Button insertbtn;
    private DbHandlersup dbhandlersup;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sup);

        suppliername = findViewById(R.id.suppliername);
        supplierdescription = findViewById(R.id.supplierdescription);
        insertbtn = findViewById(R.id.insertbtn);
        context = this;
        dbhandlersup = new DbHandlersup(context);

        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = suppliername.getText().toString();
                String description = supplierdescription.getText().toString();
                long start = System.currentTimeMillis();

                Modelsupplier modelsupplier = new Modelsupplier(name,description,start);
                dbhandlersup.addsupplier(modelsupplier);

                startActivity(new Intent(context, Supplier.class));
            }
        });

    }

}