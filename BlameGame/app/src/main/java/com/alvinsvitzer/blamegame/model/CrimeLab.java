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

       crimeList = new ArrayList<>();

        for (int x = 0; x < 100; x++){

            Crime c = new Crime();
            c.setTitle("Crime #" + x);
            c.setSolved(x % 2 == 0); //set every other crime object to not solved
            crimeList.add(c);
        }
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

    public Crime getCrime(UUID id){

        for (Crime c: crimeList){
            if (c.getId().equals(id)){
                return c;
            }
        }

        return null;

    }

}
