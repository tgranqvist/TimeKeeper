package fi.tkjg.timekeeper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Granqvist on 07-12-2016.
 *
 * This is the database helper for the project. Use this, and only this, to access the
 * database. It has helper methods for all the queries needed.
 */
public class TimeRecordDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TimeKeeper.db";

    public TimeRecordDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    public List<TimeRecord> getAllEntries() {
        List<TimeRecord> records = new ArrayList<>();
        try (SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TimeRecordContract.TABLE_NAME, TimeRecordContract.ALL_COLUMNS, null, null, null, null, null) ) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TimeRecord record = new TimeRecord();
                record.date = cursor.getString(cursor.getColumnIndexOrThrow(TimeRecordContract._ID));
                record.in = cursor.getInt(cursor.getColumnIndexOrThrow(TimeRecordContract.COLUMN_TIME_IN));
                record.out = cursor.getInt(cursor.getColumnIndexOrThrow(TimeRecordContract.COLUMN_TIME_OUT));
                record.isVelhoOk = cursor.getInt(cursor.getColumnIndexOrThrow(TimeRecordContract.COLUMN_VELHO_OK)) == 1;

                records.add(record);
                cursor.moveToNext();
            }
        }

        return records;
    }

    public boolean addEntry(TimeRecord record) {
        boolean result = false;
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(TimeRecordContract.COLUMN_TIME_IN, record.in);
            values.put(TimeRecordContract.COLUMN_TIME_OUT, record.out);
            values.put(TimeRecordContract.COLUMN_VELHO_OK, record.isVelhoOk);
            long id = db.insert(TimeRecordContract.TABLE_NAME, null, values);

            result = id > -1;
        }
        return result;
    }

    // PRIVATE //
    private static final String SQL_CREATE_TABLE = String.format (
        "CREATE TABLE %s (%s PRIMARY KEY DEFAULT CURRENT_DATE, %s INTEGER, %s INTEGER, %s INTEGER)",
            TimeRecordContract.TABLE_NAME, TimeRecordContract._ID, TimeRecordContract.COLUMN_TIME_IN,
            TimeRecordContract.COLUMN_TIME_OUT, TimeRecordContract.COLUMN_VELHO_OK
    );

    private static final String TAG = "TimeRecordDBHelper";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TimeRecordContract.TABLE_NAME;
    private static final String[] ALL_COLUMNS = TimeRecordContract.ALL_COLUMNS;
    //private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("Y-MM-d");
}
