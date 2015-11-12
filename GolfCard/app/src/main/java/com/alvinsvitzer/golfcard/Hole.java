package com.alvinsvitzer.golfcard;

/**
 * Created by Alvin on 11/11/15.
 */
public class Hole {

    private String mLabel;
    private int mStrokeCount;

            public Hole(String label, int strokeCount){

                mLabel = label;
                mStrokeCount = strokeCount;
            }

    public int getStrokeCount() {
        return mStrokeCount;
    }

    public void setStrokeCount(int strokeCount) {
        mStrokeCount = strokeCount;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }
}
