package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "project";
    private static final String TABLE_NAME = "myDescription";

    //column names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String NOTE = "note";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";


    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY = "CREATE TABLE " +TABLE_NAME+ "" +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +NOTE+ " TEXT,"
                +STARTED + " TEXT,"
                +FINISHED+ " TEXT" +
                ");" ;

        db.execSQL(TABLE_CREATE_QUERY);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public void addNotes(Model model) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,model.getTitle());
        contentValues.put(NOTE,model.getDescription());
        contentValues.put(STARTED,model.getStarted());
        contentValues.put(FINISHED,model.getFinished());

        //save to table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        //close database
        sqLiteDatabase.close();
    }

    //Count table records
    public int count() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    //Get all notes to a list
    public List<Model> getAllnotes() {

        List<Model> models = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do{
                //Create new Model
                Model model = new Model();

                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setDescription(cursor.getString(2));
                model.setStarted(cursor.getLong(3));
                model.setFinished(cursor.getLong(4));

                models.add(model);
            }while (cursor.moveToNext());

        }
        return models;
    }

    //Delete Item
    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,ID  +" =?",new String[]{String.valueOf(id)});
        db.close();
    }

    //get a single data
    public Model getSingleModel(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,NOTE,STARTED,FINISHED},ID +"= ?"
                ,new String[]{String.valueOf(id)},null,null,null);

        Model model;
        if(cursor != null ) {
            cursor.moveToFirst();
            model = new Model(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );
            return model;
        }
        return null;
    }

    //Update a single note
    public int updateSingleNote(Model model) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,model.getTitle());
        contentValues.put(NOTE,model.getDescription());
        contentValues.put(STARTED,model.getStarted());
        contentValues.put(FINISHED,model.getFinished());

        int status = db.update(TABLE_NAME,contentValues,ID +" =?",
                new String[]{String.valueOf(model.getId())});

        db.close();
        return  status;

    }
}

