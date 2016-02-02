package com.alvinsvitzer.blamegame.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.alvinsvitzer.blamegame.database.CrimeBaseHelper;
import com.alvinsvitzer.blamegame.database.CrimeCursorWrapper;
import com.alvinsvitzer.blamegame.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alvin on 12/30/15.
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public static synchronized CrimeLab getInstance(Context context) {


        if (sCrimeLab == null)

        {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;

    }

    public List<Crime> getCrimes(){

        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally{
                cursor.close();
            }
        return crimes;
    }

    public void addCrime(Crime c){

        ContentValues values = CrimeLab.getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);

    }

    public void removeCrime(Crime c){

    }

    public Crime getCrime(UUID id){

        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString()});

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        }
        finally{
            cursor.close();
        }

    }

    public void updateCrime(Crime crime){

        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[] {uuidString});

    }

    private static ContentValues getContentValues(Crime crime){

        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().toString());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;

    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){

        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CrimeCursorWrapper(cursor);
    }

    public File getPhotoFile(Crime crime){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null){
            return null;
        }

        return new File(externalFilesDir, crime.getPhotoFilename());
    }

}
