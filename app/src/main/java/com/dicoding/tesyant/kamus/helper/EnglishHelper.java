package com.dicoding.tesyant.kamus.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.dicoding.tesyant.kamus.model.EnglishModel;

import java.util.ArrayList;

/**
 * Created by tesyant on 10/5/17.
 */

public class EnglishHelper {

    private static String DATABASE_TABLE = DatabaseHelper.TABLE_ENG;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public EnglishHelper(Context context) {
        this.context = context;
    }

    public EnglishHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor searchQueryByName (String query) {
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " +
                DatabaseHelper.ENG_VOCAB + " LIKE '%" + query + "%'", null);
    }

    public Cursor searchMeanByVocab (String query) {
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " +
        DatabaseHelper.ENG_VOCAB + " = '" + query + "'", null);
    }

    public final String getMeaningData(String search) {
        Cursor cursor = searchMeanByVocab(search);
        String result = new String();
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            result = cursor.getString(2);
        }
        cursor.close();
        return result;
    }

    public final ArrayList<String> getData(String search) {
        Cursor cursor = searchQueryByName(search);
        ArrayList<String> result = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            for (int i=0; i<cursor.getCount(); i++) {
                result.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

    public Cursor queryAllData() {
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY " +
        DatabaseHelper.ENG_ID + " ASC", null);
    }

    public ArrayList<EnglishModel> getAllData() {
        ArrayList<EnglishModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData();
        cursor.moveToFirst();
        EnglishModel englishModel;
        if (cursor.getCount()>0) {
            do {
                englishModel = new EnglishModel();
                englishModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ENG_ID)));
                englishModel.setVocab(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ENG_VOCAB)));
                englishModel.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ENG_MEANS)));

                arrayList.add(englishModel);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<EnglishModel> getDetailData() {
        ArrayList<EnglishModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData();
        cursor.moveToFirst();
        EnglishModel englishModel;
        if (cursor.getCount()>0) {
            do {
                englishModel = new EnglishModel();
                englishModel.setVocab(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ENG_VOCAB)));
                englishModel.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ENG_MEANS)));

                arrayList.add(englishModel);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(EnglishModel englishModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHelper.ENG_VOCAB, englishModel.getVocab());
        initialValues.put(DatabaseHelper.ENG_MEANS, englishModel.getMeans());
        Log.d("insert", "check");
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void insertTransaction(ArrayList<EnglishModel> englishModels) {
        String sql = "INSERT INTO " + DATABASE_TABLE + " ("
                + DatabaseHelper.ENG_VOCAB + ", "
                + DatabaseHelper.ENG_MEANS + ", " + ") VALUES (?, ?, ?, ?);";
        database.beginTransaction();

        SQLiteStatement statement = database.compileStatement(sql);
        for (int i = 0; i < englishModels.size(); i++) {
            statement.bindString(1, englishModels.get(i).getVocab());
            statement.bindString(3, englishModels.get(i).getMeans());
            statement.execute();
            statement.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void update(EnglishModel englishModel) {
        ContentValues args = new ContentValues();
        args.put(DatabaseHelper.ENG_VOCAB, englishModel.getVocab());
        args.put(DatabaseHelper.ENG_MEANS, englishModel.getMeans());
        database.update(DATABASE_TABLE, args, DatabaseHelper.ENG_ID + "= '"
        + englishModel.getId() + "'", null);
    }

    public void delete(int id) {
        database.delete(DatabaseHelper.TABLE_ENG, DatabaseHelper.ENG_ID + " = '" + id
        + "'", null);
    }


}
