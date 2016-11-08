package com.bignerdranch.android.geoquiz;

/**
 * Created by comathuna on 04/10/2016.
 */
public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mWasAnswered;
    private boolean mAnsweredCorrectly;

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public void submitAnswer(boolean userAnswer) {
        mWasAnswered = true;
        if (userAnswer == mAnswerTrue) {
            mAnsweredCorrectly = true;
        } else {
            mAnsweredCorrectly = false;
        }
    }

    public boolean getWasAnswered() {
        return mWasAnswered;
    }

    public boolean getAnsweredCorrectly() {
        return mAnsweredCorrectly;
    }

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mWasAnswered = false;
        mAnsweredCorrectly = false;
    }

}
