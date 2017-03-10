package com.example.cida.agenda.database;

/**
 * Created by Cida on 11/07/2016.
 */
import android.content.Context;
import android.database.sqlite.*;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {

        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Script.getCreateContato());


    }
        @Override

        public void onUpgrade (SQLiteDatabase db,int oldVersion,int newVersion){

        }
    }
