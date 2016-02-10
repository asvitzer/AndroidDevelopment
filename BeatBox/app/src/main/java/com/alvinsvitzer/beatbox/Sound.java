package com.alvinsvitzer.beatbox;

/**
 * Created by asvitzer on 02/10/2016.
 */
public class Sound {

    private String mAssetPath;
    private String mName;

    public Sound(String assetPath){

        mAssetPath = assetPath;
        String[] componenets = assetPath.split("/");
        String filename = componenets[componenets.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath(){
        return mAssetPath;
    }

    public String getName(){
        return mName;
    }
}