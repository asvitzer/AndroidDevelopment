package com.alvinsvitzer.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asvitzer on 02/08/2016.
 */
public class BeatBox {

    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context){
        mAssets = context.getAssets();

        //Can't use SoundPool.Builder since the MIN API for this app is level 16.
        //Can go back in the future and create two separate constructors (16 & up/ Below 16)
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {

        String[] soundNames = new String[0];

        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
        }

        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                Log.v(TAG, "Loaded sound: " + filename + " at path " + assetPath);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException{

        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd,1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){

        Integer soundId = sound.getSoundId();
        if (sound == null){
            Log.e(TAG, "Could not play sound " + soundId);
            return;
        }

        Log.d(TAG, "Playing sound: " + soundId);
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release(){
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
