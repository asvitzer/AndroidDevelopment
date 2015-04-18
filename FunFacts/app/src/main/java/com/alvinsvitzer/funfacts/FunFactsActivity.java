package com.alvinsvitzer.funfacts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FunFactsActivity extends Activity {

    public static final String TAG = FunFactsActivity.class.getSimpleName();
    private FactBook factb = new FactBook();
    private ColorPicker colorWheel = new ColorPicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.App);
        final Button showFactButton = (Button) findViewById(R.id.FFButton);
        final TextView factLabel = (TextView) findViewById(R.id.funFact);

        //Create a listener to change the displayed fact and colors once button is clicked.
        showFactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update fact label with a new fact.
                factLabel.setText(factb.getRandomFact());
                int colorNum = colorWheel.getRandomColor();

                rl.setBackgroundColor(colorNum);
                showFactButton.setTextColor(colorNum);

                Log.d(TAG, "I've been clicked!!!");

            }
        });


    }

}
