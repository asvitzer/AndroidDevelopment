package com.alvinsvitzer.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.alvinsvitzer.geoquiz.answer_is_true";
    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX = "Question_Bank_Index";
    private static final String ANSWER_INDEX = "Answer_Result_Index";
    private static final String CHEAT_INDEX = "Cheated_Result_Index";

    private Button mFalseButton;
    private Button mTrueButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionText;

    //This should be moved to a model object once it gets more complex
    private TrueFalse[] mQuestionBank = {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private boolean[] mCheatedResult = new boolean[mQuestionBank.length];
    private boolean[] mAnswerResult = new boolean[mQuestionBank.length];

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mFalseButton = (Button) findViewById(R.id.falseButton);
        mTrueButton = (Button) findViewById(R.id.trueButton);
        mNextButton = (ImageButton) findViewById(R.id.nextButton);
        mPrevButton = (ImageButton) findViewById(R.id.prevButton);
        mCheatButton = (Button) findViewById(R.id.cheatButton);
        mQuestionText = (TextView) findViewById(R.id.questionText);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mAnswerResult = savedInstanceState.getBooleanArray(ANSWER_INDEX);
            mCheatedResult = savedInstanceState.getBooleanArray(CHEAT_INDEX);
        }

        updateQuestions();

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(intent, 0);
            }
        });

        mQuestionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentIndex < (mQuestionBank.length - 1)) {
                    mCurrentIndex++;

                } else {

                    Log.v(TAG, "End of question bank. Questions reset");
                    mCurrentIndex = 0;

                }

                updateQuestions();

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentIndex < (mQuestionBank.length - 1)) {
                    mCurrentIndex++;

                }

                else{

                    Log.v(TAG, "End of question bank. Questions reset");
                    mCurrentIndex = 0;

                }

                updateQuestions();

            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentIndex <= (mQuestionBank.length - 1) && mCurrentIndex > 0 ) {
                    mCurrentIndex--;

                }

                else{

                    Log.v(TAG, "End of question bank. Questions reset");
                    mCurrentIndex = mQuestionBank.length - 1;

                }

                updateQuestions();

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswers(false);


            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswers(true);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray(ANSWER_INDEX, mAnswerResult);
        outState.putBooleanArray(CHEAT_INDEX, mCheatedResult);
    }

    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    private void updateQuestions(){


        mQuestionText.setText(mQuestionBank[mCurrentIndex].getQuestion());
    }

    private void checkAnswers(boolean userPressedTrue){

        boolean answerTruth = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;

            if (mCheatedResult[mCurrentIndex]) {
                messageResId = R.string.judgement_toast;
            } else {

                if(mAnswerResult[mCurrentIndex]){
                    messageResId = R.string.duplicate_answer;
                }

                else {

                    mAnswerResult[mCurrentIndex] = true;

                    if (userPressedTrue == answerTruth) {
                        messageResId = R.string.toast_correct;
                    } else {
                        messageResId = R.string.toast_incorrect;
                    }
                }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null){

            return;
        }

        mCheatedResult[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN, false);
    }
}
