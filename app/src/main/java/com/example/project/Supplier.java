package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends AppCompatActivity {

    private Button addbtn;
    private ListView listViewsup;
    private DbHandlersup dbHandlersup;
    private List<Modelsupplier> modelsuppliers;
    private Context context;
    private TextView counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        context = this;
        dbHandlersup = new DbHandlersup(this);

        counts = findViewById(R.id.counttext);
        addbtn = findViewById(R.id.addbtn);
        listViewsup = findViewById(R.id.listviewsup);
        modelsuppliers = new ArrayList<>();

        modelsuppliers = dbHandlersup.getSupplierDetails();

        ModelAdaptersupp modelAdaptersupp = new ModelAdaptersupp(context,R.layout.display_sup,modelsuppliers);
        listViewsup.setAdapter(modelAdaptersupp);

        int count =dbHandlersup.count();
        counts.setText(count+" Suppliers");

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Supplier.this, Add_sup.class);
                startActivity(intent);
            }
        });



        listViewsup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Modelsupplier modelsupplier = modelsuppliers.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(modelsupplier.getSuppliername());
                builder.setMessage(modelsupplier.getSupplierdescription());

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandlersup.deletesupplier(modelsupplier.getId_sup());
                        startActivity(new Intent(context,Supplier.class));
                    }
                });

                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,Edit_sup.class);
                        intent.putExtra("id_sup",String.valueOf(modelsupplier.getId_sup()));
                        startActivity(intent);
                    }
                });

                builder.show();
            }
        });





    }


}