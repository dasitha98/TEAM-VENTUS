package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FrontAddress extends AppCompatActivity {

    Button callSignUp,login_btn;
    ImageView image;
    TextView logoText, sloganText;
    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This Line will hide the status bar from the screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_front_address);


        //hooks
        callSignUp = findViewById(R.id.signup_screen);
        logoText = findViewById(R.id.logoName);
        sloganText = findViewById(R.id.sloganName);

        login_btn = findViewById(R.id.login_btn);
        image = findViewById(R.id.logoImage);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontAddress.this, AddAddress.class);
                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_descri");
                pairs[3] = new Pair<View, String>(login_btn, "button_tran");
                pairs[4] = new Pair<View, String>(callSignUp, "login_signUp_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FrontAddress.this,pairs);
                    startActivity(intent, options.toBundle());
                }

            }
        });




        //retrieve data


        SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);

        lst1 = findViewById(R.id.lst1);
        final Cursor c = db.rawQuery("select * from records",null);
        int name = c.getColumnIndex("name");
        int cardNo = c.getColumnIndex("cardNo");
        int expiryDate = c.getColumnIndex("expiryDate");
        int cvc = c.getColumnIndex("cvc");
        titles.clear();

        arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,titles);
        lst1.setAdapter(arrayAdapter);


        final  ArrayList<UserHelperClass> stud = new ArrayList<UserHelperClass>();
        if(c.moveToFirst())
        {
            do{
                UserHelperClass stu = new UserHelperClass();

                stu.name = c.getString(name);
                stu.cardNo = c.getString(cardNo);
                stu.expiryDate = c.getString(expiryDate);
                stu.cvc = c.getString(cvc);
                stud.add(stu);

                titles.add(c.getString(name) + " \t "  + c.getString(cardNo) + " \t " + c.getString(expiryDate) + " \t "  + c.getString(cvc) );

            } while(c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();



        }

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserHelperClass stu = stud.get(i);
                Intent il = new Intent(FrontAddress.this,DeleteCard.class);

                il.putExtra("id",stu.id);
                il.putExtra("name",stu.name);
                il.putExtra("cardNo",stu.cardNo);
                il.putExtra("expiryDate",stu.expiryDate);
                il.putExtra("cvc",stu.cvc);
                startActivity(il);


            }
        });

    }

}