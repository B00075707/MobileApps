package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String QUESTION_KEY_INDEX = "question_index";
    private static final String TIMER_KEY_INDEX = "timer_index";

    private long seconds = 0;
    private boolean isRunning;
    private Handler mHandler;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private TextView mTimerTextView;

    private int mCurrentIndex = 0;
    private int mQuestionsAnswered = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private boolean[] mAnsweredQuestions = new boolean[mQuestionBank.length];
    private boolean[] mQuizResults = new boolean[mQuestionBank.length];


    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void nextQuestion() {
        Log.d(TAG, "nextQuestion() called");

        if(mQuestionsAnswered >= mQuestionBank.length) {
            //finish quiz
            Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
            startActivity(i);
        } else {
            //mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
            do {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
            } while (mQuestionBank[mCurrentIndex].getWasAnswered());
        }

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void prevQuestion() {
        Log.d(TAG, "prevQuestion() called");

        if(mQuestionsAnswered >= mQuestionBank.length) {
            //finish quiz
            Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
            startActivity(i);
        } else {
            //mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
            do {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length-1;
                } else {
                    mCurrentIndex--;
                }
            } while (mQuestionBank[mCurrentIndex].getWasAnswered());
        }

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {


        mQuestionBank[mCurrentIndex].submitAnswer(userPressedTrue);

        mQuestionsAnswered = mQuestionsAnswered + 1;

        int messageResId = 0;

        if(mQuestionBank[mCurrentIndex].getAnsweredCorrectly()) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
        Log.d(TAG, "checkAnswer() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mHandler = new Handler();

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(QUESTION_KEY_INDEX, 0);
            seconds = savedInstanceState.getLong(TIMER_KEY_INDEX, 0);
        }

        mTimerTextView = (TextView) findViewById(R.id.timer_text_view);
        mTimerTextView.setText(String.format("%d",seconds));

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
//                updateQuestion();
                nextQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                nextQuestion();
//                updateQuestion();
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                nextQuestion();
//                updateQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
//                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevQuestion();
//                updateQuestion();
            }
        });

//        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(QUESTION_KEY_INDEX, mCurrentIndex);
        savedInstanceState.putLong(TIMER_KEY_INDEX, seconds);
    }

    @Override
    public void onStart() {
        super.onStart();
        isRunning = true;
        mHandler.postDelayed(mRunTimer, 1000L);
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private final Runnable mRunTimer = new Runnable() {
        @Override
        public void run() {
            if(isRunning) {
                seconds++;
                mTimerTextView.setText(String.format("%d",seconds));
                mHandler.postDelayed(mRunTimer,1000L);
            }
        }
    };
}
