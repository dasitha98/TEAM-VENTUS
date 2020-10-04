package com.example.project.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.project.model.item;
import com.example.project.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_BOOK_PRice + " INTEGER,"
                + Constants.KEY_BOOK_NAME + " TEXT,"
                + Constants.KEY_AUTHOR + " TEXT,"
                + Constants.KEY_DATE_NAME + " LONG" + ")";

        db.execSQL(CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addItem(item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BOOK_NAME, item.getBookname());
        values.put(Constants.KEY_AUTHOR, item.getBookAuthor());
        values.put(Constants.KEY_BOOK_PRice, item.getBookprice());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Inset the row
        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("DBHandler", "added Item: ");
    }


    //Get all Items
    public List<item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<item> itemList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BOOK_PRice,
                        Constants.KEY_BOOK_NAME,
                        Constants.KEY_AUTHOR, Constants.KEY_DATE_NAME},
                null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                item item = new item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setBookname(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOK_NAME)));
                item.setBookAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
                item.setBookprice(cursor.getInt(cursor.getColumnIndex(Constants.KEY_BOOK_PRice)));


                //convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                        .getTime());

                item.setDateItemAdded(formattedDate);

                //Add to arraylist
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;
    }


    //Get an Item
    public item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_ID,
                        Constants.KEY_BOOK_PRice,
                        Constants.KEY_BOOK_NAME,
                        Constants.KEY_AUTHOR},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        item item = new item();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            item.setBookname(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOK_NAME)));
            item.setBookAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
            item.setBookprice(cursor.getInt(cursor.getColumnIndex(Constants.KEY_BOOK_PRice)));

            //convert Timestamp to something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                    .getTime());

            item.setDateItemAdded(formattedDate);


        }

        return item;
    }

    //Todo: Add updateItem
    public int updateItem(item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BOOK_NAME, item.getBookname());
        values.put(Constants.KEY_AUTHOR, item.getBookAuthor());
        values.put(Constants.KEY_BOOK_PRice, item.getBookprice());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());
        ;//timestamp of the system

        return db.update(Constants.TABLE_NAME, values,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

    }

    //Todo: Add Delete Item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        //close
        db.close();
    }

    //Todo: getItemCount
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

}
