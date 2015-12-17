package com.alvinsvitzer.geoquiz;

/**
 * Created by Alvin on 12/12/15.
 */
public class TrueFalse {


    private int mQuestion;
    private boolean mTrueQuestion;

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public TrueFalse(int question, boolean trueQuestion){

        mQuestion = question;
        mTrueQuestion = trueQuestion;

    }
}
