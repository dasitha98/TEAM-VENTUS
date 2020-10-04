package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_sup extends AppCompatActivity {

    private EditText suppliername, supplierdescription;
    private Button editbtn;
    private Context context;
    private DbHandlersup dbHandlersup;
    private Long updatedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sup);

        context = this;
        dbHandlersup = new DbHandlersup(context);

        suppliername = findViewById(R.id.editsuppliername);
        supplierdescription = findViewById(R.id.editsupplierdescription);
        editbtn = findViewById(R.id.editbtn);

        final String id = getIntent().getStringExtra("id_sup");
        Modelsupplier modelsupplier = dbHandlersup.getsupplier(Integer.parseInt(id));

        suppliername.setText(modelsupplier.getSuppliername());
        supplierdescription.setText(modelsupplier.getSupplierdescription());

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = suppliername.getText().toString();
                String description = supplierdescription.getText().toString();
                updatedate = System.currentTimeMillis();

                Modelsupplier modelsupplier = new Modelsupplier(Integer.parseInt(id),name,description,updatedate);
                int state = dbHandlersup.update(modelsupplier);
                System.out.println(state);
                startActivity(new Intent(context,Supplier.class));

            }
        });

    }
}