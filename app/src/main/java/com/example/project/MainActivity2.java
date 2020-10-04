package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import com.example.project.data.DatabaseHandler;
import com.example.project.model.item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText bookname;
    private EditText bookprice;
    private EditText bookauthor;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);
        byPassActivity();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
    }
    private void byPassActivity() {
        if(databaseHandler.getItemsCount() > 0){
            startActivity(new Intent(MainActivity2.this,ListActivity.class));
            finish();
        }
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

        Snackbar.make(view,"Item Saved",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity2.this,ListActivity.class));

            }
        }, 1200);
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        bookname = view.findViewById(R.id.Book_name);
        bookprice = view.findViewById(R.id.Book_price);
        bookauthor = view.findViewById(R.id.Book_author);

        saveButton = view.findViewById(R.id.saveButton);
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

        builder.setView(view);
        dialog = builder.create();// creating our dialog object
        dialog.show();// important step!

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}