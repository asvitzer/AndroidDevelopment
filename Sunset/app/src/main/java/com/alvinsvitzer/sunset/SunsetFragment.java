package com.alvinsvitzer.sunset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alvin on 2/27/16.
 */
public class SunsetFragment extends  Fragment{

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    public static Fragment newInstance(){

        return new SunsetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = v;
        mSunView = v.findViewById(R.id.sun);
        mSkyView = v.findViewById(R.id.sky);

        return v;
    }

    private void startAnimation{

        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

    }
}
