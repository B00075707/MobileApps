package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final boolean LOCAL_LOGD = false;

    private static final String TAG = "QuizActivity";
    private static final String QUESTION_KEY_INDEX = "question_index";
    private static final String TIMER_KEY_INDEX = "timer_index";
    private static final String QUESTIONS_ANSWERED_KEY_INDEX = "questions_answered_index";
    private static final String CORRECT_ANSWERS_KEY_INDEX = "correct_answers_index";
    private static final String ANSWERED_QUESTIONS_KEY_INDEX = "answered_questions_index";
    private static final String QUIZ_RESULTS_KEY_INDEX = "quiz_results_index";

    private long mTimerSeconds = 0;
    private boolean mTimerRunning;
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
            new Question(R.string.question_oceans, false),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false),
    };

    private boolean[] mAnsweredQuestions = new boolean[mQuestionBank.length];
    private boolean[] mQuizResults = new boolean[mQuestionBank.length];
    private int mCorrectAnswers = 0;

    private void resetQuiz() {
        mAnsweredQuestions = new boolean[mQuestionBank.length];
        mQuizResults = new boolean[mQuestionBank.length];
        mCurrentIndex = 0;
        mQuestionsAnswered = 0;
        mTimerSeconds = 0;
        mCorrectAnswers = 0;
        mTimerRunning = false;
    }

    private void finishQuiz() {
        Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
        i.setType("text/plain");
        i.putExtra("correct_answers", Integer.toString(mCorrectAnswers));
        i.putExtra("number_questions", Integer.toString(mQuestionBank.length));
        i.putExtra("time_taken", Integer.toString((int) mTimerSeconds));
        resetQuiz();
        startActivity(i);
    }

    private void nextQuestion() {
        do {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        } while (mAnsweredQuestions[mCurrentIndex]);

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void prevQuestion() {
        do {
            if (mCurrentIndex == 0) {
                mCurrentIndex = mQuestionBank.length-1;
            } else {
                mCurrentIndex--;
            }
        } while (mAnsweredQuestions[mCurrentIndex]);

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void startTimer() {
        mTimerRunning = true;
        mHandler.postDelayed(mRunTimer,1000L);
    }

    private void stopTimer() {
        mTimerRunning = false;
        mHandler.removeCallbacks(mRunTimer);
    }

    private final Runnable mRunTimer = new Runnable() {
        @Override
        public void run() {
            if(mTimerRunning) {
                mTimerSeconds++;
                mTimerTextView.setText(String.format("%d", mTimerSeconds));
                mHandler.postDelayed(mRunTimer,1000L);
            }
        }
    };

    private void checkAnswer(boolean userPressedTrue) {
        if(mTimerRunning == false) {
            startTimer();
        }

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        mAnsweredQuestions[mCurrentIndex] = true;
        mQuestionsAnswered++;

        if(answerIsTrue == userPressedTrue) {
            messageResId = R.string.correct_toast;
            mQuizResults[mCurrentIndex] = true;
            mCorrectAnswers++;
        } else {
            messageResId = R.string.incorrect_toast;
            mQuizResults[mCurrentIndex] = false;
        }

        if(mQuestionsAnswered >= mQuestionBank.length) {
            finishQuiz();
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

        if (LOCAL_LOGD) Log.d(TAG, "checkAnswer() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mHandler = new Handler();

        if (savedInstanceState != null) {
            mAnsweredQuestions = savedInstanceState.getBooleanArray(ANSWERED_QUESTIONS_KEY_INDEX);
            mQuizResults = savedInstanceState.getBooleanArray(QUIZ_RESULTS_KEY_INDEX);
            mCurrentIndex = savedInstanceState.getInt(QUESTION_KEY_INDEX, 0);
            mQuestionsAnswered = savedInstanceState.getInt(QUESTIONS_ANSWERED_KEY_INDEX, 0);
            mTimerSeconds = savedInstanceState.getLong(TIMER_KEY_INDEX, 0);
            mCorrectAnswers = savedInstanceState.getInt(CORRECT_ANSWERS_KEY_INDEX, 0);
        }

        mTimerTextView = (TextView) findViewById(R.id.timer_text_view);
        mTimerTextView.setText(String.format("%d", mTimerSeconds));

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(question);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                nextQuestion();
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                nextQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevQuestion();
            }
        });

        if (LOCAL_LOGD) Log.d(TAG, "onCreate() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBooleanArray(ANSWERED_QUESTIONS_KEY_INDEX, mAnsweredQuestions);
        savedInstanceState.putBooleanArray(QUIZ_RESULTS_KEY_INDEX, mQuizResults);
        savedInstanceState.putInt(QUESTION_KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(QUESTIONS_ANSWERED_KEY_INDEX, mQuestionsAnswered);
        savedInstanceState.putLong(TIMER_KEY_INDEX, mTimerSeconds);
        savedInstanceState.putInt(CORRECT_ANSWERS_KEY_INDEX, mCorrectAnswers);

        if (LOCAL_LOGD) Log.d(TAG, "onSaveInstanceState() called");
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mQuestionsAnswered > 0) {
            startTimer();
        }
        if (LOCAL_LOGD) Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (LOCAL_LOGD) Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LOCAL_LOGD) Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimer();
        if (LOCAL_LOGD) Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LOCAL_LOGD) Log.d(TAG, "onDestroy() called");
    }
}
