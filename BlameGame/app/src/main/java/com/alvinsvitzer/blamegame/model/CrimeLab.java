package com.alvinsvitzer.blamegame.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alvin on 12/30/15.
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> crimeList;

    private CrimeLab(Context context) {

       crimeList = new ArrayList<Crime>();
    }

    public static synchronized CrimeLab getInstance(Context context) {


        if (sCrimeLab == null)

        {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;

    }

    public List<Crime> getCrimes(){

        return crimeList;
    }

    public Crime getCrime(UUID uuid){

        return new Crime(); //Change to loop through the crime objects in the Arraylist

    }

}
