package com.alvinsvitzer.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mFalseButton;
    private Button mTrueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mFalseButton = (Button) findViewById(R.id.falseButton);
        mTrueButton = (Button) findViewById(R.id.trueButton);

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
