package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandlersup extends SQLiteOpenHelper {

    //database
    private static final int VERSION =1;
    private static final String DB_NAME_SUP = "supplier";
    private static final String TABLE_NAME_SUP = "supplier";

    //columns
    private static final String ID_SUP = "id_sup";
    private static final String  SUPPLIER_NAME = "suppliername";
    private static final String  SUPPLIER_DESCRIPTION = "supplierdescription";
    private static final String  START = "start" ;

    public DbHandlersup(@Nullable Context context) {
        super(context, DB_NAME_SUP, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY_SUP = "CREATE TABLE " + TABLE_NAME_SUP+ "" +
                "("
                +ID_SUP+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +SUPPLIER_NAME+" TEXT,"
                +SUPPLIER_DESCRIPTION+" TEXT,"
                +START+" TEXT" +
                ");" ;

        db.execSQL(TABLE_CREATE_QUERY_SUP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY_SUP = "DROP TABLE IF EXISTS "+ TABLE_NAME_SUP;
        db.execSQL(DROP_TABLE_QUERY_SUP);
        onCreate(db);

    }

    public void addsupplier(Modelsupplier modelsupplier){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SUPPLIER_NAME,modelsupplier.getSuppliername());
        contentValues.put(SUPPLIER_DESCRIPTION,modelsupplier.getSupplierdescription());
        contentValues.put(START,modelsupplier.getStart());

        sqLiteDatabase.insert(TABLE_NAME_SUP, null, contentValues);

        sqLiteDatabase.close();

    }

    public List<Modelsupplier> getSupplierDetails(){

        List<Modelsupplier> modelsuppliers = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " +TABLE_NAME_SUP;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                Modelsupplier modelsupplier = new Modelsupplier();

                modelsupplier.setId_sup(cursor.getInt(0));
                modelsupplier.setSuppliername(cursor.getString(1));
                modelsupplier.setSupplierdescription(cursor.getString(2));
                modelsupplier.setStart(cursor.getLong(3));

                modelsuppliers.add(modelsupplier);
            }while (cursor.moveToNext());
        }
        return modelsuppliers;
    }

    public int count(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME_SUP;

        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    public void deletesupplier(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_SUP,ID_SUP +" =?",new String[]{String.valueOf(id)});
        db.close();
    }


    public Modelsupplier getsupplier(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SUP, new String[]{ID_SUP,SUPPLIER_NAME,SUPPLIER_DESCRIPTION,START}, ID_SUP +"=? "
                ,new String[]{String.valueOf(id)},null,null,null);

        Modelsupplier modelsupplier;
        if(cursor != null){
            cursor.moveToFirst();
            modelsupplier = new Modelsupplier(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3)
            );
            return modelsupplier;
        }
        return null;
    }


    public int update(Modelsupplier modelsupplier){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_SUP,modelsupplier.getId_sup());
        contentValues.put(SUPPLIER_NAME,modelsupplier.getSuppliername());
        contentValues.put(SUPPLIER_DESCRIPTION,modelsupplier.getSupplierdescription());

        int status = db.update(TABLE_NAME_SUP,contentValues, ID_SUP +" =?",
                new String[]{String.valueOf(modelsupplier.getId_sup())});

        db.close();
        return status;

    }


}

