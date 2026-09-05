package com.example.routesense;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "routesense.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TRIPS = "trips";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_PRIORITY = "priority";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRIP_TABLE = "CREATE TABLE " + TABLE_TRIPS + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_SOURCE + " TEXT,"
                + COLUMN_DESTINATION + " TEXT,"
                + COLUMN_WEIGHT + " INTEGER,"
                + COLUMN_PRIORITY + " TEXT" + ")";
        db.execSQL(CREATE_TRIP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        onCreate(db);
    }

    public boolean insertTrip(String id, String source, String destination, int weight, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_SOURCE, source);
        values.put(COLUMN_DESTINATION, destination);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_PRIORITY, priority);
        long result = db.insert(TABLE_TRIPS, null, values);
        return result != -1;
    }
}