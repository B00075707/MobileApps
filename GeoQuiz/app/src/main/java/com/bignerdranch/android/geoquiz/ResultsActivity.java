package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView mResultsHeadingTextView;
    private TextView mCorrectAnswersTextView;
    private TextView mNumberOfQuestionsTextView;
    private TextView mTimeTakenTextView;
    private Button mRestartButton;
    private Button mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        final String correctAnswers = intent.getStringExtra("correct_answers");
        final String numQuestions = intent.getStringExtra("number_questions");
        final String timeTaken = intent.getStringExtra("time_taken");

        mResultsHeadingTextView = (TextView) findViewById(R.id.results_heading_text_view);
        mResultsHeadingTextView.setText(R.string.quiz_results);

        mCorrectAnswersTextView = (TextView) findViewById(R.id.results_correct_answers_text_view);
        mCorrectAnswersTextView.setText(correctAnswers);

        mNumberOfQuestionsTextView = (TextView) findViewById(R.id.results_number_of_questions_text_view);
        mNumberOfQuestionsTextView.setText(numQuestions);

        mTimeTakenTextView = (TextView) findViewById(R.id.results_time_taken_text_view);
        mTimeTakenTextView.setText(timeTaken);

        mRestartButton = (Button) findViewById(R.id.restart_button);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mShareButton = (Button) findViewById(R.id.share_button);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultsText = getResources().getString(R.string.results_correct_answers_title) + correctAnswers + ", "
                                    + getResources().getString(R.string.results_total_answers_title) + numQuestions + ", "
                                    + getResources().getString(R.string.results_time_taken_title) + timeTaken + " seconds";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, resultsText);
                startActivity(intent);
            }
        });
    }
}
