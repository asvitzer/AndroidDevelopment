package com.alvinsvitzer.funfacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class FunFactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);

        final TextView factLabel = (TextView) findViewById(R.id.funFact);

        Button showFactButton = (Button) findViewById(R.id.FFButton);
        showFactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update fact label with a new fact.
                String fact = getRandomFact();
                factLabel.setText(fact);

            }
        });
    }

    public String getRandomFact() {

        ArrayList<String> factList = new ArrayList<String>();

        factList.add("Banging your head against a wall burns 150 calories an hour.");
        factList.add("Bikinis and tampons invented by men.");
        factList.add("A toaster uses almost half as much energy as a full-sized oven.");
        factList.add("A baby octopus is about the size of a flea when it is born.");
        factList.add("Facebook, Skype and Twitter are all banned in China.");

        int factSpot = (int) (Math.random() * factList.size());

        return factList.get(factSpot);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun_facts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
