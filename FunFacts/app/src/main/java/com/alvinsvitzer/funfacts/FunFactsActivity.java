package com.alvinsvitzer.funfacts;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FunFactsActivity extends Activity {

    public static final String TAG = FunFactsActivity.class.getSimpleName();
    private static final String KEY_FACT = "KEY_FACT";
    private static final String KEY_COLOR = "KEY_COLOR";

    private FactBook factb = new FactBook();

    private ColorPicker colorWheel = new ColorPicker();
    private String mFact = factb.getFirstFact();
    private int mColorNum = Color.parseColor(colorWheel.mColors[0]);

    private TextView mFactLabel;
    private RelativeLayout mRelativeLayout;
    private Button mShowFactButton;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_FACT, mFact);
        outState.putInt(KEY_COLOR, mColorNum);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mFact = savedInstanceState.getString(KEY_FACT);
        mFactLabel.setText(mFact);

        mColorNum = savedInstanceState.getInt(KEY_COLOR);
        mRelativeLayout.setBackgroundColor(mColorNum);
        mShowFactButton.setTextColor(mColorNum);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.App);
        mShowFactButton = (Button) findViewById(R.id.FFButton);
        mFactLabel = (TextView) findViewById(R.id.funFact);

        //Create a listener to change the displayed fact and colors once button is clicked.
        mShowFactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFact = factb.getRandomFact();

                //Update fact label with a new fact.
                mFactLabel.setText(mFact);
                mColorNum = colorWheel.getRandomColor();

                mRelativeLayout.setBackgroundColor(mColorNum);
                mShowFactButton.setTextColor(mColorNum);

                Log.d(TAG, "Fact Button Clicked.");

            }
        });


    }

}
