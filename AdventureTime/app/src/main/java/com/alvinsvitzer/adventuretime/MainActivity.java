package com.alvinsvitzer.adventuretime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        final Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                //Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG);
                startStory(name);
            }
        });

    }

    private void startStory(String name){
        Intent intent = new Intent(this,StoryActivity.class);
        intent.putExtra(getString(R.string.key_name),name);
        startActivity(intent);
    }
}
