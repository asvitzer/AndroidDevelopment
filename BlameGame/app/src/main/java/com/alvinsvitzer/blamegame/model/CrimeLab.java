package com.alvinsvitzer.blamegame.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeBaseHelper;

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

        return new ArrayList<>();
    }

    public void addCrime(Crime c){

        ContentValues values = CrimeLab.getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);

    }

    public void removeCrime(Crime c){

    }

    public Crime getCrime(UUID id){

        return null;

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

        return values;

    }

    private Cursor queryCrimes(String whereClause, String[] whereArgs){

        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return cursor;
    }

}
