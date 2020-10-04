package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project.data.DatabaseHandler;
import com.example.project.model.item;
import com.example.project.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText bookname;
    private EditText bookprice;
    private EditText bookauthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycelerview);
        fab = findViewById(R.id.fab);
        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        itemList = databaseHandler.getAllItems();

        for(item item : itemList){
            Log.d("Bookname","onCreate" + item.getBookname());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);

        bookname = view.findViewById(R.id.Book_name);
        bookprice = view.findViewById(R.id.Book_price);
        bookauthor = view.findViewById(R.id.Book_author);

        saveButton = view.findViewById(R.id.saveButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bookname.getText().toString().isEmpty()&&!bookprice.getText().toString().isEmpty()&&!bookauthor.getText().toString().isEmpty()){
                    saveItem(v);
                }else {
                    Snackbar.make(v,"Empty Files not allowed",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveItem(View view) {

        item item = new item();
        String title = bookname.getText().toString().trim();
        int price = Integer.parseInt(bookprice.getText().toString().trim());
        String author = bookauthor.getText().toString().trim();


        item.setBookname(title);
        item.setBookprice(price);
        item.setBookAuthor(author);

        databaseHandler.addItem(item);

        Snackbar.make(view, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //code to be run
                alertDialog.dismiss();
                //Todo: move to next screen - details screen
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();

            }
        }, 1200);// 1sec
    }
}