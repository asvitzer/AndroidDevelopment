package com.alvinsvitzer.funfacts;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by asvitzer on 4/17/2015.
 */
public class ColorPicker {

    public String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };

    public int getRandomColor(){

        Random randomGenerator = new Random();
        int colorSpot = randomGenerator.nextInt(mColors.length);

        int colorNumber = Color.parseColor(mColors[colorSpot]);

        return colorNumber;

    }
    
}
