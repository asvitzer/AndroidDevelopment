package com.alvinsvitzer.silentcircle.helper;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by asvitzer on 10/07/2015.
 */
public class SilentCircleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "GEOA0udADw7u779mWThMiRmLvxQAJNIHLcSvcF54", "2cVwjqvIsD8wR2XSktt44n6pfVsOgG3v0WaVS8tu");
    }
}
