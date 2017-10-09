package com.dicoding.tesyant.kamus.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tesyant on 10/4/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static String DATABASE_NAME = "dbkamus";

    //table name
    public static String TABLE_ENG = "englishindo";
    public static String TABLE_IND = "tbl_ind";

    //column name
    public static String ENG_ID = "eng_id";
    public static String IND_ID = "ind_id";
    public static String ENG_VOCAB = "eng_vocab";
    public static String IND_VOCAB = "ind_vocab";
    public static String ENG_MEANS = "eng_means";
    public static String IND_MEANS = "ind_means";

    public static final String CREATE_TABLE_ENGLISHIND = "CREATE TABLE " + TABLE_ENG
            + "("
            + ENG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + ENG_VOCAB + " TEXT NOT NULL , "
            + ENG_MEANS + " TEXT NOT NULL );" ;


    public static final String CREATE_TABLE_INDOENG = "CREATE TABLE " + TABLE_IND
            + "("
            + IND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + IND_VOCAB + " TEXT NOT NULL , "
            + IND_MEANS + " TEXT NOT NULL );" ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISHIND);
        db.execSQL(CREATE_TABLE_INDOENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IND);
        onCreate(db);
    }
}
