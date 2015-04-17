package com.alvinsvitzer.funfacts;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ALVIN on 4/16/15.
 */
public class FactBook {

    private String Fact;
    private ArrayList<String> factList;

    FactBook(){
        setUpFacts();
    }

    public String getRandomFact(){

        Random randomGenerator = new Random();
        int factSpot = randomGenerator.nextInt(factList.size());

        return factList.get(factSpot);
    }

    private void setUpFacts(){
        factList = new ArrayList<String>();

        factList.add("Banging your head against a wall burns 150 calories an hour.");
        factList.add("Bikinis and tampons invented by men.");
        factList.add("A toaster uses almost half as much energy as a full-sized oven.");
        factList.add("A baby octopus is about the size of a flea when it is born.");
        factList.add("Facebook, Skype and Twitter are all banned in China.");

    }


}
