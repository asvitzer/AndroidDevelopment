package com.alvinsvitzer.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mFalseButton;
    private Button mTrueButton;
    private Button mNextButton;
    private TextView mQuestionText;

    //This should be moved to a model object once it gets more complex
    private TrueFalse[] mQuestionBank = {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mFalseButton = (Button) findViewById(R.id.falseButton);
        mTrueButton = (Button) findViewById(R.id.trueButton);
        mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton = (Button) findViewById(R.id.nextButton);
        mQuestionText = (TextView) findViewById(R.id.questionText);

        mQuestionText.setText(mQuestionBank[mCurrentIndex].getQuestion());

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(QuizActivity.this, R.string.toast_incorrect,Toast.LENGTH_SHORT)
                        .show();

            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(QuizActivity.this, R.string.toast_correct,Toast.LENGTH_SHORT)
                        .show();

            }
        });
    }
}
