package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.alvinsvitzer.adventuretime.R;


public class StoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

            Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.key_name));

    }

}
