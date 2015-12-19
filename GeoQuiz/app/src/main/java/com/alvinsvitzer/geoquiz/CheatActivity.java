package com.alvinsvitzer.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean mAnswerIsTrue;
    private boolean mIsAnswerShown;
    private Button mShowAnswerButton;
    private TextView mAnswerTextView;

    public static final String EXTRA_ANSWER_IS_SHOWN = "com.alvinsvitzer.geoquiz.answer_is_shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        Intent intent = getIntent();
        mAnswerIsTrue = intent.getBooleanExtra(QuizActivity.EXTRA_ANSWER_IS_TRUE, false);

        if (savedInstanceState != null){
            mIsAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_SHOWN, false);
        }

        setAnswerShownResult(mIsAnswerShown);

        mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAnswerIsTrue){

                    mAnswerTextView.setText(R.string.true_button);
                }

                else{
                    mAnswerTextView.setText(R.string.false_button);
                }

                mIsAnswerShown = true;
                setAnswerShownResult(mIsAnswerShown);

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(EXTRA_ANSWER_IS_SHOWN, mIsAnswerShown);
    }

    private void setAnswerShownResult(boolean isAnswerShown){

            Intent intent = new Intent();
            intent.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
            setResult(RESULT_OK, intent);
    }
}
