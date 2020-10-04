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

public class MainActivity3 extends AppCompatActivity {

    private Button add;
    private ListView listviewMain;
    private TextView mainText;
    private DbHandler dbHandler;
    private TextView counts;
    private List<Model> models;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        context = this;

        add = findViewById(R.id.add);
        listviewMain = findViewById(R.id.listviewMain);
        mainText = findViewById(R.id.mainText);
        dbHandler = new DbHandler(this);
        counts = findViewById(R.id.mainText);
        models = new ArrayList<>();

        models = dbHandler.getAllnotes();

        modelAdapter adapter = new modelAdapter(context,R.layout.display_notes,models);
        listviewMain.setAdapter(adapter);

        //Get count value and display
        int count = dbHandler.count();
        counts.setText("You have "+count+" notes");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity3.this,add_notes.class);
                startActivity(myintent);
            }
        });

        listviewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Model model = models.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(model.getTitle());
                builder.setMessage(model.getDescription());
                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.setFinished(System.currentTimeMillis());
                        dbHandler.updateSingleNote(model);
                        startActivity(new Intent(context,MainActivity3.class));

                    }
                });

                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,edit_notes.class);
                        intent.putExtra("id",String.valueOf(model.getId()));
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteNote(model.getId());
                        startActivity(new Intent(context,MainActivity3.class));

                    }
                });

                builder.show();
            }
        });
    }
}