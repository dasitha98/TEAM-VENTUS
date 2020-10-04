package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class edit_notes extends AppCompatActivity {

    private EditText title,des;
    private Button edit;
    private DbHandler dbHandler;
    private Context context;
    private Long updatedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        context = this;
        dbHandler = new DbHandler(context);

        title = findViewById(R.id.editTitle);
        des = findViewById(R.id.editDescription);
        edit = findViewById(R.id.buttonEdit);

        final String id =getIntent().getStringExtra("id");
        Model model = dbHandler.getSingleModel(Integer.parseInt(id));

        title.setText(model.getTitle());
        des.setText(model.getDescription());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titletext = title.getText().toString();
                String dectext = des.getText().toString();
                updatedate = System.currentTimeMillis();

                Model model = new Model(Integer.parseInt(id),titletext,dectext,updatedate,0);
                int state = dbHandler.updateSingleNote(model);
                System.out.println(state);
                startActivity(new Intent(context,MainActivity3.class));
            }
        });
    }
}